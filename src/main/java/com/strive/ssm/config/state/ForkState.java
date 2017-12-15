package com.strive.ssm.config.state;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

/**
 * @ClassName: ForkState
 * @Description: Fork需要在states和transitions两个地方定义
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月02日 18:26
 */
@Configuration
@EnableStateMachine
public class ForkState extends EnumStateMachineConfigurerAdapter<States2, Events> {

    @Override
    public void configure(StateMachineStateConfigurer<States2, Events> states) throws Exception {
        states
                .withStates()
                .initial(States2.S1)
                .fork(States2.S2)
                .state(States2.S3)
                .and()
                .withStates()
                .parent(States2.S3)
                .initial(States2.S2I)
                .state(States2.S21)
                .state(States2.S22)
                .end(States2.S2F)
                .and()
                .withStates()
                .parent(States2.S3)
                .initial(States2.S3I)
                .state(States2.S31)
                .state(States2.S32)
                .end(States2.S3F);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States2, Events> transitions) throws Exception {
        transitions
                .withFork()
                .source(States2.S2)
                .target(States2.S22)
                .target(States2.S32);
    }

}