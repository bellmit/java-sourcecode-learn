package com.niimbot.asset.framework.web;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * created by chen.y on 2020/11/12 16:07
 */
public class JacksonSerializerModifier extends BeanSerializerModifier {

    private JsonSerializer<Object> nullArrayJsonSerializer = new NullArrayJsonSerializer();
    private JsonSerializer<Object> nullStringJsonSerializer = new NullStringJsonSerializer();
    private JsonSerializer<Object> nullIntegerJsonSerializer = new NullIntegerJsonSerializer();
    private JsonSerializer<Object> nullDateTimeJsonSerializer = new NullDateTimeJsonSerializer();
    private JsonSerializer<Object> nullBooleanJsonSerializer = new NullBooleanJsonSerializer();

    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc,
                                                     List<BeanPropertyWriter> beanProperties) {
        // 循环所有的beanPropertyWriter
        for (BeanPropertyWriter writer : beanProperties) {
            // 判断字段的类型，如果是array，list，set则注册nullSerializer
            if (isArrayType(writer)) {
                writer.assignNullSerializer(this.defaultNullArrayJsonSerializer());
            } else if (isStringType(writer)) {
                writer.assignNullSerializer(this.defaultNullStringJsonSerializer());
            } else if (isIntegerType(writer)) {
                writer.assignNullSerializer(this.defaultNullIntegerJsonSerializer());
            } else if (isDateTimeType(writer)) {
                writer.assignNullSerializer(this.defaultNullDateTimeJsonSerializer());
            } else if (isBooleanType(writer)) {
                writer.assignNullSerializer(this.defaultNullBooleanJsonSerializer());
            }
        }
        return beanProperties;
    }

    protected boolean isArrayType(BeanPropertyWriter writer) {
        Class<?> clazz = writer.getType().getRawClass();
        return clazz.isArray() || clazz.isAssignableFrom(List.class) || clazz.isAssignableFrom(Set.class);
    }

    protected boolean isStringType(BeanPropertyWriter writer) {
        Class<?> clazz = writer.getType().getRawClass();
        // Long类型必须转Str，前端会异常
        return clazz.isAssignableFrom(String.class) || clazz.isAssignableFrom(Long.class) || clazz.isAssignableFrom(long.class);
    }

    protected boolean isIntegerType(BeanPropertyWriter writer) {
        Class<?> clazz = writer.getType().getRawClass();
        return clazz.isAssignableFrom(Integer.class) || clazz.isAssignableFrom(int.class)
                || clazz.isAssignableFrom(Short.class) || clazz.isAssignableFrom(short.class);
    }

    protected boolean isDateTimeType(BeanPropertyWriter writer) {
        Class<?> clazz = writer.getType().getRawClass();
        return clazz.isAssignableFrom(LocalDateTime.class);
    }

    protected boolean isBooleanType(BeanPropertyWriter writer) {
        Class<?> clazz = writer.getType().getRawClass();
        return clazz.isAssignableFrom(boolean.class) || clazz.isAssignableFrom(Boolean.class);
    }

    protected JsonSerializer<Object> defaultNullArrayJsonSerializer() {
        return nullArrayJsonSerializer;
    }

    protected JsonSerializer<Object> defaultNullStringJsonSerializer() {
        return nullStringJsonSerializer;
    }

    protected JsonSerializer<Object> defaultNullIntegerJsonSerializer() {
        return nullIntegerJsonSerializer;
    }

    protected JsonSerializer<Object> defaultNullDateTimeJsonSerializer() {
        return nullDateTimeJsonSerializer;
    }

    protected JsonSerializer<Object> defaultNullBooleanJsonSerializer() {
        return nullBooleanJsonSerializer;
    }

    /**
     * 空集合
     */
    static class NullArrayJsonSerializer extends JsonSerializer<Object> {
        @Override
        public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            if (value == null) {
                jgen.writeStartArray();
                jgen.writeEndArray();
            } else {
                jgen.writeObject(value);
            }
        }
    }

    /**
     * 空集合
     */
    static class NullIntegerJsonSerializer extends JsonSerializer<Object> {
        @Override
        public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            if (value == null) {
                jgen.writeNumber(0);
            }
        }
    }

    /**
     * 空字符串
     */
    static class NullStringJsonSerializer extends JsonSerializer<Object> {
        @Override
        public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            if (value == null) {
                jgen.writeString("");
            }
        }
    }

    /**
     * 空字符串
     */
    static class NullDateTimeJsonSerializer extends JsonSerializer<Object> {
        @Override
        public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            if (value == null) {
                jgen.writeNumber(0);
            }
        }
    }

    /**
     * 空字符串
     */
    static class NullBooleanJsonSerializer extends JsonSerializer<Object> {
        @Override
        public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            if (value == null) {
                jgen.writeBoolean(false);
            }
        }
    }

}

