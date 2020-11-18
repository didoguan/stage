package com.deepspc.stage.core.conf;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.deepspc.stage.core.properties.DruidDataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gzw
 * @date 2020/11/18 17:28
 */
@Configuration
public class DruidConfig {

    @Bean
    public DataSource druidDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        DruidDataSourceProperties druidDataSourceProperties = druidDataSourceProperties();
        druidDataSourceProperties.init(dataSource);
        return dataSource;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DruidDataSourceProperties druidDataSourceProperties() {
        return new DruidDataSourceProperties();
    }

    /**
     * 配置Druid的监控
     * 配置一个管理后台的Servlet
     * @return
     */
    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        Map<String,String> initParams = new HashMap<String, String>();
        // 固定的用户名密码
        initParams.put("loginUsername","admin");
        initParams.put("loginPassword","123456");
        // 这个值为空或没有就允许所有人访问，ip白名单
        initParams.put("allow","");
        // 只允许本机访问，多个ip用逗号,隔开
        //initParams.put("allow","localhost");
        // ip黑名单，拒绝谁访问 deny和allow同时存在优先deny
        //initParams.put("deny","");
        // 禁用HTML页面的Reset按钮
        initParams.put("resetEnable","false");
        bean.setInitParameters(initParams);

        return bean;
    }

    /**
     * 配置一个web监控的filter
     * @return
     */
    @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        bean.addUrlPatterns("/*");
        // 把不需要监控的过滤掉,这些不进行统计
        Map<String,String> initParams = new HashMap<String,String>();
        initParams.put("exclusions","*.js,*.css,/druid/*");
        bean.setInitParameters(initParams);

        return bean;
    }
}
