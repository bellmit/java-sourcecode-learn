package com.niimbot.asset.framework.utils;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@Data
@ConfigurationProperties(prefix = "ding")
public class DingTalkUtils {
    /**
     * 钉钉请求地址
     */
    private String hostUrl;

    /**
     * 钉钉机器人消息token
     */
    private String robotToken;

    /**
     * 钉钉机器人消息secret
     */
    private String robotSecret;

    /**
     * 机器人发文本消息
     *
     * @param content  发送内容
     * @return Boolean
     */
    public Boolean sendTxt(String content) {
        Long timestamp = System.currentTimeMillis();

        // 后去签名
        String sign = signKey(robotSecret, timestamp);

        // 钉钉的webhook
        String dingUrl = hostUrl + "/robot/send?access_token=" + robotToken;
        dingUrl += "&timestamp=" + timestamp + "&sign=" + sign;

        //是否通知所有人
        boolean isAtAll = false;
        //通知具体人的手机号码列表
        List<String> mobileList = Lists.newArrayList();

        //组装请求内容
        String reqStr = buildReqStr(content, isAtAll, mobileList);

        //推送消息（http请求）
        String result = postJson(dingUrl, reqStr);
        //根据返回值做出判断
        // json字符串转对象
        JSONObject jsonObject = JSON.parseObject(result);
        return ObjectUtil.isNotEmpty(jsonObject) && ObjectUtil.isNotNull(jsonObject.get("errcode"))
                && "0".equals(jsonObject.get("errcode") + "") && ObjectUtil.isNotNull(jsonObject.get("errmsg"))
                && "ok".equals(jsonObject.get("errmsg") + "");
    }

    /**
     * 组装请求报文
     * @param content
     * @return
     */
    private static String buildReqStr(String content, boolean isAtAll, List<String> mobileList) {
        //消息内容
        Map<String, String> contentMap = Maps.newHashMap();
        contentMap.put("content", content);

        //通知人
        Map<String, Object> atMap = Maps.newHashMap();
        //1.是否通知所有人
        atMap.put("isAtAll", isAtAll);
        //2.通知具体人的手机号码列表
        atMap.put("atMobiles", mobileList);

        Map<String, Object> reqMap = Maps.newHashMap();
        reqMap.put("msgtype", "text");
        reqMap.put("text", contentMap);
        reqMap.put("at", atMap);

        return JSON.toJSONString(reqMap);
    }

    private static String signKey(String secret, Long timestamp) {
        try {
            String stringToSign = timestamp + "\n" + secret;
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] signData = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
            return URLEncoder.encode(new String(Base64.encodeBase64(signData)),"UTF-8");
        } catch (Exception e){
            log.info("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
            return "";
        }
    }

    private static final int timeout = 10000;

    public static String postJson(String url, String reqStr) {
        String body = null;
        try {
            body = HttpRequest.post(url).body(reqStr).timeout(timeout).execute().body();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return body;
    }

}
