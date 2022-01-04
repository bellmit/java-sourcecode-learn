package io.github.talelin.autoconfigure.bean;

import io.github.talelin.core.annotation.AdminMeta;
import io.github.talelin.core.annotation.GroupMeta;
import io.github.talelin.core.annotation.LoginMeta;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import io.github.talelin.core.enumeration.UserLevel;
import io.github.talelin.core.util.AnnotationUtil;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 路由信息收集器
 *
 * @author pedro@TaleLin
 * @author Juzi@TaleLin
 * @author colorful@TaleLin
 */
public class PermissionMetaCollector implements BeanPostProcessor {

    // 按照每个方法来存注解
    private Map<String, MetaInfo> metaMap = new ConcurrentHashMap<>();

    // 按照每个 类 来存储注解
    private Map<String, Map<String, Set<String>>> structuralMeta = new ConcurrentHashMap<>();

    public PermissionMetaCollector() {
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }

    // 在启动 springboot 时就会进行加载
    /**
     * 扫描注解信息，并提取
     *
     * @param bean     spring bean
     * @param beanName 名称
     * @return spring bean
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        Controller controllerAnnotation = AnnotationUtils.findAnnotation(bean.getClass(), Controller.class);
        // 非 Controller 类，无需检查权限信息
        if (controllerAnnotation == null) {
            return bean;
        }

        Method[] methods = ReflectionUtils.getAllDeclaredMethods(bean.getClass());  // 获取所有的 bean
        for (Method method : methods) {
            AdminMeta adminMeta = AnnotationUtils.findAnnotation(method, AdminMeta.class);
            if (adminMeta != null && adminMeta.mount()) {
                String permission = StringUtils.isEmpty(adminMeta.value())
                        ? adminMeta.permission() : adminMeta.value();
                putOneMetaInfo(method, permission, adminMeta.module(), UserLevel.ADMIN);
                continue;
            }
            GroupMeta groupMeta = AnnotationUtils.findAnnotation(method, GroupMeta.class);
            if (groupMeta != null && groupMeta.mount()) {
                String permission = StringUtils.isEmpty(groupMeta.value())
                        ? groupMeta.permission() : groupMeta.value();
                putOneMetaInfo(method, permission, groupMeta.module(), UserLevel.GROUP);
                continue;
            }
            LoginMeta loginMeta = AnnotationUtils.findAnnotation(method, LoginMeta.class);
            if (loginMeta != null && loginMeta.mount()) {
                String permission = StringUtils.isEmpty(loginMeta.value())
                        ? loginMeta.permission() : loginMeta.value();
                putOneMetaInfo(method, permission, loginMeta.module(), UserLevel.LOGIN);
                continue;
            }
            // 最后寻找 PermissionMeta
            PermissionMeta permissionMeta = AnnotationUtils.findAnnotation(method,
                    PermissionMeta.class);
            if (permissionMeta != null && permissionMeta.mount()) {
                String permission = StringUtils.isEmpty(permissionMeta.value())   // 例：查询日志记录的用户
                        ? permissionMeta.permission() : permissionMeta.value();

                // 通过另一个注解 Required 来获取当前用户的等级
                UserLevel level = AnnotationUtil.findRequired(method.getAnnotations()); // method 指注解所在的方法，例如 LogController
                putOneMetaInfo(method, permission, permissionMeta.module(), level);
            }
        }
        return bean;
    }

    // 将获取到的 PermissionMeta 注解中的信息放入 map 中

    /**
     *
     * @param method 发现注解的 bean
     * @param permission permission.value
     * @param module 暂不知 但是是一个布尔值的值
     * @param userLevel 用户的等级，对应的就是用户的权限
     */
    private void putOneMetaInfo(Method method, String permission, String module,
                                UserLevel userLevel) {
        if (StringUtils.isEmpty(module)) {
            PermissionModule permissionModule = AnnotationUtils.findAnnotation(
                    method.getDeclaringClass(), PermissionModule.class);   // 调用 spring 中的方法 获取注解的信息
            if (permissionModule != null) { // 判断注解是否为空
                // 判断注解中的 value 是否为空，如果为空就设置默认值 默认为 true
                module = StringUtils.isEmpty(permissionModule.value()) ?
                        method.getDeclaringClass().getName() : permissionModule.value();
            }
        }

        // getUsers
        String methodName = method.getName();

        // io.github.talelin.latticy.controller.cms.LogController$$EnhancerBySpringCGLIB$$41041f22
        String className = method.getDeclaringClass().getName();

        // io.github.talelin.latticy.controller.cms.LogController$$EnhancerBySpringCGLIB$$41041f22#getUsers
        String identity = className + "#" + methodName; // 类的全路径 # 方法名

        // 拼接 metainfo
        // MetaInfo
        // {permission='查询日志记录的用户',
        // module='日志',
        // identity='io.github.talelin.latticy.controller.cms.LogController$$EnhancerBySpringCGLIB$$41041f22#getUsers',
        // userLevel=TOURIST}
        MetaInfo metaInfo = new MetaInfo(permission, module, identity, userLevel);
        metaMap.put(identity, metaInfo); // 将 PermissionMeta 注解 都存放到 metaMap 这个字典中去
        this.putMetaIntoStructuralMeta(identity, metaInfo);
    }

    /**
     *
     * @param identity  类全路径 # 方法
     * @param meta 上一个方法中拼接的内容
     */
    private void putMetaIntoStructuralMeta(String identity, MetaInfo meta) {
        String module = meta.getModule();  // 获取 PermissionModule 注解中的 value ，这个注解打在类上面
                                           // 使用这个注解可以省略掉 PermissionMeta 中的 module
        String permission = meta.getPermission();
        // 如果已经存在了该 module，直接向里面增加
        if (structuralMeta.containsKey(module)) {
            Map<String, Set<String>> moduleMap = structuralMeta.get(module);
            // 如果 permission 已经存在
            this.putIntoModuleMap(moduleMap, identity, permission);
        } else {
            // 不存在 该 module，创建该 module
            Map<String, Set<String>> moduleMap = new HashMap<>();
            // 如果 permission 已经存在
            this.putIntoModuleMap(moduleMap, identity, permission);
            structuralMeta.put(module, moduleMap);
        }
    }

    private void putIntoModuleMap(Map<String, Set<String>> moduleMap, String identity,
                                  String auth) {
        if (moduleMap.containsKey(auth)) {
            moduleMap.get(auth).add(identity);
        } else {
            Set<String> eps = new HashSet<>();
            eps.add(identity);
            moduleMap.put(auth, eps);
        }
    }

    /**
     * 获取路由信息map
     *
     * @return 路由信息map
     */
    public Map<String, MetaInfo> getMetaMap() {
        return metaMap;
    }

    public MetaInfo findMeta(String key) {
        return metaMap.get(key);
    }

    public MetaInfo findMetaByPermission(String permission) {
        Collection<MetaInfo> values = metaMap.values();
        MetaInfo[] objects = values.toArray(new MetaInfo[0]);
        for (MetaInfo object : objects) {
            if (object.getPermission().equals(permission)) {
                return object;
            }
        }
        return null;
    }

    public void setMetaMap(Map<String, MetaInfo> metaMap) {
        this.metaMap = metaMap;
    }

    /**
     * 获得结构化路由信息
     *
     * @return 路由信息
     */
    public Map<String, Map<String, Set<String>>> getStructuralMeta() {
        return structuralMeta;
    }

    public void setStructrualMeta(Map<String, Map<String, Set<String>>> structuralMeta) {
        this.structuralMeta = structuralMeta;
    }
}
