package com.deepspc.stage.core.conf.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.deepspc.stage.core.enums.DataSourceEnum;
import com.deepspc.stage.core.properties.DruidDataSourceProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 数据源配置
 * @author gzw
 * @date 2020/11/18 17:28
 */
@Configuration
public class DruidConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.druid")
    public DruidDataSourceProperties druidDataSourceProperties() {
        return new DruidDataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.druid.primary")
    public DataSource primaryDataSource() {
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        DruidDataSourceProperties properties = druidDataSourceProperties();
        properties.init(dataSource);
        return dataSource;
    }

    @Bean
    @ConfigurationProperties("spring.datasource.druid.second")
    @ConditionalOnProperty(prefix = "spring.datasource.druid.second", name = "enabled", havingValue = "true")
    public DataSource secondDataSource() {
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        DruidDataSourceProperties properties = druidDataSourceProperties();
        properties.init(dataSource);
        return dataSource;
    }

    /**
     * 添加多个数据源并设置默认数据源
     * @param primaryDataSource 默认数据源
     * @param secondDataSource 从数据源
     * @return DynamicDataSource
     */
    @Bean
    @Primary
    public DynamicDataSource dataSource(DataSource primaryDataSource, DataSource secondDataSource){
        Map<Object,Object> targetDataSource = new HashMap<>(2);
        targetDataSource.put(DataSourceEnum.PRIMARY.getDataSourceName(), primaryDataSource);
        targetDataSource.put(DataSourceEnum.SECOND.getDataSourceName(), secondDataSource);

        return new DynamicDataSource(primaryDataSource ,targetDataSource);
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

    @PostConstruct
    public void setProperties() {
        Properties properties = System.getProperties();
        properties.setProperty("druid.mysql.usePingMethod", "false");
        System.setProperties(properties);
    }
}
