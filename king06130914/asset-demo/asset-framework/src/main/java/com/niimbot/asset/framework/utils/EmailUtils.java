package com.niimbot.asset.framework.utils;

import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

import cn.hutool.core.collection.CollUtil;
import com.niimbot.asset.framework.model.MailSenderDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@EqualsAndHashCode(callSuper = true)
@Slf4j
@Component
@Data
@ConfigurationProperties(prefix = "email")
public class EmailUtils extends Thread {

    /**
     * 邮箱服务器地址
     */
    private String host;

    /**
     * 邮箱服务器端口
     */
    private String port = "25";

    /**
     * 邮箱账号
     */
    private String from;

    /**
     * 登陆邮件发送服务器的用户名和密码
     */
    private String userName;
    private String password;

    /**
     * 是否需要身份验证
     */
    private boolean validate = true;

    /***
     * 以HTML格式发送邮件
     * @param mailInfo
     *     待发送的邮件信息
     */
    public boolean sendMail(MailSenderDto mailInfo) {
        //判断是否需要身份认证
        MyAuthenticator authenticator = null;
        Properties pro = new Properties();
        pro.put("mail.smtp.host", host);
        pro.put("mail.smtp.port", port);
        pro.put("mail.smtp.auth", validate ? "true" : "false");
        //如果需要身份认证；则创建一个密码验证器
        if (validate) {
            //如果需要身份认证；则创建一个密码验证器
            authenticator = new MyAuthenticator(userName, password);
        }

        try {
            //根据邮件会话属性和密码验证器构造一个邮件发送的session
            Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
            Transport transport = sendMailSession.getTransport();
            // 连接邮件服务器：邮箱类型，帐号，授权码代替密码（更安全）
            transport.connect(host, from, password);//后面的字符是授权码
            // 根据session 创建一个邮件消息
            Message mailMessage = new MimeMessage(sendMailSession);
            //设置邮件消息的发送者
            mailMessage.setFrom(new InternetAddress(from));
            //创建邮件的接受者地址；并设置到邮件消息中
            //Message.RecipientType.TO表示接收者的类型为TO
            mailMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(mailInfo.getTo()));
            //设置邮件消息的主题
            mailMessage.setSubject(mailInfo.getSubject());
            //设置邮件消息发送的时间
            mailMessage.setSentDate(new Date());
            //设置邮件消息的主要内容

            //MiniMultipart 类是一个容器 包含MimeBodyPart类型的对象
            Multipart mailPart = new MimeMultipart();
            //创建一个包含HTNL内容的MimeBodyPart
            BodyPart html = new MimeBodyPart();
            //设置HTML内容
            html.setContent(mailInfo.getContent(), "text/html;charset=utf-8");
            mailPart.addBodyPart(html);

            // 判断是否有附件
            List<String> attachFileNames = mailInfo.getAttachFileNames();
            if (CollUtil.isNotEmpty(attachFileNames)) {
                for (String attachFileName : attachFileNames) {
                    // 设置信件的附件（用本地上的文件作为附件）
                    html = new MimeBodyPart();
                    DataHandler dh = new DataHandler(new FileDataSource(attachFileName));
                    //对文件名进行编码，防止出现乱码
                    String fileName = MimeUtility.encodeWord(dh.getName(), "utf-8", "B");
                    html.setFileName(fileName);
                    html.setDataHandler(dh);
                    mailPart.addBodyPart(html);
                }
            }

            //将MiniMultipart对象设置为邮件内容
            mailMessage.setContent(mailPart);
            mailMessage.saveChanges();

            //发送邮件
            transport.sendMessage(mailMessage, mailMessage.getAllRecipients());
            transport.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static class MyAuthenticator extends Authenticator {

        String userName = null;
        String password = null;

        public MyAuthenticator() {

        }

        public MyAuthenticator(String userName, String password) {
            this.userName = userName;
            this.password = password;
        }

        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(userName, password);

        }
    }
}