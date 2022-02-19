package com.niimbot.asset.framework.utils;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "sms")
public class SmsUtils {

    /**
     * AppID
     */
    private Integer appId;

    /**
     * AppKey
     */
    private String appKey;

    /**
     * 是否开启
     */
    private Boolean enable;

    /**
     * 签名内容
     */
    private String sign;

    public Boolean sendSms(String phoneNumber, String[] params, Integer templateId) {
        try {
            //创建sender对象
            SmsSingleSender sender = new SmsSingleSender(appId, appKey);
            //发送
            SmsSingleSenderResult result = sender.sendWithParam("86", phoneNumber,
                    templateId, params, sign, "", "");
            // 签名参数未提供或者为空时，会使用默认签名发送短信
            System.out.println(result.toString());
            return result.result == 0;
        } catch (Exception e) {
            // HTTP响应码错误
            e.printStackTrace();
            return false;
        }
    }
}