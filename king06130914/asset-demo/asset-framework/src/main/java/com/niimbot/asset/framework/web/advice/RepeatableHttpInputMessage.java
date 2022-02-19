package com.niimbot.asset.framework.web.advice;

import cn.hutool.core.util.StrUtil;
import com.niimbot.asset.framework.autoconfig.AssetConfig;
import com.niimbot.asset.framework.constant.BaseConstant;
import com.niimbot.asset.framework.core.enums.SystemResultCode;
import com.niimbot.asset.framework.service.RedisService;
import com.niimbot.asset.framework.utils.ServletUtils;
import com.niimbot.jf.core.exception.category.BusinessException;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * created by chen.y on 2021/4/28 13:54
 */
public class RepeatableHttpInputMessage implements HttpInputMessage {

    public static final String
            REPEAT_PARAMS = "repeatParams";

    public static final String REPEAT_TIME = "repeatTime";

    private HttpHeaders headers;

    private InputStream body;

    private final AssetConfig.RepeatTimeConfig repeatTimeConfig;

    /**
     * 间隔时间，单位:秒 默认5秒 两次相同参数的请求，如果间隔时间大于该参数，系统不会认定为重复提交的数据
     */
    @Setter
    private long intervalTime = 5;

    @SuppressWarnings("unchecked")
    public RepeatableHttpInputMessage(HttpInputMessage inputMessage,
                                      RedisService redisService,
                                      AssetConfig.RepeatTimeConfig repeatTimeConfig) throws Exception {
        this.repeatTimeConfig = repeatTimeConfig;
        this.headers = inputMessage.getHeaders();
        //获取请求体
        String content = new BufferedReader(new InputStreamReader(inputMessage.getBody()))
                .lines().collect(Collectors.joining(System.lineSeparator()));

        Map<String, Object> nowDataMap = new HashMap<>(16);
        nowDataMap.put(REPEAT_PARAMS, content);
        nowDataMap.put(REPEAT_TIME, System.currentTimeMillis());

        // 请求地址（作为存放cache的key值）
        HttpServletRequest request = ServletUtils.getRequest();
        String url = request.getRequestURI();

        // 唯一值（没有消息头则使用请求地址）
        String submitKey = request.getHeader("Authorization");
        if (StrUtil.isEmpty(submitKey)) {
            submitKey = url;
        }

        // 唯一标识（指定key + 消息头）
        String cacheRepeatKey = BaseConstant.REPEAT_SUBMIT_KEY + submitKey;
        Map<Object, Object> sessionMap = redisService.hGetAll(cacheRepeatKey);
        if (sessionMap != null && sessionMap.containsKey(url)) {
            Map<String, Object> preDataMap = (Map<String, Object>) sessionMap.get(url);
            if (compareParams(nowDataMap, preDataMap) && compareTime(nowDataMap, preDataMap)) {
                throw new BusinessException(SystemResultCode.PARAM_REPEAT_SUBMIT);
            }
        }
        Map<String, Object> cacheMap = new HashMap<>(16);
        cacheMap.put(url, nowDataMap);
        redisService.hSetAll(cacheRepeatKey, cacheMap, Optional.ofNullable(repeatTimeConfig)
                .map(AssetConfig.RepeatTimeConfig::getIntervalTime).orElse(this.intervalTime));

        this.body = new ByteArrayInputStream(content.getBytes());
    }


    @Override
    public InputStream getBody() throws IOException {
        return body;
    }

    @Override
    public HttpHeaders getHeaders() {
        return headers;
    }

    /**
     * 判断参数是否相同
     */
    private boolean compareParams(Map<String, Object> nowMap, Map<String, Object> preMap) {
        String nowParams = (String) nowMap.get(REPEAT_PARAMS);
        String preParams = (String) preMap.get(REPEAT_PARAMS);
        return nowParams.equals(preParams);
    }

    /**
     * 判断两次间隔时间
     */
    private boolean compareTime(Map<String, Object> nowMap, Map<String, Object> preMap) {
        long time1 = (Long) nowMap.get(REPEAT_TIME);
        long time2 = (Long) preMap.get(REPEAT_TIME);
        long intervalToUse = this.intervalTime * 1000;
        if (repeatTimeConfig != null && repeatTimeConfig.getIntervalTime() != null) {
            TimeUnit unit = Optional.ofNullable(repeatTimeConfig.getTimeUnit()).orElse(TimeUnit.SECONDS);
            intervalToUse = unit.toMillis(repeatTimeConfig.getIntervalTime());
        }
        return (time1 - time2) < intervalToUse;
    }

}
