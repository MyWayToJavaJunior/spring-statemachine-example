package com.strive.ssm.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.security.SecurityRule;

/**
 * @ClassName: SecuringTransitions
 * @Description:
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月06日 11:26
 */
@Configuration
@EnableStateMachine
public class SecuringTransitions extends StateMachineConfigurerAdapter<String, String> {

    /**
     * 在全局上定义转换安全性
     *
     * @param config
     * @throws Exception
     */
    @Override
    public void configure(StateMachineConfigurationConfigurer<String, String> config)
            throws Exception {
        config
                .withSecurity()
                .enabled(true) // 启用状态机的安全性配置
                .transition("true")
                .transition("ROLE_ANONYMOUS", SecurityRule.ComparisonType.ANY);
    }

    /**
     * 在转换内部重写全局的转换安全性设置
     *
     * @param transitions
     * @throws Exception
     */
    @Override
    public void configure(StateMachineTransitionConfigurer<String, String> transitions)
            throws Exception {
        transitions
                .withExternal()
                .source("S0").target("S1").event("A")
                .secured("ROLE_ANONYMOUS", SecurityRule.ComparisonType.ANY)
                .secured("hasTarget('S1')");
    }
}
