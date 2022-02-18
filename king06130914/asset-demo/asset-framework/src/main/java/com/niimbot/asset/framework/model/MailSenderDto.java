package com.niimbot.asset.framework.model;

import lombok.Data;

import java.util.List;

@Data
public class MailSenderDto {

    //邮件接收者的地址
    private String to;
    //邮件发送主题
    private String subject;
    //邮件的文本内容
    private String content;
    //邮件附件的文件名
    private List<String> attachFileNames;

}