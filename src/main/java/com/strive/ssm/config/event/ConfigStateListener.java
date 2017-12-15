package com.strive.ssm.config.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.statemachine.StateMachine;

/**
 * @ClassName: ConfigStateListener
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月04日 16:55
 */
public class ConfigStateListener {

    @Autowired
    StateMachine<States, Events> stateMachine;

    @Bean
    public StateMachineEventListener stateMachineEventListener() {
        StateMachineEventListener listener = new StateMachineEventListener();
        stateMachine.addStateListener(listener);
        return listener;
    }
}
