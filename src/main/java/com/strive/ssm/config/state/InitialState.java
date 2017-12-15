package com.strive.ssm.config.state;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;

import java.util.EnumSet;

/**
 * @ClassName: InitialState
 * @Description: 初始状态
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月02日 17:38
 */
@Configuration
@EnableStateMachine
public class InitialState extends EnumStateMachineConfigurerAdapter<States, Events> {

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states)
            throws Exception {
        states
                .withStates()
                .initial(States.S1, this.initialAction())
                .end(States.SF)
                .states(EnumSet.allOf(States.class));
    }

    public Action<States, Events> initialAction() {
        return context -> System.out.println("执行一些初始化的逻辑");
    }
}
