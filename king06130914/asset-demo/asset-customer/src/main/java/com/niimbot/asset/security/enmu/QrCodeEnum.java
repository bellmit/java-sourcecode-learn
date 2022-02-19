package com.niimbot.asset.security.enmu;

/**
 * 二维码内容状态
 * created by chen.y on 2020/11/13 19:18
 */
public enum QrCodeEnum {
    // 未被扫描
    NOT_SCAN(0, "未被扫描"),
    // 被扫描
    SCANNED(1, "被扫描"),
    // 完成
    CONFIRM(2, "确认"),
    // 取消
    CANCEL(3, "取消"),
    // 过期
    EXPIRED(4, "过期");

    private final int code;
    private final String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    QrCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
