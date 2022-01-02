package io.github.talelin.latticy.extension.file;

import io.github.talelin.latticy.module.file.Uploader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * 文件上传配置类
 *
 * @author Juzi@TaleLin
 * @author colorful@TaleLin
 */
@Configuration(proxyBeanMethods = false)  // 配置类
public class UploaderConfiguration {
    /**
     * @return 本地文件上传实现类 （默认，如果有其他实现就不使用这个实现）
     */
    @Bean
    @Order
    @ConditionalOnMissingBean
    public Uploader uploader(){
        return new LocalUploader();
    }


    // 七牛
    @Bean
    public Uploader qiniuUpLoader(){return new QiniuUploader();}


}
