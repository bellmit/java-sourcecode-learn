package com.niimbot.asset.controller;

import com.niimbot.asset.system.service.CusRoleService;
import com.niimbot.system.CusRoleDto;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * created by chen.y on 2020/10/27 14:42
 */
@RestController
@RequestMapping("server/system/cusRole")
public class CusRoleServiceController {

    @Resource
    private CusRoleService cusRoleService;

    /**
     * 查询获取角色
     *
     * @param userId 用户ID
     * @return RoleDto信息
     */
    @GetMapping(value = "/user/{userId}")
    public List<CusRoleDto> getRoleByUserId(@PathVariable("userId") Long userId) {
        return cusRoleService.getRoleByUserId(userId);
    }
}
