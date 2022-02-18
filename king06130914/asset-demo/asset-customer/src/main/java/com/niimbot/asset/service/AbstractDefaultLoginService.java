package com.niimbot.asset.service;

import com.niimbot.asset.framework.service.RedisService;
import com.niimbot.asset.service.feign.CusUserFeignClient;
import com.niimbot.asset.service.feign.DataAuthorityFeignClient;

/**
 * 登录后置处理 created by chen.y on 2021/3/26 14:58
 */
public abstract class AbstractDefaultLoginService extends AbstractLoginService {

    private final CusUserFeignClient userFeignClient;

    public AbstractDefaultLoginService(DataAuthorityFeignClient dataAuthorityFeignClient,
                                       RedisService redisService,
                                       CusUserFeignClient userFeignClient) {
        super(dataAuthorityFeignClient, redisService);
        this.userFeignClient = userFeignClient;
    }

    /**
     * 登录后处理记录 记录当前登录时间，上次登录时间，登录次数
     */
    protected void loginAfterRecord(Long userId) {
        userFeignClient.loginAfterRecord(userId);
    }

}
