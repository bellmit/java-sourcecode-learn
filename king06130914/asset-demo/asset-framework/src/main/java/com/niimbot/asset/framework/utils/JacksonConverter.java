package com.niimbot.asset.framework.utils;

import cn.hutool.core.convert.Convert;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.niimbot.asset.framework.web.JacksonSerializerModifier;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class JacksonConverter {

    public static ObjectMapper MAPPER = null;

    static {
        Jackson2ObjectMapperBuilder builder = Jackson2ObjectMapperBuilder.json();
        MAPPER = builder.build();
        // 所有值都返回
        MAPPER.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        // 反序列化失败不抛异常
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 配置空值替换
        MAPPER.setSerializerFactory(MAPPER.getSerializerFactory().withSerializerModifier(new JacksonSerializerModifier()));

        // 配置LocalDateTime类型序列化
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
            @Override
            public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeNumber(value == null ? 0 : value.toInstant(ZoneOffset.of("+8")).toEpochMilli());
            }
        });
        simpleModule.addSerializer(LocalDate.class, new JsonSerializer<LocalDate>() {
            @Override
            public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeNumber(value == null ? 0 : value.atStartOfDay().toInstant(ZoneOffset.of("+8")).toEpochMilli());
            }
        });
        simpleModule.addSerializer(Long.class, new JsonSerializer<Long>() {
            @Override
            public void serialize(Long value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeString(value == null ? "0" : Convert.toStr(value));
            }
        });
        MAPPER.registerModule(new Jdk8Module())
                .registerModule(simpleModule);
    }
}
