package com.strive.ssm.config.transition;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import java.util.EnumSet;

/**
 * @ClassName: StateMachineConfig
 * @Description: 状态机配置
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月02日 10:57
 */
@Configuration
@EnableStateMachine
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config)
            throws Exception {
        config
                .withConfiguration()
                .autoStartup(true) // 配置状态机自动启动
                .listener(this.listener()); // 配置状态机监听器
    }

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states)
            throws Exception {
        states
                .withStates()
                .initial(States.S1)
                .states(EnumSet.allOf(States.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions)
            throws Exception {
        transitions
                .withExternal()
                .source(States.S1).target(States.S2).event(Events.E1)
                .and()
                .withInternal().source(States.S2).event(Events.E2)
                .and()
                .withLocal().source(States.S2).target(States.S3).event(Events.E3);
    }

    public StateMachineListener<States, Events> listener() {
        return new StateMachineListenerAdapter<States, Events>() {
            @Override
            public void stateChanged(State<States, Events> from, State<States, Events> to) {
                if (null == from) {
                    System.out.println("State change to " + to.getId());
                } else {
                    System.out.println("State from " + from.getId() + " change to " + to.getId());
                }
            }
        };
    }
}
