package com.niimbot.asset.security.service;

import com.niimbot.asset.framework.model.CusUserDto;
import com.niimbot.asset.service.feign.CusMenuFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class SysPermissionService {

    @Autowired
    private CusMenuFeignClient cusMenuFeignClient;

    /**
     * 获取菜单数据权限
     *
     * @param user 用户信息
     * @return 菜单权限信息
     */
    public Set<String> getMenuPermission(CusUserDto user) {
        Set<String> perms = new HashSet<>();
        // 管理员拥有所有权限
        if (user.getIsAdmin()) {
            perms.add("*:*:*");
        } else {
            List<String> permList = cusMenuFeignClient.selectMenuPermsByUserId(user.getId());
            perms.addAll(permList);
        }
        return perms;
    }

}
