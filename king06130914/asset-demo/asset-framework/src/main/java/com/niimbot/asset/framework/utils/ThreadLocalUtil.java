package com.niimbot.asset.framework.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * created by chen.y on 2021/4/8 11:46
 */
public class ThreadLocalUtil {

    private ThreadLocalUtil() {

    }

    private static final InheritableThreadLocal<Map<String, String>> HEADER_MAP = new InheritableThreadLocal<Map<String, String>>() {
        @Override
        protected Map<String, String> initialValue() {
            return new HashMap<>();
        }
    };

    public static Map<String, String> get() {
        return HEADER_MAP.get();
    }

    public static String get(String key) {
        return HEADER_MAP.get().get(key);
    }

    public static void set(String key, String value) {
        HEADER_MAP.get().put(key, value);
    }

    public static void remove() {
        HEADER_MAP.remove();
    }
}
