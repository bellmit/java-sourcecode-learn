package com.niimbot.asset.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.niimbot.asset.model.AsAdminUser;

import java.util.Map;


public interface AdminUserService extends IService<AsAdminUser> {

    /**
     * 根据用户名查询用户信息
     *
     * @param username 登录名称
     * @return 用户信息
     */
    Map<String, Object> selectUserByUserName(String username);

}
