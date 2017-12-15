package com.strive.ssm.config.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @ClassName: StateMachineConfig
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月02日 11:25
 */
@Configuration
@EnableStateMachine
public class StateMachineConfig extends StateMachineConfigurerAdapter<String, String> {

    @Override
    public void configure(StateMachineStateConfigurer<String, String> states)
            throws Exception {
        states
                .withStates()
                .initial("S1")
                .end("SF")
                .states(new HashSet<>(Arrays.asList("S1", "S2", "S3", "S4")));
    }
}
