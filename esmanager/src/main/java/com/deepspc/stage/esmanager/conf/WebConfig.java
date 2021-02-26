package com.deepspc.stage.esmanager.conf;

import com.deepspc.stage.sys.common.SysPropertiesConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author gzw
 * @date 2020/11/30 14:57
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final SysPropertiesConfig sysPropertiesConfig;

    @Autowired
    public WebConfig(SysPropertiesConfig sysPropertiesConfig) {
        this.sysPropertiesConfig = sysPropertiesConfig;
    }

    /**
     * 静态资源映射
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/attachment/**").addResourceLocations("file:///" + sysPropertiesConfig.getAttachmentPath());
    }

}
