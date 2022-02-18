package com.niimbot.asset.framework.web.advice;

import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ParameterTrimHttpInputMessage implements HttpInputMessage {

    private HttpHeaders headers;

    private InputStream body;

    public ParameterTrimHttpInputMessage(HttpInputMessage inputMessage) throws Exception {
        //获取请求体
        String content = new BufferedReader(new InputStreamReader(inputMessage.getBody()))
                .lines().collect(Collectors.joining(System.lineSeparator()));
        this.headers = inputMessage.getHeaders();
        Object object = jsonStringToMap(content);
        if (object instanceof String) {
            this.body = new ByteArrayInputStream(Convert.toStr(object).getBytes(StandardCharsets.UTF_8));
        } else {
            this.body = new ByteArrayInputStream(JSON.toJSONString(object).getBytes(StandardCharsets.UTF_8));
        }
    }

    @Override
    public InputStream getBody() throws IOException {
        return body;
    }

    @Override
    public HttpHeaders getHeaders() {
        return headers;
    }

    private static Object jsonStringToMap(String jsonString) {

        try {
            JSONObject jsonObject = JSON.parseObject(jsonString);
            Map<String, Object> map = new HashMap<>(16);
            for (String k : jsonObject.keySet()) {
                Object o = jsonObject.get(k);
//                if (ObjectUtil.isNotNull(o)) {
//                    if (o instanceof String) {
//                        if (StringUtils.isNotBlank(String.valueOf(o))) {
//                            map.put(k, o.toString().trim());
//                        }
//                    } else if (o instanceof JSONArray || o instanceof JSONObject) {
//                        map.put(k, jsonStringToMap(o.toString()));
//                    } else {
//                        map.put(k, o);
//                    }
//                }
                if (o instanceof String) {
                    map.put(k, o.toString().trim());
                } else if (o instanceof JSONArray || o instanceof JSONObject) {
                    map.put(k, jsonStringToMap(o.toString()));
                } else {
                    map.put(k, o);
                }
            }
            return map;
        } catch (Exception e) {
            try {
                JSONArray jsonArray = JSON.parseArray(jsonString);
                List<Object> list = new ArrayList<>();
                for (Object o : jsonArray) {
                    if (o instanceof String) {
                        list.add(o.toString().trim());
                    } else if (o instanceof JSONArray || o instanceof JSONObject) {
                        list.add(jsonStringToMap(o.toString()));
                    } else {
                        list.add(o);
                    }
                }
                return list;
            } catch (Exception ee) {
                return jsonString.trim();
            }
        }
    }

}
