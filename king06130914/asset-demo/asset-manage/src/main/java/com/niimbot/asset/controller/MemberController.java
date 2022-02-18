package com.niimbot.asset.controller;


import com.niimbot.asset.framework.core.controller.BaseController;
import com.niimbot.asset.framework.utils.PageUtils;
import com.niimbot.asset.service.feign.UserFeignClient;
import com.niimbot.jf.core.component.annotation.ResultController;
import com.niimbot.system.UserDto;
import com.niimbot.system.UserPageQueryDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 菜单管理模块
 */
@Api(tags = "菜单管理接口")
@ResultController
@RequestMapping("admin/member")
public class MemberController extends BaseController {

    @Autowired
    private UserFeignClient userFeignClient;

    /**
     * 会员分页查询
     *
     * @param queryDto 分页、查询参数
     * @return pageUtils
     */
    @ApiOperation(value = "会员分页列表查询")
    @PostMapping(value = "/list")
    public PageUtils<UserDto> page(UserPageQueryDto queryDto) {
        return userFeignClient.selectPage(queryDto);
    }

}

