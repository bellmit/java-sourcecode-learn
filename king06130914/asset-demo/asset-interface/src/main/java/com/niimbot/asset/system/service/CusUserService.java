package com.niimbot.asset.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.niimbot.asset.framework.model.CusUserDto;
import com.niimbot.asset.system.model.AsCusUser;

/**
 * @author jc
 * @Date 2020/10/30
 */
public interface CusUserService extends IService<AsCusUser> {

    /**
     * 根据用户名查询用户信息
     *
     * @param account 登录名称
     * @return 用户信息
     */
    CusUserDto selectUserByAccount(String account);

    /**
     * 登录后置处理
     *
     * @param userId 用户Id
     */
    void loginAfterRecord(Long userId);
}
