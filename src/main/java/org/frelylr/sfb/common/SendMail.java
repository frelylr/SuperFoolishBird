package org.frelylr.sfb.common;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {

    private final String TRUE = "true";
    private final String SMTP = "smtp";
    private final String MAIL_SMTP_HOST = "mail.smtp.host";
    private final String MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";
    private final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    private final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";

    private String server;
    private String from;
    private String pwd;
    private String to;
    private String subject;
    private String msg;

    public SendMail(String server, String from, String pwd, String to, String subject, String msg) {
        this.server = server;
        this.from = from;
        this.pwd = pwd;
        this.to = to;
        this.subject = subject;
        this.msg = msg;
    }

    public void sendText() throws MessagingException {

        Properties props = new Properties();
        props.put(MAIL_SMTP_HOST, server); // 发送邮件的服务器地址, 例如：www.xxx.com
        props.setProperty(MAIL_TRANSPORT_PROTOCOL, SMTP); // 设置发送邮件使用的传输协议
        props.setProperty(MAIL_SMTP_AUTH, TRUE); // 设置使用验证
        props.setProperty(MAIL_SMTP_STARTTLS_ENABLE, TRUE); // 使用STARTTLS安全连接

        Session session = Session.getInstance(props);
        Address addressFrom = new InternetAddress(from); // 发件人邮箱
        Address addressTo = new InternetAddress(to); // 收件人邮箱

        MimeMessage message = new MimeMessage(session);
        message.setFrom(addressFrom);
        message.setRecipient(MimeMessage.RecipientType.TO, addressTo);
        message.setSubject(subject); // 设置邮件的主题
        message.setText(msg); // 设置邮件的正文

        Transport transport = session.getTransport(); // 得到传输对象
        transport.connect(server, from, pwd); // 发送邮件的服务器地址(SMTP), 邮箱, 密码
        transport.sendMessage(message, message.getAllRecipients()); // 设置需要发送的信息以及收件人地址
        transport.close();
    }
}
