package com.niimbot.asset.system.service;

/**
 * 邮箱
 *
 * @author dk
 */
public interface EmailService {
    /**
     * 发送验证码
     *
     * @param email  邮箱
     */
    Boolean sendEmail(String email);

}
