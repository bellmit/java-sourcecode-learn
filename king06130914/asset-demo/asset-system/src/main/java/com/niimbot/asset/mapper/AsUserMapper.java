package com.niimbot.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.niimbot.asset.model.AsUser;
import com.niimbot.system.UserDto;
import com.niimbot.system.UserPageQueryDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户账号表 Mapper 接口
 * </p>
 *
 * @author dk
 * @since 2021-02-23
 */
@Mapper
public interface AsUserMapper extends BaseMapper<AsUser> {

    /**
     * 账号列表查询
     *
     * @param page      分页对象
     * @param queryDto  账号条件
     * @return 分页数据
     */
    IPage<UserDto> selectPage(Page<UserDto> page, @Param("em") UserPageQueryDto queryDto);
}
