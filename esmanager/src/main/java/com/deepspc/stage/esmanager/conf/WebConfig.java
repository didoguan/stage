package com.deepspc.stage.esmanager.conf;

import com.deepspc.stage.shiro.common.ShiroKit;
import com.deepspc.stage.sys.common.SysPropertiesConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gzw
 * @date 2020/11/30 14:57
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final SysPropertiesConfig sysPropertiesConfig;
    private final ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    public WebConfig(SysPropertiesConfig sysPropertiesConfig, ThymeleafViewResolver thymeleafViewResolver) {
        this.sysPropertiesConfig = sysPropertiesConfig;
        this.thymeleafViewResolver = thymeleafViewResolver;
    }

    /**
     * 静态资源映射
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/attachment/**").addResourceLocations("file:///" + sysPropertiesConfig.getAttachmentPath());
    }

    /**
     * 设置全局变量
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        if (thymeleafViewResolver != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("ShiroKit", new ShiroKit());
            thymeleafViewResolver.setStaticVariables(map);
        }
    }

}
