package com.strive.ssm.config.event;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

/**
 * @ClassName: TimerTrigger
 * @Description:
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月04日 15:12
 */
@Configuration
@EnableStateMachine
public class TimerTrigger extends StateMachineConfigurerAdapter<String, String> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<String, String> config)
            throws Exception {
        config
                .withConfiguration()
                .machineId("timerTriggerStateMachine")
                .autoStartup(true) // 配置状态机自动启动
                .listener(this.listener()); // 配置状态机监听器
    }

    @Override
    public void configure(StateMachineStateConfigurer<String, String> states)
            throws Exception {
        states
                .withStates()
                .initial("S1")
                .state("S2")
                .state("S3");
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<String, String> transitions)
            throws Exception {
        transitions
                .withExternal()
                .source("S1").target("S2").event("E1")
                .and()
                .withExternal()
                .source("S1").target("S3").event("E2")
                .and()
                .withInternal()
                .source("S2")
                .action(this.timerAction())
                .timer(1000)
                .and()
                .withInternal()
                .source("S3")
                .action(this.timerAction())
                .timerOnce(1000);
    }

    public TimerAction timerAction() {
        return new TimerAction();
    }

    public class TimerAction implements Action<String, String> {

        @Override
        public void execute(StateContext<String, String> context) {
            System.out.println(context.getSource().getId() + " do something in every 1 sec");
        }
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