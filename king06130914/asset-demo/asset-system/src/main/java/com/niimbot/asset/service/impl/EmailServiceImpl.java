package com.niimbot.asset.service.impl;

import com.google.common.collect.Lists;
import com.niimbot.asset.framework.model.MailSenderDto;
import com.niimbot.asset.framework.utils.EmailUtils;
import com.niimbot.asset.framework.utils.NumberUtils;
import com.niimbot.asset.system.service.EmailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author dk
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Resource
    private EmailUtils emailUtils;

    @Override
    public Boolean sendEmail(String email) {
        try {
            String code = NumberUtils.generateCode(6);
            String subject = "test";
            String content = "【test】验证码是："+code+"<br>您正在使用注册功能，该验证码仅用于注册验证，请勿泄露给他人使用。5分钟内有效！<br>";
            //这个类主要是设置邮件
            MailSenderDto mailInfo = new MailSenderDto();
            mailInfo.setTo(email);
            mailInfo.setSubject(subject);//设置邮箱标题
            mailInfo.setContent(content);//设置邮箱内容

            List<String> attachFileNames = Lists.newArrayList();
            attachFileNames.add("/Users/dingke/Documents/import_asset.ini");
            attachFileNames.add("/Users/dingke/Downloads/21天生酮饮食食谱.pdf");
            mailInfo.setAttachFileNames(attachFileNames);
            return emailUtils.sendMail(mailInfo);
        } catch (Exception e) {
            return false;
        }
    }
}
