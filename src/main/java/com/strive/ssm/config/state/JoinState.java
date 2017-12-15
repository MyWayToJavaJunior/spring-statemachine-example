package com.strive.ssm.config.state;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

/**
 * @ClassName: JoinState
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月02日 18:33
 */
@Configuration
@EnableStateMachine
public class JoinState extends EnumStateMachineConfigurerAdapter<States2, Events> {

    @Override
    public void configure(StateMachineStateConfigurer<States2, Events> states) throws Exception {
        states
                .withStates()
                .initial(States2.S1)
                .state(States2.S3)
                .join(States2.S4)
                .state(States2.S5)
                .end(States2.SF)
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
                .withJoin()
                .source(States2.S2F)
                .source(States2.S3F)
                .target(States2.S4)
                .and()
                .withExternal()
                .source(States2.S4)
                .target(States2.S5)
                .guardExpression("!extendedState.variables.isEmpty()")
                .and()
                .withExternal()
                .source(States2.S4)
                .target(States2.SF)
                .guardExpression("extendedState.variables.isEmpty()");
    }
}