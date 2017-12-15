package com.strive.ssm.config.state;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.config.configurers.StateConfigurer;

/**
 * @ClassName: HistoryState
 * @Description: 历史状态
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月02日 17:51
 */
@Configuration
@EnableStateMachine
public class HistoryState extends EnumStateMachineConfigurerAdapter<States, Events> {

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states)
            throws Exception {
        states
                .withStates()
                .initial(States.S1)
                .state(States.S2)
                .and()
                .withStates()
                .parent(States.S2)
                .initial(States.S2I)
                .state(States.S21)
                .state(States.S22)
                .history(States.SH, StateConfigurer.History.SHALLOW);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions)
            throws Exception {
        transitions
                .withHistory()
                .source(States.SH).target(States.S22);
    }

}