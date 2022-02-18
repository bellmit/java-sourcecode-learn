package com.niimbot.asset.controller;

import com.niimbot.asset.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("server/system/adminUser")
public class AdminUserServiceController {

    @Autowired
    private AdminUserService adminUserService;

    @GetMapping("/getByUserName")
    public Map<String, Object> getUserByUserName(@RequestParam String username) {
        return adminUserService.selectUserByUserName(username);
    }
}
