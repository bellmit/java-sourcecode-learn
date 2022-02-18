package com.niimbot.asset.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.niimbot.asset.service.UserService;
import com.niimbot.system.UserDto;
import com.niimbot.system.UserPageQueryDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("server/system/member")
public class UserServiceController {

    @Resource
    private UserService userService;

    /**
     * companyId
     *
     * @param queryDto queryDto
     * @return pageUtils
     */
    @PostMapping(value = "/page")
    public IPage<UserDto> page(UserPageQueryDto queryDto) {
        return userService.selectPage(queryDto.buildIPage(), queryDto);
    }
}
