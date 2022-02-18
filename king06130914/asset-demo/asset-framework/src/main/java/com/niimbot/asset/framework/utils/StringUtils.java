package com.niimbot.asset.framework.utils;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.niimbot.asset.framework.constant.BaseConstant;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * 字符工具类
 *
 * @author xie.wei
 * @Date 2020/11/12
 */
public class StringUtils extends StrUtil {

    /**
     * 获取员工empNo
     * <p>
     * 从list中获取最大数字+1返回
     * 返回格式00010,五位,不足补齐
     *
     * @param list 待处理的list
     * @return 结果
     */
    public static String getMaxIncreFromList(List<String> list, String defaultResult) {
        Optional<String> opt = list.stream().filter(s -> NumberUtil.isNumber(s) && s.length() == 5)
                .sorted(Comparator.reverseOrder())
                .findFirst();
        String result = defaultResult;
        if (opt.isPresent()) {
            int i = NumberUtil.parseInt(opt.get());
            String temp = String.valueOf(++i);
            if (temp.length() > 5) {
                result = temp;
            } else {
                result = StrUtil.padPre(temp, 5, "0");
            }
        }
        return result;
    }

    /**
     * 获取单据编号
     * rule:  字母前缀JY+年月日时分秒+时间戳的微秒数小数点后半部分的前四位
     * 后四位取nanoTime后四位
     *
     * @return orderNo
     */
    public static String getOrderNo(String prefix) {
        String now = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now());
        String substring = StringUtils.toString(System.nanoTime()).substring(0, 4);
        return concat(true, prefix, now, substring);
    }

    /**
     * 判断路径是否是url
     *
     * @param url 带判断url
     * @return true/false
     */
    public static Boolean isUrl(@NonNull String url) {
        return StringUtils.isNotBlank(url) && (
                url.startsWith(BaseConstant.HTTP)
                        || url.startsWith(BaseConstant.HTTPS));
    }
}
