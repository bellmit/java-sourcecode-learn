package com.niimbot.asset.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


@FeignClient(url = "${feign.remote}", name = "asset-demo")
public interface AdminUserFeignClient {

    /**
     * 根据登录名称查询用户
     * @param username 登录名
     * @return 结果
     */
    @GetMapping(value = "server/system/adminUser/getByUserName")
    Map<String, Object> selectUserByUserName(@RequestParam("username") String username);

}
