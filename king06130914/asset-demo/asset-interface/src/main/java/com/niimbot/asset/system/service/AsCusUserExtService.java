package com.niimbot.asset.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.niimbot.asset.system.model.AsCusUserExt;

public interface AsCusUserExtService extends IService<AsCusUserExt> {

    /**
     * 更新用户最后登录时间
     *
     * @param userId 用户Id
     */
    void updateLastLoginTime(Long userId);
}
