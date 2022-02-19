package com.niimbot.asset.controller;

import com.niimbot.asset.system.model.AsSmsInfo;
import com.niimbot.asset.system.service.AsSmsInfoService;
import com.niimbot.asset.system.service.SmsCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短信验证码控制器
 *
 * @author dk
 */
@RestController
@RequestMapping("server/system/sms")
@RequiredArgsConstructor
public class SmsCodeServiceController {

    private final SmsCodeService smsCodeService;

    @GetMapping("/send/{mobile}")
    public Boolean sendSmsCode(@PathVariable("mobile") String mobile) {
        return smsCodeService.sendSmsCode(mobile);
    }
}
