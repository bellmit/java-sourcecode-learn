package com.niimbot.asset.security.enmu;

/**
 * 二维码
 *
 * @author Wangzhiwen
 */
public enum QrTypeEnum {

    LOGIN("login","扫描登录");

    private String type;
    private String description;

    QrTypeEnum(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
