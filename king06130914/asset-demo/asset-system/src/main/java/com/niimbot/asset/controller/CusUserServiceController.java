package com.niimbot.asset.controller;

import com.niimbot.asset.framework.model.CusUserDto;
import com.niimbot.asset.system.service.CusUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("server/system/cusUser")
@Slf4j
public class CusUserServiceController {

    @Resource
    private CusUserService userService;

    @GetMapping("/getByAccount")
    public CusUserDto getUserByAccount(@RequestParam String account) {
        return userService.selectUserByAccount(account);
    }

    /**
     * 登录后置处理
     *
     * @param userId 用户Id
     */
    @PostMapping("loginAfterRecord")
    void loginAfterRecord(@RequestBody Long userId) {
        userService.loginAfterRecord(userId);
    }

}