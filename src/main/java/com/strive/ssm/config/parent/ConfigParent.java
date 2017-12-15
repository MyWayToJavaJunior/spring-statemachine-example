package com.strive.ssm.config.parent;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;

/**
 * @ClassName: ConfigParent
 * @Description: 配置父子状态
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月02日 11:27
 */
@Configuration
@EnableStateMachine
public class ConfigParent extends EnumStateMachineConfigurerAdapter<States, Events> {

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states)
            throws Exception {
        states
                .withStates()
                .initial(States.S1)
                .state(States.S1)
                .and()
                .withStates()
                .parent(States.S1)
                .initial(States.S2)
                .state(States.S2);
    }

}
