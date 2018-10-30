package org.frelylr.sfb.common;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public class ExcelUtils {

    private final static Logger log = LoggerFactory.getLogger(ExcelUtils.class);
    private final static String EXCEL2003 = "xls";
    private final static String EXCEL2007 = "xlsx";

    /**
     * 读取excel
     *
     * @param path 路径(含文件名)
     * @param cls  数据Bean
     * @return 数据List
     */
    public static <T> List<T> readExcel(String path, Class<T> cls) {

        List<T> dataList = new ArrayList<>();
        Workbook workbook = null;
        try {
            if (path.endsWith(EXCEL2007)) {
                FileInputStream is = new FileInputStream(new File(path));
                workbook = new XSSFWorkbook(is);
            }
            if (path.endsWith(EXCEL2003)) {
                FileInputStream is = new FileInputStream(new File(path));
                workbook = new HSSFWorkbook(is);
            }
            if (workbook != null) {
                // 类映射 注解 value --> bean columns
                Map<String, List<Field>> classMap = new HashMap<>();
                Field[] fields = cls.getDeclaredFields();
                for (Field field : fields) {
                    ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
                    if (annotation != null) {
                        String value = annotation.value();
                        if (StringUtils.isBlank(value)) {
                            continue;
                        }
                        if (!classMap.containsKey(value)) {
                            classMap.put(value, new ArrayList<>());
                        }
                        field.setAccessible(true);
                        classMap.get(value).add(field);
                    }
                }
                // 索引 --> columns
                Map<Integer, List<Field>> reflectionMap = new HashMap<>();
                Sheet sheet = workbook.getSheetAt(0); // 默认读取第一个sheet

                boolean firstRow = true;
                for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);

                    if (firstRow) { // 首行 提取注解
                        for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
                            Cell cell = row.getCell(j);
                            String cellValue = getCellValue(cell);
                            if (classMap.containsKey(cellValue)) {
                                reflectionMap.put(j, classMap.get(cellValue));
                            }
                        }
                        firstRow = false;
                    } else {
                        if (row == null) {
                            continue;
                        }

                        try {
                            T t = cls.newInstance();
                            boolean allBlank = true; // 判断是否为空白行
                            for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
                                if (reflectionMap.containsKey(j)) {
                                    Cell cell = row.getCell(j);
                                    String cellValue = getCellValue(cell);
                                    if (StringUtils.isNotBlank(cellValue)) {
                                        allBlank = false;
                                    }
                                    List<Field> fieldList = reflectionMap.get(j);
                                    for (Field field : fieldList) {
                                        try {
                                            handleField(t, cellValue, field);
                                        } catch (Exception e) {
                                            log.error(String.format("reflect field:%s value:%s exception!",
                                                    field.getName(), cellValue), e);
                                        }
                                    }
                                }
                            }
                            if (!allBlank) {
                                dataList.add(t);
                            } else {
                                log.warn(String.format("row:%s is blank ignore!", i));
                            }
                        } catch (Exception e) {
                            log.error(String.format("parse row:%s exception!", i), e);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error(String.format("parse excel exception!"), e);
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (Exception e) {

                }
            }
        }
        return dataList;
    }

    private static <T> void handleField(T t, String value, Field field) throws Exception {

        Class<?> type = field.getType();
        if (type == null || type == void.class || StringUtils.isBlank(value)) {
            return;
        }

        if (type == Object.class) {
            field.set(t, value);
        } else if (type.getSuperclass() == null || type.getSuperclass() == Number.class) {
            if (type == int.class || type == Integer.class) {
                field.set(t, NumberUtils.toInt(value));
            } else if (type == long.class || type == Long.class) {
                field.set(t, NumberUtils.toLong(value));
            } else if (type == byte.class || type == Byte.class) {
                field.set(t, NumberUtils.toByte(value));
            } else if (type == short.class || type == Short.class) {
                field.set(t, NumberUtils.toShort(value));
            } else if (type == double.class || type == Double.class) {
                field.set(t, NumberUtils.toDouble(value));
            } else if (type == float.class || type == Float.class) {
                field.set(t, NumberUtils.toFloat(value));
            } else if (type == char.class || type == Character.class) {
                field.set(t, CharUtils.toChar(value));
            } else if (type == boolean.class) {
                field.set(t, BooleanUtils.toBoolean(value));
            } else if (type == BigDecimal.class) {
                field.set(t, new BigDecimal(value));
            }
        } else if (type == Boolean.class) {
            field.set(t, BooleanUtils.toBoolean(value));
        } else if (type == Date.class) {
            field.set(t, DateUtils.parseDate(value));
        } else if (type == String.class) {
            field.set(t, value);
        } else {
            Constructor<?> constructor = type.getConstructor(String.class);
            field.set(t, constructor.newInstance(value));
        }
    }

    /**
     * 取cell内容
     */
    private static String getCellValue(Cell cell) {

        if (cell == null) {
            return "";
        }

        CellType cellType = cell.getCellType();
        if (cellType == CellType.BLANK) {
            return "";
        } else if (cellType == CellType.STRING) {
            return StringUtils.trimToEmpty(cell.getStringCellValue());
        } else if (cellType == CellType.FORMULA) {
            return StringUtils.trimToEmpty(cell.getCellFormula());
        } else if (cellType == CellType.BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cellType == CellType.ERROR) {
            return "ERROR";
        } else if (cellType == CellType.NUMERIC) {
            if (DateUtil.isCellDateFormatted(cell)) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
                return dtf.format(DateUtil.getJavaDate(cell.getNumericCellValue()).toInstant());
            } else {
                return new BigDecimal(cell.getNumericCellValue()).toString();
            }
        } else {
            return cell.toString().trim();
        }
    }

    /**
     * 数据写入并在指定位置生成excel文件
     *
     * @param path     路径(含文件名)
     * @param dataList 数据List
     * @param cls      数据Bean
     */
    public static <T> void writeExcel(String path, List<T> dataList, Class<T> cls) {

        Workbook wb = createWorkBook(dataList, cls);

        File file = new File(path);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            wb.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (wb != null) {
                    wb.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 以流的形式输出excel
     *
     * @param fileName 文件名
     * @param dataList 数据List
     * @param cls      数据Bean
     */
    public static <T> void writeExcelOutputStream(String fileName, List<T> dataList, Class<T> cls) {

        Workbook wb = createWorkBook(dataList, cls);

        HttpServletResponse response = RequestUtils.getResponse();

        try (OutputStream out = response.getOutputStream()) {
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
            wb.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置excel
     */
    private static <T> Workbook createWorkBook(List<T> dataList, Class<T> cls) {

        // 数据处理
        Field[] fields = cls.getDeclaredFields();
        List<Field> fieldList = Arrays.stream(fields).filter(field -> {
            ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
            if (annotation != null && annotation.col() > 0) {
                field.setAccessible(true);
                return true;
            }
            return false;
        }).sorted(Comparator.comparing(field -> {
            int col = 0;
            ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
            if (annotation != null) {
                col = annotation.col();
            }
            return col;
        })).collect(Collectors.toList());

        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("devices");
        AtomicInteger ai = new AtomicInteger();

        // 写入头部
        {
            Row row = sheet.createRow(ai.getAndIncrement());
            AtomicInteger aj = new AtomicInteger();
            fieldList.forEach(field -> {
                ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
                String columnName = "";
                if (annotation != null) {
                    columnName = annotation.value();
                }

                CellStyle cellStyle = wb.createCellStyle();
                cellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                cellStyle.setAlignment(HorizontalAlignment.CENTER);
                Font font = wb.createFont();
                font.setBold(true);
                cellStyle.setFont(font);

                Cell cell = row.createCell(aj.getAndIncrement());
                cell.setCellStyle(cellStyle);
                cell.setCellValue(columnName);
            });
        }

        // 写入数据
        if (dataList != null && !dataList.isEmpty()) {
            dataList.forEach(t -> {
                Row row = sheet.createRow(ai.getAndIncrement());
                AtomicInteger aj = new AtomicInteger();
                fieldList.forEach(field -> {
                    Class<?> type = field.getType();
                    Object value = "";
                    try {
                        value = field.get(t);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Cell cell = row.createCell(aj.getAndIncrement());
                    if (value != null) {
                        if (type == Date.class) {
                            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
                            cell.setCellValue(dtf.format(((Date) value).toInstant()));
                        } else {
                            cell.setCellValue(value.toString());
                        }
                    }
                });
            });
        }

        // 调整列宽
        for (int i = 0; i < fieldList.size(); i++) {
            sheet.autoSizeColumn(i);
        }

        // 冻结窗格
        wb.getSheet("devices").createFreezePane(0, 1, 0, 1);

        return wb;
    }

    /**
     * 下载指定路径文件
     *
     * @param filePath 文件路径(含文件名)
     */
    public static void downLoadFile(String filePath) {

        FileInputStream in = null;
        ServletOutputStream out = null;
        BufferedOutputStream toOut = null;

        try {
            in = new FileInputStream(new File(filePath));
            byte[] buffer = new byte[in.available()];
            while (in.read(buffer) != -1) {
                HttpServletResponse response = RequestUtils.getResponse();
                // 设置响应的文件的头文件格式
                response.reset();
                response.setContentType("application/octet-stream");
                response.addHeader("Content-type", "application-download");
                response.setHeader("Content-Disposition",
                        "attachment;filename=" + new String(new File(filePath).getName().getBytes("GBK"), "ISO8859-1"));
                response.setHeader("Pragma", "No-cache");
                response.setHeader("Cache-Control", "No-cache");
                response.setDateHeader("Expires", 0);
                // 获取响应的对象流
                out = response.getOutputStream();
                toOut = new BufferedOutputStream(out);
                toOut.write(buffer);
                toOut.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
                if (toOut != null) {
                    toOut.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
