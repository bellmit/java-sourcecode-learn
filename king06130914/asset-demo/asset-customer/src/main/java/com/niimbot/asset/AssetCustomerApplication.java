package com.niimbot.asset;

import com.thebeastshop.forest.springboot.annotation.ForestScan;
import org.springframework.boot.Banner;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.SpringVersion;

@EnableCaching
@SpringBootApplication
@EnableFeignClients(basePackages = "com.niimbot.asset.service")
@ForestScan(basePackages = "com.niimbot.asset.service.http")
public class AssetCustomerApplication {

    public static void main(String[] args) {
        // 启动颜色格式化
        AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);
        new SpringApplicationBuilder(AssetCustomerApplication.class)
                .main(SpringVersion.class) // 这个是为了可以加载 Spring 版本
                .bannerMode(Banner.Mode.CONSOLE)// 控制台打印
                .run(args);
//        SpringApplication.run(AssetCustomerApplication.class, args);
        System.out.println("asset-customer启动完成");
    }

}
