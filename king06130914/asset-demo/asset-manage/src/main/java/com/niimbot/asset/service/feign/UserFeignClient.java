package com.niimbot.asset.service.feign;

import com.niimbot.asset.framework.utils.PageUtils;
import com.niimbot.system.UserDto;
import com.niimbot.system.UserPageQueryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(url = "${feign.remote}", name = "asset-demo")
public interface UserFeignClient {

//    /**
//     * 根据登录名称查询用户
//     * @param username 登录名
//     * @return 结果
//     */
//    @GetMapping(value = "server/system/adminUser/getByUserName")
//    Map<String, Object> selectUserByUserName(@RequestParam("username") String username);

    /**
     * 账号列表查询
     *
     * @param queryDto 分页参数或查询参数
     * @return 分页数据
     */
    @PostMapping(value = "server/system/member/page")
    PageUtils<UserDto> selectPage(@SpringQueryMap UserPageQueryDto queryDto);

}
