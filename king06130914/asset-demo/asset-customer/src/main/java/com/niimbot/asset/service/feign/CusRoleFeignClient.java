package com.niimbot.asset.service.feign;

import com.niimbot.system.CusRoleDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(url = "${feign.remote}", name = "asset-demo")
public interface CusRoleFeignClient {

    /**
     * 根据用户Id查询组织详情
     *
     * @param userId 用户Id
     * @return 组织数据
     */
    @GetMapping(value = "server/system/cusRole/user/{userId}")
    List<CusRoleDto> getRoleByUserId(@PathVariable("userId") Long userId);

}
