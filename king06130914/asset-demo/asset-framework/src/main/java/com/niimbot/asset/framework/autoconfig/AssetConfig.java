package com.niimbot.asset.framework.autoconfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Data
@ConfigurationProperties(prefix = "asset")
public class AssetConfig {

    /**
     * 体验账号
     */
    private String account = "TY300001";

    /**
     * 体验密码
     */
    private String password = "jc123456";

    /**
     * 二维码失效时长 默认3分钟
     */
    private int qrCodeExpireTime = 180;

    /**
     * rsa公钥
     */
    private String rsaPublicKey;

    /**
     * rsa私钥
     */
    private String rsaPrivateKey;

    /**
     * 谷歌动态口令密钥
     */
    private String googleAuthSecret;

    /**
     * 默认本地版
     */
    private String edition = "local";

    /**
     * 上传路径
     */
    private String uploadPath;

    /**
     * 关于我们
     */
    private AssetAboutConfig about;

    /**
     * 防重复提交配置
     */
    private RepeatTimeConfig repeatTime;

    @Data
    public static class RepeatTimeConfig {
        private Long intervalTime;
        private TimeUnit timeUnit;
    }
}
