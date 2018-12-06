package org.frelylr.sfb.common;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

/**
 * FTP文件上传下载工具类 *使用@Autowired注入的方式调用, 否则取不到配置信息
 */
@Component
public class FtpUtils {

    private final Logger log = LoggerFactory.getLogger(ExcelUtils.class);

    // ftp服务器地址
    @Value("${ftp.config.hostname}")
    private String hostname;

    // ftp服务器端口号默认为21
    @Value("${ftp.config.port}")
    private Integer port;

    // ftp登录账号
    @Value("${ftp.config.username}")
    private String username;

    // ftp登录密码
    @Value("${ftp.config.password}")
    private String password;

    // ftp客户端
    private FTPClient ftpClient = null;

    private final String sysSeparator = System.getProperty("file.separator");

    /**
     * 初始化ftp服务器
     */
    public void initFtpClient() {

        ftpClient = new FTPClient();
        ftpClient.setControlEncoding("utf-8");
        try {
            log.info("connecting...ftp服务器:" + this.hostname + ":" + this.port);
            ftpClient.connect(hostname, port); // 连接ftp服务器
            ftpClient.login(username, password); // 登录ftp服务器
            int replyCode = ftpClient.getReplyCode(); // 是否成功登录服务器
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                log.info("connect failed...ftp server:" + this.hostname + ":" + this.port);
            }
            log.info("connect successful...ftp server:" + this.hostname + ":" + this.port);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传文件
     *
     * @param uploadPath    ftp服务保存目录
     * @param fileName      上传到ftp的文件名
     * @param localFilePath 待上传文件的名称（绝对地址）
     */
    public boolean uploadFile(String uploadPath, String fileName, String localFilePath) {

        boolean flag = false;
        InputStream inputStream = null;
        try {
            log.info("开始上传文件");
            inputStream = new FileInputStream(new File(localFilePath));
            initFtpClient();
            ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);
            createDirectory(uploadPath);
            ftpClient.makeDirectory(uploadPath);
            ftpClient.changeWorkingDirectory(uploadPath);
            ftpClient.storeFile(fileName, inputStream);
            inputStream.close();
            ftpClient.logout();
            flag = true;
            log.info("上传文件成功");
        } catch (Exception e) {
            log.info("上传文件失败");
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return flag;
    }

    /**
     * 上传文件
     *
     * @param uploadPath  ftp服务保存目录
     * @param fileName    上传到ftp的文件名
     * @param inputStream 输入文件流
     */
    public boolean uploadFile(String uploadPath, String fileName, InputStream inputStream) {

        boolean flag = false;
        try {
            log.info("开始上传文件");
            initFtpClient();
            ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);
            createDirectory(uploadPath);
            ftpClient.makeDirectory(uploadPath);
            ftpClient.changeWorkingDirectory(uploadPath);
            ftpClient.storeFile(fileName, inputStream);
            inputStream.close();
            ftpClient.logout();
            flag = true;
            log.info("上传文件成功");
        } catch (Exception e) {
            log.info("上传文件失败");
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return flag;
    }

    /**
     * 改变目录路径
     */
    public boolean changeWorkingDirectory(String directory) {

        boolean flag = true;
        try {
            flag = ftpClient.changeWorkingDirectory(directory);
            if (flag) {
                log.info("进入文件夹" + directory + " 成功！");

            } else {
                log.info("进入文件夹" + directory + " 失败！开始创建文件夹");
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return flag;
    }

    /**
     * 创建上传目录
     */
    private boolean createDirectory(String uploadPath) throws IOException {

        boolean success = true;
        String directory = uploadPath + sysSeparator;

        if (!directory.equalsIgnoreCase(sysSeparator) && !changeWorkingDirectory(new String(directory))) {
            int start = 0;
            int end = 0;
            if (directory.startsWith(sysSeparator)) {
                start = 1;
            } else {
                start = 0;
            }
            end = directory.indexOf(sysSeparator, start);
            String path = "";
            String paths = "";
            while (true) {
                String subDirectory = new String(uploadPath.substring(start, end).getBytes("GBK"), "iso-8859-1");
                path = path + sysSeparator + subDirectory;
                if (!existFile(path)) {
                    if (makeDirectory(subDirectory)) {
                        changeWorkingDirectory(subDirectory);
                    } else {
                        log.info("创建目录[" + subDirectory + "]失败");
                        changeWorkingDirectory(subDirectory);
                    }
                } else {
                    changeWorkingDirectory(subDirectory);
                }

                paths = paths + sysSeparator + subDirectory;
                start = end + 1;
                end = directory.indexOf(sysSeparator, start);

                // 检查所有目录是否创建完毕
                if (end <= start) {
                    break;
                }
            }
        }

        return success;
    }

    /**
     * 判断ftp服务器文件是否存在
     */
    public boolean existFile(String path) throws IOException {

        boolean flag = false;
        FTPFile[] ftpFileArr = ftpClient.listFiles(path);
        if (ftpFileArr.length > 0) {
            flag = true;
        }

        return flag;
    }

    /**
     * 创建目录
     */
    public boolean makeDirectory(String dir) {

        boolean flag = true;
        try {
            flag = ftpClient.makeDirectory(dir);
            if (flag) {
                log.info("创建文件夹" + dir + " 成功！");

            } else {
                log.info("创建文件夹" + dir + " 失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }

    /**
     * 以流的形式输出excel
     *
     * @param filePath FTP文件路径
     * @param fileName 要下载的文件名
     */
    public void downloadStream(String filePath, String fileName) {

        boolean flag = false;
        OutputStream os = null;
        try {
            log.info("开始下载文件");
            initFtpClient();
            //切换FTP目录
            ftpClient.changeWorkingDirectory(filePath);
            FTPFile[] ftpFiles = ftpClient.listFiles();
            for (FTPFile file : ftpFiles) {
                if (fileName.equalsIgnoreCase(file.getName())) {
                    HttpServletResponse response = RequestUtils.getResponse();
                    os = response.getOutputStream();
                    response.setContentType("application/x-msdownload");
                    response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
                    ftpClient.retrieveFile(file.getName(), os);
                    os.close();
                }
            }
            ftpClient.logout();
            flag = true;
            log.info("下载文件成功");
        } catch (Exception e) {
            log.info("下载文件失败");
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 下载文件
     *
     * @param ftpPath   FTP服务器文件目录
     * @param fileName  文件名称
     * @param localPath 下载后的文件路径
     */
    public boolean downloadFile(String ftpPath, String fileName, String localPath) {

        boolean flag = false;
        OutputStream os = null;
        try {
            log.info("开始下载文件");
            initFtpClient();
            //切换FTP目录
            ftpClient.changeWorkingDirectory(ftpPath);
            FTPFile[] ftpFiles = ftpClient.listFiles();
            for (FTPFile file : ftpFiles) {
                if (fileName.equalsIgnoreCase(file.getName())) {
                    File localFile = new File(localPath + sysSeparator + file.getName());
                    os = new FileOutputStream(localFile);
                    ftpClient.retrieveFile(file.getName(), os);
                    os.close();
                }
            }
            ftpClient.logout();
            flag = true;
            log.info("下载文件成功");
        } catch (Exception e) {
            log.info("下载文件失败");
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return flag;
    }

    /**
     * 删除文件
     *
     * @param ftpPath  FTP服务器保存目录
     * @param fileName 要删除的文件名称
     */
    public boolean deleteFile(String ftpPath, String fileName) {

        boolean flag = false;
        try {
            log.info("开始删除文件");
            initFtpClient();
            ftpClient.changeWorkingDirectory(ftpPath);
            ftpClient.dele(fileName);
            ftpClient.logout();
            flag = true;
            log.info("删除文件成功");
        } catch (Exception e) {
            log.info("删除文件失败");
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return flag;
    }

}
