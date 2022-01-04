package io.github.talelin.core.annotation;

import java.lang.annotation.*;

/**
 * 路由信息，记录路由权限、模块等信息
 *
 * 修饰使用其的方法，将此方法纳入权限系统中，并接受保护
 *
 * @ref
 *      https://doc.cms.talelin.com/server/spring-boot/permission.html#%E6%9D%83%E9%99%90%E4%BD%BF%E7%94%A8
 *
 * @author pedro@TaleLin
 * @author colorful@TaleLin
 *
 * @see {@link GroupRequired}  方法分组，纳入了权限系统还不够，还需要设置到正确的分组，即“必须拥有xx权限的分组”才可访问
 *
 *
 * lin-cms 的权限系统：
 *     - 定义 API 接口，API 是权限的基本单位，但并不是每个 API 接口都需要权限，如删除图书这样慎重的操作需要权限
 *     - 接口需要 PermissionMeta 注解来将其纳入权限系统，并且需要开启 auth.enabled 配置才能在开启权限拦截
 *     - 接口需要类似 GroupRequired 的注解来告诉 lin-cms ，该接口对哪些人开启
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PermissionMeta {

    // 操作内容
    String value();

    // 权限名称，用来给权限命名，如：删除图书
    @Deprecated
    String permission() default "";

    // 权限模块，表示该权限属于 xx 模块，如：图书
    String module() default "";

    // 是否挂载到权限系统中，如果为 false ，则该权限不挂载到权限系统中
    boolean mount() default true;

}
