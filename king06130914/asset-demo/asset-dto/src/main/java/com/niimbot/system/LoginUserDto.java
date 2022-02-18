package com.niimbot.system;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@NoArgsConstructor
public class LoginUserDto {

    /**
     * 用户唯一标识
     */
    @Getter
    @Setter
    private String token;

    /**
     * 用户登录设备
     */
    @Getter
    @Setter
    private String terminal;

    /**
     * 登录时间
     */
    @Getter
    @Setter
    private Long loginTime;

    /**
     * 过期时间
     */
    @Getter
    @Setter
    private Long expireTime;

    /**
     * 权限列表
     */
    @Getter
    @Setter
    private Set<String> permissions;

    /**
     * PC用户信息
     */
    @Getter
    @Setter
    private UserDto cusUser;

    /**
     * 运营用户信息
     */
    @Getter
    @Setter
    private AdminUserDto adminUser;


    /**
     * 账户是否未过期,过期无法验证
     */
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 指定用户是否解锁,锁定的用户无法进行身份验证
     */
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 指示是否已过期的用户的凭据(密码),过期的凭据防止认证
     */
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否可用 ,禁用的用户不能身份验证
     */
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

}
