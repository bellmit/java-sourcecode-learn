package com.niimbot.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.niimbot.asset.system.model.AsSmsInfo;
import com.niimbot.jf.mybatisplus.autoconfigure.cache.MybatisRedisCache;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.CacheNamespaceRef;
import org.apache.ibatis.annotations.Property;

/**
 * <p>
 * 短信发送信息记录表 Mapper 接口
 * </p>
 *
 * @author dk
 * @since 2021-03-26
 */
@CacheNamespace(implementation = MybatisRedisCache.class, properties = {@Property(name = "flushInterval", value = "3600000")})
@CacheNamespaceRef(AsSmsInfoMapper.class)
public interface AsSmsInfoMapper extends BaseMapper<AsSmsInfo> {

}
