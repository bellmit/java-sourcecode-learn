package com.niimbot.asset.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.niimbot.asset.mapper.AsAdminUserMapper;
import com.niimbot.asset.model.AsAdminUser;
import com.niimbot.asset.service.AdminUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class AdminUserServiceImpl extends ServiceImpl<AsAdminUserMapper, AsAdminUser> implements AdminUserService {

    @Resource
    private AsAdminUserMapper adminUserMapper;

    /**
     * 根据用户名查询用户信息
     *
     * @param username 登录名称
     * @return 用户信息
     */
    @Override
    public Map<String, Object> selectUserByUserName(String username) {
        return adminUserMapper.queryUserByUsername(username);
    }

}
