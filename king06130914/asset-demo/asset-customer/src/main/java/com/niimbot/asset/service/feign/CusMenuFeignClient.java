package com.niimbot.asset.service.feign;

import com.niimbot.system.AppCusMenuDto;
import com.niimbot.system.CusMenuDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * created by chen.y on 2020/10/27 11:36
 *
 * @author chen.y
 */
@FeignClient(url = "${feign.remote}", name = "asset-demo")
public interface CusMenuFeignClient {

    /**
     * 配置角色菜单权所用列表
     *
     * @return 菜单集合
     */
    @GetMapping(value = "server/system/cusMenu/configRoleMenu/pc")
    List<CusMenuDto> configRoleMenuPcList();

    /**
     * 配置角色菜单权所用列表
     *
     * @return 菜单集合
     */
    @GetMapping(value = "server/system/cusMenu/configRoleMenu/app")
    List<CusMenuDto> configRoleMenuAppList();

    /**
     * 根据用户权限查询菜单树显示列表
     *
     * @return 菜单集合
     */
    @GetMapping(value = "server/system/cusMenu/userMenu/pc")
    List<CusMenuDto> userMenuPcList();

    /**
     * 根据用户权限查询菜单树显示列表
     *
     * @return 菜单集合
     */
    @GetMapping(value = "server/system/cusMenu/userMenu/app")
    AppCusMenuDto userMenuAppList();

    /**
     * 根据菜单Id查询菜单
     *
     * @param menuId 菜单id
     * @return 结果
     */
    @GetMapping(value = "server/system/cusMenu/{menuId}")
    CusMenuDto selectMenuById(@PathVariable("menuId") Long menuId);

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户Id
     * @return 结果
     */
    @GetMapping(value = "server/system/cusMenu/getMenuPermsByUserId/{userId}")
    List<String> selectMenuPermsByUserId(@PathVariable("userId") Long userId);

    /**
     * 新增菜单数据
     *
     * @param menuDto 菜单数据
     * @return 结果
     */
    @PostMapping(value = "server/system/cusMenu")
    Boolean insertMenu(CusMenuDto menuDto);

    /**
     * 编辑菜单数据
     *
     * @param menuDto 菜单数据
     * @return 结果
     */
    @PutMapping(value = "server/system/cusMenu")
    Boolean updateMenu(CusMenuDto menuDto);

    /**
     * 批量删除菜单
     *
     * @param menuIds 菜单数据
     * @return 结果
     */
    @DeleteMapping(value = "server/system/cusMenu")
    Boolean deleteMenuByIds(List<Long> menuIds);
}
