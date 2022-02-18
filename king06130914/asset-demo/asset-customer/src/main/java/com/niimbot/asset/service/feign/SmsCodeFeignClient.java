package com.niimbot.asset.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 短信验证码客户端
 *
 * @author dk
 */
@FeignClient(url = "${feign.remote}", name = "asset-demo")
public interface SmsCodeFeignClient {

    /**
     * 发送短信验证码
     *
     * @param mobile  手机号
     */
    @GetMapping("server/system/sms/send/{mobile}")
    Boolean sendSmsCode(@PathVariable("mobile") String mobile);

    /**
     * 发送短信验证码
     *
     * @param email  邮箱
     */
    @GetMapping("server/system/email/send")
    Boolean sendEmailCode(@RequestParam("email") String email);
}
