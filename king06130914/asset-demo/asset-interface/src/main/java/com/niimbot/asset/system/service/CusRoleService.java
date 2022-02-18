package com.niimbot.asset.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.niimbot.asset.system.model.AsCusRole;
import com.niimbot.system.CusRoleDto;

import java.util.List;

public interface CusRoleService extends IService<AsCusRole> {

    /**
     * 查询获取角色信息
     *
     * @param userId 用户ID
     * @return CusRoleDto信息
     */
    List<CusRoleDto> getRoleByUserId(Long userId);

}
