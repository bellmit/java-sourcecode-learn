package com.niimbot.asset.framework.autoconfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 上传、下载配置
 *
 * @author xie.wei
 * @Date 2020/11/30
 */
@Component
@ConfigurationProperties(prefix = "asset.upload")
@Data
public class FileUploadConfig {

    /**
     * 前端 form-data  多上传文件参数name
     */
    public static final String FRONT_MULTI_PARAM_NAME = "files";

    /**
     * 前端 form-data  多上传文件参数name
     */
    public static final String FRONT_SINGLE_PARAM_NAME = "file";

    /**
     * 上传服务器的域名地址
     */
    private String domain;

    /**
     * 上传路径
     */
    private String path = "C:/uploadPath";

    /**
     * 临时上传目录
     */
    private String tempPath = "C:/uploadTempPath";

    /**
     * 上传文件名校验正则
     */
    private String fileNameRegex;

    /**
     * 上传的文件名长度限制
     */
    private Integer fileNameLengthLimit;

    /**
     * 单文件上传大小限制
     *  e.g 20MB/mb  20KB/kb
     * tomcat 文件限制1M 该值默认设置小于tomcat的配置
     *
     */
    private String fileMaxSize;

    /**
     * 总文件大小限制
     */
    private String maxRequestSize;

    /**
     * 上传允许的扩展名,多个以,隔开
     */
    private String allowExtension;

    /**
     * 是否开启上传配置
     */
    private Boolean enable = true;

    /**
     * 是否允许多文件上传
     */
    private Boolean allowMultiFile = true;

    /**
     * 文件上传最大数量
     */
    private Integer fileMaxCount;

    /**
     * 是否允许下载
     */
    private Boolean allowDownLoad = true;

    /**
     * 多文件下载时最大文件数
     */
    private Integer maxDownLoadCountSize;
}
