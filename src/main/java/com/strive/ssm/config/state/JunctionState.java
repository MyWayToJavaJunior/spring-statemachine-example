package com.strive.ssm.config.state;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;

import java.util.EnumSet;

/**
 * @ClassName: JunctionState
 * @Description: 连接状态，Junction需要在states和transitions两个地方定义
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月02日 18:19
 */
@Configuration
@EnableStateMachine
public class JunctionState extends EnumStateMachineConfigurerAdapter<States, Events> {

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states)
            throws Exception {
        states
                .withStates()
                .initial(States.SI)
                .junction(States.S1)
                .end(States.SF)
                .states(EnumSet.allOf(States.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions)
            throws Exception {
        transitions
                .withJunction()
                .source(States.S1)
                .first(States.S2, this.s2Guard())
                .then(States.S3, this.s3Guard())
                .last(States.S4);
    }

    public Guard<States, Events> s2Guard() {
        return context -> false;
    }

    public Guard<States, Events> s3Guard() {
        return context -> true;
    }

}