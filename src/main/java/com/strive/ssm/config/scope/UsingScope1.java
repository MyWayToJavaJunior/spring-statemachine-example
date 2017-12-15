package com.strive.ssm.config.scope;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;

/**
 * @ClassName: UsingScope1
 * @Description: 一旦将状态机的范围标记为session级别的话，那么在每个被@Controller标记的controller中自动注入StateMachine时，每个session
 * 都会得到新的状态机实例。当HttpSession失效时，状态机也会被销毁
 * <p>
 * 在session范围内使用状态机时需要仔细规划，主要是因为它是一个相对重量级的组件
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月04日 11:34
 */
@Configuration
public class UsingScope1 {

    @Bean
    @Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
    StateMachine<String, String> stateMachine() throws Exception {
        StateMachineBuilder.Builder<String, String> builder = StateMachineBuilder.builder();

        builder.configureConfiguration()
                .withConfiguration()
                .autoStartup(true)
                .taskExecutor(new SyncTaskExecutor());

        builder.configureStates()
                .withStates()
                .initial("S1")
                .state("S2");

        builder.configureTransitions()
                .withExternal()
                .source("S1")
                .target("S2")
                .event("E1");
        return builder.build();
    }
}
