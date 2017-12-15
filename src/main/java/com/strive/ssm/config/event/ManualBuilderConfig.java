package com.strive.ssm.config.event;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;

/**
 * @ClassName: ManualBuilderConfig
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月04日 16:53
 */
@Configuration
@EnableStateMachine
public class ManualBuilderConfig {

    @Bean
    public StateMachine<String, String> stateMachine() throws Exception {

        StateMachineBuilder.Builder<String, String> builder = StateMachineBuilder.builder();
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
