package io.github.talelin.core.annotation;

import io.github.talelin.core.enumeration.UserLevel;

import java.lang.annotation.*;

/**
 * 分组权限-即只有加入分组后的用户，且该分组已经拥有该权限，用户才能访问接口
 *
 * @like {@link LoginRequired} 表示用户登录后即可访问，非登录用户不可访问
 * @like {@link AdminRequired} 表示管理员才可以访问
 *
 * 等级 	                 作用 	                           说明
 * LoginRequired 	拦截未登录用户 	      默认新建用户为 guest 分组，游客分组，该分组也可登录，但不可分配权限
 * GroupRequired 	拦截非已有权限分组用户 	  用户加入分组后，再为分组分配权限，该用户才能访问权限接口
 * AdminRequired 	拦截非管理员用户 	      root 分组用户即时管理员用户，且目前 root 只能有一个用户
 *
 *
 * lin-cms 默认只有一个管理员分组，即 root 分组，且 root 分组只有一个用户--root，用户新建时无特殊特定默认在 guest 分组中
 * 不过，目前更改默认分组和用户还需要修改项目源码 (2021/10/07)
 *
 * @author pedro@TaleLin
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Required(level = UserLevel.GROUP)
public @interface GroupRequired {
}
