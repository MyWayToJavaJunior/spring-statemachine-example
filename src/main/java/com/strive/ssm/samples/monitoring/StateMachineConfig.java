package com.strive.ssm.samples.monitoring;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

/**
 * @ClassName: StateMachineConfig
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月10日 17:27
 */
@Configuration
public class StateMachineConfig {

    @Configuration
    @EnableStateMachine
    public static class Config extends StateMachineConfigurerAdapter<String, String> {

        @Override
        public void configure(StateMachineStateConfigurer<String, String> states)
                throws Exception {
            states
                    .withStates()
                    .initial("S1")
                    .state("S2", null, (c) -> System.out.println("hello"))
                    .state("S3", (c) -> System.out.println("hello"), null);
        }

        @Override
        public void configure(StateMachineTransitionConfigurer<String, String> transitions)
                throws Exception {
            transitions
                    .withExternal()
                    .source("S1").target("S2").event("E1")
                    .action((c) -> System.out.println("hello"))
                    .and()
                    .withExternal()
                    .source("S2").target("S3").event("E2");
        }
    }
}