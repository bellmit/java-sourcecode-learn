package com.niimbot.asset.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.niimbot.asset.framework.web.LoginUserThreadLocal;
import com.niimbot.asset.mapper.AsAdminUserMapper;
import com.niimbot.asset.mapper.AsUserMapper;
import com.niimbot.asset.model.AsAdminUser;
import com.niimbot.asset.model.AsUser;
import com.niimbot.asset.service.AdminUserService;
import com.niimbot.asset.service.UserService;
import com.niimbot.system.UserDto;
import com.niimbot.system.UserPageQueryDto;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<AsUserMapper, AsUser> implements UserService {

    @Resource
    private AsUserMapper userMapper;

//    @Resource
//    private AsUserMapper userMapper;

    @Override
    public IPage<UserDto> selectPage(Page<UserDto> page, UserPageQueryDto queryDto) {
        IPage<UserDto> pageData = userMapper.selectPage(page, queryDto);
        List<UserDto> records = pageData.getRecords();



//        records.parallelStream().forEach(UserDto -> {
//            List<DataAuthorityDto> authorityList = cusAccountDto.getAuthorityList();
//            Map<String, List<Long>> authorityMap = Maps.newHashMap();
//            if (CollUtil.isNotEmpty(authorityList)) {
//                authorityList.parallelStream().forEach(dataAuthorityDto -> authorityMap
//                        .put(dataAuthorityDto.getAuthorityCode(), dataAuthorityDto.getAuthorityData()));
//                cusAccountDto.setAuthorityMap(authorityMap);
//            }
//        });
        return pageData;
    }

}
