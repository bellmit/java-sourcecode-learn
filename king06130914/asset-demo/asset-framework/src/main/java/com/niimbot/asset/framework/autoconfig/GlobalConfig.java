package com.niimbot.asset.framework.autoconfig;

import cn.hutool.core.date.DateUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Date;

@Configuration
@ComponentScan(basePackages = {"cn.hutool.extra.spring"})
@Import(cn.hutool.extra.spring.SpringUtil.class)
public class GlobalConfig {

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {

        return new WebMvcConfigurer() {
            @Override
            public void addFormatters(FormatterRegistry registry) {
                registry.addConverter(new Converter<String, Date>() {
                    @Override
                    public Date convert(String source) {
                        return DateUtil.parseDate(source);
                    }
                });
            }
        };
    }
}
