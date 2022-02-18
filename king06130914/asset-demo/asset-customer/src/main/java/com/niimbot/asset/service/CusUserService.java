package com.niimbot.asset.service;

import com.niimbot.asset.framework.model.CusUserDto;

public interface CusUserService {
    /**
     * 通过用户名查询用户
     *
     * @param account 用户名
     * @return 用户对象信息
     */
    CusUserDto selectUserByAccount(String account);

}
