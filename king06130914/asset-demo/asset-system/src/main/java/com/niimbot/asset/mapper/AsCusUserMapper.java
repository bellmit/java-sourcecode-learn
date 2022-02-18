package com.niimbot.asset.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.niimbot.asset.framework.model.CusUserDto;
import com.niimbot.asset.system.model.AsCusUser;
import com.niimbot.jf.mybatisplus.autoconfigure.cache.MybatisRedisCache;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 * 客户账号 Mapper 接口
 * </p>
 *
 * @author chen.y
 * @since 2020-10-27
 */
@CacheNamespace(implementation = MybatisRedisCache.class, properties = {@Property(name = "flushInterval", value = "3600000")})
@CacheNamespaceRef(AsCusUserMapper.class)
public interface AsCusUserMapper extends BaseMapper<AsCusUser> {

    /**
     * 根据用户名查询用户信息
     *
     * @param account 登录名称
     * @return 用户信息
     */
    CusUserDto queryUserByAccount(@Param("account") String account);

}
