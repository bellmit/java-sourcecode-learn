package com.niimbot.asset.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.niimbot.asset.mapper.AsCusRoleMapper;
import com.niimbot.asset.system.model.AsCusRole;
import com.niimbot.asset.system.service.CusRoleService;
import com.niimbot.system.CusRoleDto;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CusRoleServiceImpl extends ServiceImpl<AsCusRoleMapper, AsCusRole> implements CusRoleService {

    /**
     * 查询获取角色信息
     *
     * @param userId 用户ID
     * @return CusRoleDto信息
     */
    @Override
    public List<CusRoleDto> getRoleByUserId(Long userId) {
        return this.getBaseMapper().getRoleByUserId(userId);
    }
}
