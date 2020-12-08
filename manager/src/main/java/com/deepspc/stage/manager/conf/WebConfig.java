package com.deepspc.stage.manager.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;

/**
 * @author gzw
 * @date 2020/11/30 14:57
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final PropertiesConfig propertiesConfig;

    @Autowired
    public WebConfig(PropertiesConfig propertiesConfig) {
        this.propertiesConfig = propertiesConfig;
    }

    /**
     * 静态资源映射
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/attachment/**").addResourceLocations("file:///" + propertiesConfig.getAttachmentPath());
    }

}
