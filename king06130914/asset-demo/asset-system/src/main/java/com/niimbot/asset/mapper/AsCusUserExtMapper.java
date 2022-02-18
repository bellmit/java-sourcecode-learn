package com.niimbot.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.niimbot.asset.system.model.AsCusUserExt;
import com.niimbot.jf.mybatisplus.autoconfigure.cache.MybatisRedisCache;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.CacheNamespaceRef;
import org.apache.ibatis.annotations.Property;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 用户自定义设置表 Mapper 接口
 * </p>
 *
 * @author xie.wei
 * @since 2020-12-18
 */
@CacheNamespace(implementation = MybatisRedisCache.class, properties = {@Property(name = "flushInterval", value = "3600000")})
@CacheNamespaceRef(AsCusUserExtMapper.class)
public interface AsCusUserExtMapper extends BaseMapper<AsCusUserExt> {

    /**
     * 更新用户最后登录时间
     *
     * @param userId 用户Id
     */
    @Update("update as_cus_user_ext set last_login_time = current_login_time where id = #{userId}")
    void updateLastLoginTime(Long userId);

}
