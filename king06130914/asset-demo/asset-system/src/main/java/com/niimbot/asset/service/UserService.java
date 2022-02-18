package com.niimbot.asset.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.niimbot.asset.model.AsUser;
import com.niimbot.system.UserDto;
import com.niimbot.system.UserPageQueryDto;


public interface UserService extends IService<AsUser> {

    /**
     * 会员列表查询
     *
     * @param page     分页对象
     * @param queryDto 账号条件
     * @return 分页数据
     */
    IPage<UserDto> selectPage(Page<UserDto> page, UserPageQueryDto queryDto);
}
