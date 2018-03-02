package com.turkeymz.shiro.config.shiro;

import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Author : Created by richard (v-rxu@expedia.com)
 * Date : 2018/3/2
 * Time : 11:00
 * Description : shiro配置类
 * (1) 配置shiroFilterFactory
 * (2) 配置SecutiryManager
 */
@Configuration
public class ShiroConfiguration {

    /**
     * 定义shiro工厂类
     * @param securityManager
     * @return
     */
    @Bean //注入ShiroFilter
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        /**
         * 1. 定义ShiroFactoryBean
         * 2. 设置securityManager
         * 3. 设置拦截器以及配置相关权限url,使用map配置
         * 4. 返回定义ShiroFactoryBean
         */
        //定义ShiroFactoryBean
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        //设置securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 设置拦截器,使用map配置
        shiroFilterFactoryBean.setFilterChainDefinitionMap(this.getFilterChainMap());

        // 设置默认登录url
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 设置成功之后跳转的连接
        shiroFilterFactoryBean.setSuccessUrl("/index");
        // 设置未授权界面
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");

        //返回定义ShiroFactoryBean
        return shiroFilterFactoryBean;
    }

    /**
     * 定义shiro安全管理器
     * @return
     */
    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        return securityManager;
    }

    //获取拦截器map配置
    private Map getFilterChainMap() {
        //LinkedHashMap是有序的，shiro会根据顺序进行拦截

        Map<String, String> filterChainMap = new LinkedHashMap<String, String>();
        // 配置退出
        filterChainMap.put("/logout", "logout");
        // 所有url必须认证通过才允许访问
        filterChainMap.put("/**", "authc");

        return filterChainMap;
    }
}
