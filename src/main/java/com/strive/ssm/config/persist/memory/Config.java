package com.strive.ssm.config.persist.memory;

import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

/**
 * @ClassName: Config
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月06日 17:28
 */
public class Config extends StateMachineConfigurerAdapter<String, String> {

    @Override
    public void configure(StateMachineStateConfigurer<String, String> states) throws Exception {
        states
                .withStates()
                .initial("S1")
                .state("S1")
                .state("S2");
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<String, String> transitions) throws Exception {
        transitions
                .withExternal()
                .source("S1").target("S2").event("E1");
    }
}
