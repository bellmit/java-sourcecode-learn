package io.github.talelin.latticy.common.listener;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.talelin.autoconfigure.bean.MetaInfo;
import io.github.talelin.autoconfigure.bean.PermissionMetaCollector;
import io.github.talelin.latticy.model.PermissionDO;
import io.github.talelin.latticy.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 检查 数据库中存储的内容和 通过全局扫描注解的方式获取到的注解内容是否一致，如果不一致就将数据库中的记录关闭
 * @author pedro@TaleLin
 * @author colorful@TaleLin
 * 执行在 全局注解扫描完毕之后
 */
@Component
public class PermissionHandleListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private PermissionService permissionService;

    // 包含所有的 PermissionMeta 注解信息
    @Autowired
    private PermissionMetaCollector metaCollector;

    // 容器中发布事件后，此方法会触发
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        addNewPermissions();
        removeUnusedPermissions();
    }

    private void addNewPermissions() {
        metaCollector.getMetaMap().values().forEach(meta -> {
            String module = meta.getModule();
            String permission = meta.getPermission();
            createPermissionIfNotExist(permission, module);
        });
    }

    private void removeUnusedPermissions() {
        // PermissionDO(name=删除图书, module=图书, mount=true)
        // PermissionDO(name=查询所有日志, module=日志, mount=true)
        // PermissionDO(name=搜索日志, module=日志, mount=true)
        // PermissionDO(name=查询日志记录的用户, module=日志, mount=true)
        List<PermissionDO> allPermissions = permissionService.list();

        // metaMap 以 方法名作为 key
        Map<String, MetaInfo> metaMap = metaCollector.getMetaMap();

        // 对比 metaMap 中的内容 和 从数据库中查出来的内容，判断是否相同
        for (PermissionDO permission : allPermissions) {
            boolean stayedInMeta = metaMap
                    .values()
                    .stream()
                    .anyMatch(meta -> meta.getModule().equals(permission.getModule())
                            && meta.getPermission().equals(permission.getName()));

            // 如果不相同，就更新数据库，把 metaMap 数据库中的内容 mount 状态设置为 false，即关闭
            if (!stayedInMeta) {
                permission.setMount(false);
                permissionService.updateById(permission);
            }
        }
    }

    /**
     *
     * @param name    value
     * @param module  module
     */
    private void createPermissionIfNotExist(String name, String module) {
        // 从数据库中去查 是否存在
        QueryWrapper<PermissionDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(PermissionDO::getName, name).eq(PermissionDO::getModule, module);

        // SELECT id,name,module,mount,create_time,update_time,delete_time FROM lin_permission
        //          WHERE delete_time IS NULL AND (name = ? AND module = ?)

        // PermissionDO(name=搜索日志, module=日志, mount=true)
        PermissionDO permission = permissionService.getOne(wrapper);
        if (permission == null) {
            permissionService.save(PermissionDO.builder().module(module).name(name).build());
        }
        if (permission != null && !permission.getMount()) {
            permission.setMount(true);
            permissionService.updateById(permission);
        }
    }
}
