package com.niimbot.asset.framework.autoconfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "token")
public class TokenConfig {
    /**
     * 令牌自定义标识
     */
    private String header = "Authorization";
    /**
     * 令牌秘钥
     */
    private String secret = "niimbot-asset-demo";
    /**
     * PC令牌有效期（单位:小时, 默认24小时）
     */
    private int expireTime = 24;
    /**
     * APP令牌有效期（单位:小时, 默认168小时）
     */
    private int appExpireTime = 168;

    /**
     * 是否单人登陆
     */
    private boolean kickout = false;
}
