package com.strive.ssm.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.security.SecurityRule;

/**
 * @ClassName: SecuringEvents
 * @Description:
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月06日 11:26
 */
@Configuration
@EnableStateMachine
public class SecuringEvents extends StateMachineConfigurerAdapter<String, String> {

    /**
     * 在全局上定义事件安全性
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
                .event("true")
                .event("ROLE_ANONYMOUS", SecurityRule.ComparisonType.ANY);
    }
}
