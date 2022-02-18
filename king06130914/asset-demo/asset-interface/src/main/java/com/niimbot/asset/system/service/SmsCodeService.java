package com.niimbot.asset.system.service;

/**
 * 验证码
 *
 * @author dk
 */
public interface SmsCodeService {
    /**
     * 发送验证码
     *
     * @param mobile  手机号
     */
    Boolean sendSmsCode(String mobile);

}
