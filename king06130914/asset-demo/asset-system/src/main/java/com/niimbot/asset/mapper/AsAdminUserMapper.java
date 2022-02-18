package com.niimbot.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.niimbot.asset.model.AsAdminNode;
import com.niimbot.asset.model.AsAdminUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户账号表 Mapper 接口
 * </p>
 *
 * @author dk
 * @since 2021-02-16
 */
@Mapper
public interface AsAdminUserMapper extends BaseMapper<AsAdminUser> {

    /**
     * 根据用户名查询用户信息
     *
     * @param username 登录名称
     * @return 用户信息
     */
    Map<String, Object> queryUserByUsername(@Param("username") String username);

    List<AsAdminNode> userFeRoute(Long currentAdminUserId);
}
