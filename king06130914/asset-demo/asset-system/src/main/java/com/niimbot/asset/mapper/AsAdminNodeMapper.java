package com.niimbot.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.niimbot.asset.model.AsAdminNode;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 后台 (菜单)权限节点表 Mapper 接口
 * </p>
 *
 * @author dk
 * @since 2021-02-16
 */
@Mapper
public interface AsAdminNodeMapper extends BaseMapper<AsAdminNode> {

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    List<String> selectMenuPermsByUserId(Long userId);

    /**
     * 根据用户查询系统菜单列表
     *
     * @param params 参数
     * @return 菜单列表
     */
    List<AsAdminNode> selectMenuListByUserId(Map<String, Object> params);

    String getButtonConfig();
}
