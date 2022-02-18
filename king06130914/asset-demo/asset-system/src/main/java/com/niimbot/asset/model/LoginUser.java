package com.niimbot.asset.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.niimbot.system.AdminUserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * 登录用户身份权限
 */
@NoArgsConstructor
public class LoginUser implements UserDetails {
    private static final long serialVersionUID = 1L;

    /**
     * 用户唯一标识
     */
    @Getter
    @Setter
    private String token;

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
     * 用户信息
     */
    @Getter
    @Setter
    private AdminUserDto adminUser;

    public LoginUser(AdminUserDto adminUser, Set<String> permissions) {
        this.adminUser = adminUser;
        this.permissions = permissions;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return adminUser == null ? "" : adminUser.getPassword();
    }

    @Override
    public String getUsername() {
        return adminUser == null ? "" : adminUser.getUserName();
    }

    /**
     * 账户是否未过期,过期无法验证
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 指定用户是否解锁,锁定的用户无法进行身份验证
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 指示是否已过期的用户的凭据(密码),过期的凭据防止认证
     */
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否可用 ,禁用的用户不能身份验证
     */
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}
