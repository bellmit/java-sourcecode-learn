package com.niimbot.asset.service.feign;

import com.niimbot.system.AdminNodeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(url = "${feign.remote}", name = "asset-demo")
public interface AdminNodeFeignClient {

    /**
     * 查询菜单列表
     *
     * @return 菜单集合
     */
    @GetMapping(value = "server/system/adminMenu/list")
    List<AdminNodeDto> selectMenuList();

    /**
     * 根据菜单Id查询菜单
     * @param menuId 菜单id
     * @return 结果
     */
    @GetMapping(value = "server/system/adminMenu/{menuId}")
    AdminNodeDto selectMenuById(@PathVariable("menuId") Long menuId);

    /**
     * 根据用户ID查询权限
     * @param userId 用户Id
     * @return 结果
     */
    @GetMapping(value = "server/system/adminMenu/getMenuPermsByUserId/{userId}")
    List<String> selectMenuPermsByUserId(@PathVariable("userId") Long userId);

    /**
     * 新增菜单数据
     *
     * @param menuDto 菜单数据
     * @return 结果
     */
    @PostMapping(value = "server/system/adminMenu")
    Boolean insertMenu(AdminNodeDto menuDto);

    /**
     * 编辑菜单数据
     * @param menuDto 菜单数据
     * @return 结果
     */
    @PutMapping(value = "server/system/adminMenu")
    Boolean updateMenu(AdminNodeDto menuDto);

    /**
     * 批量删除菜单
     * @param menuIds 菜单数据
     * @return 结果
     */
    @DeleteMapping(value = "server/system/adminMenu")
    Boolean deleteMenuByIds(List<Long> menuIds);

    /**
     * 查询菜单列表
     *
     * @return 菜单集合
     */
    @GetMapping(value = "server/system/adminMenu/selectAllMenuList")
    List<AdminNodeDto> selectAllMenuList();

    /**
     * 查询按钮权限
     *
     * @return 按钮权限集合
     */
    @GetMapping(value = "server/system/adminMenu/selectButtonList")
    Map<String, Object> selectButtonList();
}
