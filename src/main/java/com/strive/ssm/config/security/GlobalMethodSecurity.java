package com.strive.ssm.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @ClassName: GlobalMethodSecurity
 * @Description: 启用状态机全局方法安全配置
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月06日 16:09
 */
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class GlobalMethodSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER");
    }
}
