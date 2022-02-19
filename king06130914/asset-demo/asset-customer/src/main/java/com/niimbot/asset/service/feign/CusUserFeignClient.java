package com.niimbot.asset.service.feign;

import com.niimbot.asset.framework.model.CusUserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


/**
 * created by chen.y on 2020/11/4 15:18
 *
 * @author chen.y
 */
@FeignClient(url = "${feign.remote}", name = "asset-demo")
public interface CusUserFeignClient {

    /**
     * 根据登录名称查询用户
     *
     * @param account 登录名
     * @return 结果
     */
    @GetMapping(value = "server/system/cusUser/getByAccount")
    CusUserDto selectUserByAccount(@RequestParam("account") String account);

    /**
     * 登录后置处理
     *
     * @param userId 用户Id
     */
    @PostMapping(value = "server/system/cusUser/loginAfterRecord")
    void loginAfterRecord(Long userId);
}
