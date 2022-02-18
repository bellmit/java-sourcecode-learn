package com.niimbot.asset.framework.constant;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * 客户端来源
 *
 * @author xie.wei
 * @Date 2020/11/27
 */
public enum SourceEnum implements IEnum<Integer> {

    PC(1, "PC"),
    ANDROID(2, "Android"),
    IOS(3, "iOS"),
    PDA(4, "PDA"),
    UNKNOWN(9, "PDA");

    private final int value;
    private final String name;

    SourceEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
