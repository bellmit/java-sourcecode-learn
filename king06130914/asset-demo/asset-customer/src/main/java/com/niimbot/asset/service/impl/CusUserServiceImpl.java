package com.niimbot.asset.service.impl;

import com.niimbot.asset.framework.constant.BaseConstant;
import com.niimbot.asset.framework.model.CusUserDto;
import com.niimbot.asset.service.CusLoginService;
import com.niimbot.asset.service.CusUserService;
import com.niimbot.asset.service.feign.CusRoleFeignClient;
import com.niimbot.asset.service.feign.CusUserFeignClient;
import com.niimbot.system.CusRoleDto;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CusUserServiceImpl implements CusUserService {
    private final CusUserFeignClient cusUserFeignClient;
    private final CusLoginService loginService;
    private final CusRoleFeignClient roleFeignClient;

    public CusUserServiceImpl(CusUserFeignClient cusUserFeignClient,
                              @Lazy CusLoginService loginService,
                              CusRoleFeignClient roleFeignClient) {
        this.cusUserFeignClient = cusUserFeignClient;
        this.loginService = loginService;
        this.roleFeignClient = roleFeignClient;
    }

    @Override
    public CusUserDto selectUserByAccount(String account) {
        // 查询用户
        CusUserDto cusUserDto = cusUserFeignClient.selectUserByAccount(account);
        if (cusUserDto != null) {
            // 查询角色
            List<CusRoleDto> roleList = roleFeignClient.getRoleByUserId(cusUserDto.getId());
            // 判断是否超级管理员
            List<String> roleNames = roleList.stream().map(CusRoleDto::getRoleCode).collect(Collectors.toList());
            cusUserDto.setIsAdmin(roleNames.contains(BaseConstant.ADMIN_ROLE));
        }
        return cusUserDto;
    }

}
