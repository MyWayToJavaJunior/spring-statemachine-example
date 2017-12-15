package com.strive.ssm.config.scope;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

/**
 * @ClassName: UsingScope2
 * @Description: 一旦将状态机的范围标记为session级别的话，那么在每个被@Controller标记的controller中自动注入StateMachine时，每个session
 * 都会得到新的状态机实例。当HttpSession失效时，状态机也会被销毁
 * <p>
 * 在session范围内使用状态机时需要仔细规划，主要是因为它是一个相对重量级的组件
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月04日 11:45
 */
@Configuration
@EnableStateMachine
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UsingScope2 extends StateMachineConfigurerAdapter<String, String> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<String, String> config) throws Exception {
        config
                .withConfiguration()
                .autoStartup(true);
    }

    @Override
    public void configure(StateMachineStateConfigurer<String, String> states) throws Exception {
        states
                .withStates()
                .initial("S1")
                .state("S2");
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<String, String> transitions) throws Exception {
        transitions
                .withExternal()
                .source("S1")
                .target("S2")
                .event("E1");
    }
}
