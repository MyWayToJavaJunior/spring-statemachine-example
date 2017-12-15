package com.strive.ssm.config.event;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

/**
 * @ClassName: UsingDeferredEvent2
 * @Description:
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月03日 17:38
 */
@Configuration
@EnableStateMachine
public class UsingDeferredEvent2 extends StateMachineConfigurerAdapter<String, String> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<String, String> config)
            throws Exception {
        config
                .withConfiguration()
                .machineId("mymachine")
                .autoStartup(true) // 配置状态机自动启动
                .listener(this.listener()); // 配置状态机监听器
    }

    @Override
    public void configure(StateMachineStateConfigurer<String, String> states)
            throws Exception {
        states
                .withStates()
                .initial("READY")
                .state("DEPLOY", "DEPLOY")
                .state("DONE")
                .and()
                .withStates()
                .parent("DEPLOY")
                .initial("DEPLOYPREPARE")
                .state("DEPLOYPREPARE", "DONE")
                .state("DEPLOYEXECUTE");
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<String, String> transitions)
            throws Exception {
        transitions
                .withExternal()
                .source("READY").target("DEPLOY").event("DEPLOY")
                .and()
                .withExternal()
                .source("DEPLOYPREPARE").target("DEPLOYEXECUTE")
                .and()
                .withExternal()
                .source("DEPLOYEXECUTE").target("READY")
                .and()
                .withExternal()
                .source("READY").target("DONE").event("DONE")
                .and()
                .withExternal()
                .source("DEPLOY").target("DONE").event("DONE");
    }

    public StateMachineListener<String, String> listener() {
        return new StateMachineListenerAdapter<String, String>() {
            @Override
            public void stateChanged(State<String, String> from, State<String, String> to) {
                if (null == from) {
                    System.out.println("State change to " + to.getId());
                } else {
                    System.out.println("State from " + from.getId() + " change to " + to.getId());
                }
            }
        };
    }
}
