package com.strive.ssm.config.region;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;

/**
 * @ClassName: ConfigRegion
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月02日 11:30
 */
@Configuration
@EnableStateMachine
public class ConfigRegion extends EnumStateMachineConfigurerAdapter<States2, Events> {

    @Override
    public void configure(StateMachineStateConfigurer<States2, Events> states)
            throws Exception {
        states
                .withStates()
                .initial(States2.S1)
                .state(States2.S2)
                .and()
                .withStates()
                .parent(States2.S2)
                .initial(States2.S2I)
                .state(States2.S21)
                .end(States2.S2F)
                .and()
                .withStates()
                .parent(States2.S2)
                .initial(States2.S3I)
                .state(States2.S31)
                .end(States2.S3F);
    }

}
