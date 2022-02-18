package com.niimbot.asset.service;

import com.niimbot.asset.framework.model.LoginUserDto;

public interface CusLoginService {
    /**
     * 账号密码登录验证
     *
     * @param account  用户名
     * @param password 密码
     * @param code     验证码
     * @param uuid     唯一标识
     * @param terminal 终端
     * @param pushId   推送id
     * @return 结果
     */
    String loginByPwd(String account, String password, String code, String uuid, String terminal, String pushId);

    /**
     * 手机号登录验证
     *
     * @param mobile   用户名
     * @param sms      验证码
     * @param terminal 终端
     * @param pushId   推送id
     * @return 结果
     */
    String loginByMobile(String mobile, String sms, String terminal, String pushId);

    /**
     * 获取登录用户信息
     *
     * @return 登录用户
     */
    LoginUserDto getLoginUser();

    /**
     * 获取二维码UUID
     *
     * @return 二维码UUID
     */
    String getQrCode();

    /**
     * 剔除登录用户
     */
    void kickOffLoginUser();
}
