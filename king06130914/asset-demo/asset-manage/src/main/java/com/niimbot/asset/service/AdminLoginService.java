package com.niimbot.asset.service;


import com.niimbot.asset.model.LoginUser;
import com.niimbot.system.AdminUserDto;

public interface AdminLoginService {
    /**
     * 登录验证
     *
     * @param account 用户名
     * @param password 密码
     * @return 结果
     */
    String login(String account, String password);

    /**
     * 获取登录用户信息
     *
     * @return 登录用户
     */
    LoginUser getLoginUser();

    /**
     * 通过用户名查询用户
     *
     * @param username 用户名
     * @return 用户对象信息
     */
    AdminUserDto selectUserByUserName(String username);

}
