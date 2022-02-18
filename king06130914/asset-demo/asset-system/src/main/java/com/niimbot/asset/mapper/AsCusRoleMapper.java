package com.niimbot.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.niimbot.asset.system.model.AsCusRole;
import com.niimbot.jf.mybatisplus.autoconfigure.cache.MybatisRedisCache;
import com.niimbot.system.CusRoleDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@CacheNamespace(implementation = MybatisRedisCache.class, properties = {@Property(name = "flushInterval", value = "3600000")})
@CacheNamespaceRef(AsCusRoleMapper.class)
public interface AsCusRoleMapper extends BaseMapper<AsCusRole> {

    /**
     * 查询获取角色结构
     *
     * @param userId 用户ID
     * @return CusRoleDto信息
     */
    List<CusRoleDto> getRoleByUserId(@Param("userId") Long userId);

}
