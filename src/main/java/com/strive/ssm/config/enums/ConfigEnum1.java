package com.strive.ssm.config.enums;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;

import java.util.EnumSet;

/**
 * @ClassName: Config1Enums
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月02日 11:24
 */
@Configuration
@EnableStateMachine
public class ConfigEnum1 extends EnumStateMachineConfigurerAdapter<States, Events> {

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states)
            throws Exception {
        states
                .withStates()
                .initial(States.S1)
                .end(States.SF)
                .states(EnumSet.allOf(States.class));
    }

}
