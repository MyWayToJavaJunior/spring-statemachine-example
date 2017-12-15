package com.strive.ssm.samples.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.security.SecurityRule;

import java.io.IOException;
import java.io.InputStream;
import java.util.EnumSet;
import java.util.Scanner;

/**
 * @ClassName: StateMachineConfig
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月10日 17:27
 */
@Configuration
public class StateMachineConfig {

    private static final Log log = LogFactory.getLog(StateMachineConfig.class);

    @EnableWebSecurity
    @EnableGlobalMethodSecurity(securedEnabled = true)
    static class SecurityConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth
                    .inMemoryAuthentication()
                    .withUser("user")
                    .password("password")
                    .roles("USER")
                    .and()
                    .withUser("admin")
                    .password("password")
                    .roles("USER", "ADMIN");
        }
    }

    @Configuration
    @EnableStateMachine
    static class Config extends EnumStateMachineConfigurerAdapter<States, Events> {

        @Override
        public void configure(StateMachineConfigurationConfigurer<States, Events> config) throws Exception {
            config
                    .withConfiguration()
                    .autoStartup(true)
                    .and()
                    .withSecurity()
                    .enabled(true)
                    .event("hasRole('USER')");
        }

        @Override
        public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
            states
                    .withStates()
                    .initial(States.S0)
                    .states(EnumSet.allOf(States.class));
        }

        @Override
        public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
            transitions
                    .withExternal()
                    .source(States.S0).target(States.S1).event(Events.A)
                    .and()
                    .withExternal()
                    .source(States.S1).target(States.S2).event(Events.B)
                    .and()
                    .withExternal()
                    .source(States.S2).target(States.S0).event(Events.C)
                    .and()
                    .withExternal()
                    .source(States.S2).target(States.S3).event(Events.E)
                    .secured("ROLE_ADMIN", SecurityRule.ComparisonType.ANY)
                    .and()
                    .withExternal()
                    .source(States.S3).target(States.S0).event(Events.C)
                    .and()
                    .withInternal()
                    .source(States.S0).event(Events.D)
                    .action(this.adminAction())
                    .and()
                    .withInternal()
                    .source(States.S1).event(Events.F)
                    .action(this.transitionAction())
                    .secured("ROLE_ADMIN", SecurityRule.ComparisonType.ANY);
        }

        @Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
        @Bean
        public Action<States, Events> adminAction() {
            return new Action<States, Events>() {

                @Secured("ROLE_ADMIN")
                @Override
                public void execute(StateContext<States, Events> context) {
                    log.info("Executed only for admin role");
                }
            };
        }

        @Bean
        public Action<States, Events> transitionAction() {
            return context -> log.info("Executed only for admin role");
        }
    }

    @Bean
    public String stateChartModel() throws IOException {
        ClassPathResource model = new ClassPathResource("statechartmodel.txt");
        InputStream inputStream = model.getInputStream();
        Scanner scanner = new Scanner(inputStream);
        String content = scanner.useDelimiter("\\Z").next();
        scanner.close();
        return content;
    }
}