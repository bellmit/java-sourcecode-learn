package com.niimbot.asset.controller;


import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeUtil;
import com.niimbot.asset.framework.core.controller.BaseController;
import com.niimbot.asset.service.feign.AdminNodeFeignClient;
import com.niimbot.jf.core.component.annotation.ResultController;
import com.niimbot.system.AdminNodeDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * 菜单管理模块
 */
@Api(tags = "菜单管理接口")
@ResultController
@RequestMapping("admin")
public class AdminMenuController extends BaseController {

    @Autowired
    private AdminNodeFeignClient adminNodeFeignClient;


    @ApiOperation(value = "查询当前用户菜单树")
    @GetMapping("/menu")
    public List<Tree<String>> userMenuTree() {
        List<AdminNodeDto> menus = adminNodeFeignClient.selectMenuList();
        return buildMenusTree(menus);
    }

    private List<Tree<String>> buildMenusTree(List<AdminNodeDto> menus) {
        // 构建树结构
        return TreeUtil.build(menus, "0", (menu, tree) -> {
            tree.setId(Convert.toStr(menu.getId()));
            tree.setParentId(Convert.toStr(menu.getPid()));
//            tree.setName(menu.getMenuName());
//            tree.setWeight(menu.getSortNum());
            tree.putExtra("menu_class", menu.getMenuClass());
            tree.putExtra("menu_icon", menu.getMenuIcon());
            tree.putExtra("menu_name", menu.getMenuName());
            tree.putExtra("node_name", menu.getNodeName());
            tree.putExtra("pid", menu.getPid());
            tree.putExtra("sort_num", menu.getSortNum());
        });
    }

    @ApiOperation(value = "查询当前用户菜单树")
    @GetMapping("/common/buttonPower")
    public Map<String, Object> getButtonPower() {
        Map<String, Object> buttonList = adminNodeFeignClient.selectButtonList();
        return buttonList;
    }

}

