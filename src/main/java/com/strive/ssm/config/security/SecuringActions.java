package com.strive.ssm.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.access.annotation.Secured;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

/**
 * @ClassName: SecuringActions
 * @Description: 对于状态机中的Action，并没有提供专门的安全定义，但是可以使用Spring Security的全局方法来实现安全定义。只需要在Action
 * 的定义上加上@Bean注解以及在重载的execute方法上加上@Secured注解
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月06日 14:00
 */
@Configuration
@EnableStateMachine
public class SecuringActions extends StateMachineConfigurerAdapter<String, String> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<String, String> config)
            throws Exception {
        config
                .withSecurity()
                .enabled(true);
    }

    @Override
    public void configure(StateMachineStateConfigurer<String, String> states)
            throws Exception {
        states
                .withStates()
                .initial("S0")
                .state("S1");
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<String, String> transitions)
            throws Exception {
        transitions
                .withExternal()
                .source("S0").target("S1").action(this.securedAction()).event("A");
    }

    @Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
    @Bean
    public Action<String, String> securedAction() {
        return new Action<String, String>() {

            @Secured("ROLE_ANONYMOUS")
            @Override
            public void execute(StateContext<String, String> context) {
            }
        };
    }
}
