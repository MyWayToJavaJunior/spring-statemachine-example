package com.strive.ssm.config.settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;

import java.util.EnumSet;

/**
 * @ClassName: StateMachineFactoryAdapter
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月03日 16:37
 */
@Configuration
@EnableStateMachineFactory
public class StateMachineFactoryAdapter extends EnumStateMachineConfigurerAdapter<States, Events> {

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states)
            throws Exception {
        states
                .withStates()
                .initial(States.S1)
                .end(States.SF)
                .states(EnumSet.allOf(States.class));
    }

    private class Bean3 {

        @Autowired
        StateMachineFactory<States, Events> stateMachineFactory;

        private void method() {
            // 通过状态机工厂类构造一个新的状态机，并指定该状态机的唯一ID
            StateMachine<States, Events> stateMachine = stateMachineFactory.getStateMachine("stateMachine1");
            stateMachine.start();
        }
    }
}
