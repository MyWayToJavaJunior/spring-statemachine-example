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
 * @ClassName: UsingDeferredEvent1
 * @Description: 使用延迟事件，如果发生在当前状态的延迟事件列表中的事件，则该事件将被推迟以进行将来的处理，直到一个未在其延迟事件列表中列出
 * 事件的状态被输入
 * <p>
 * 当进入这样的状态时，状态机将自动唤醒所有不再被延迟的事件，然后将消费或丢弃这些事件。
 * <p>
 * 在同一层级状态机概念中，substate的优先级高于superstate，则superstate的事件将被延迟并且状态转换将不会被执行。
 * <p>
 * 在正交区域中，一个正交区域对一个事件延迟，则另一个区域将优先接收该事件，该事件将被消费而不会被延迟。
 * <p>
 * 事件延迟最明显的用例是当一个事件导致转换到一个特定的状态时，状态机被返回到它的原始状态，而第二个事件应该引起相同的转换。
 * <p>
 * 在下面的例子中，如果状态机使用的是同步执行器，则在READY状态下发送多个事件不会造成任何效果，因为事件发送将在事件调用之间被阻塞。但是，如果
 * 状态机使用的是异步执行器，那么其他事件可能会丢失，因为状态机不再处于可以处理事件的状态。
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月03日 17:38
 */
@Configuration
@EnableStateMachine
public class UsingDeferredEvent1 extends StateMachineConfigurerAdapter<String, String> {

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
                .state("DEPLOYPREPARE", "DEPLOY")
                .state("DEPLOYEXECUTE", "DEPLOY");
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<String, String> transitions)
            throws Exception {
        transitions
                .withExternal()
                .source("READY").target("DEPLOYPREPARE").event("DEPLOY")
                .and()
                .withExternal()
                .source("DEPLOYPREPARE").target("DEPLOYEXECUTE")
                .and()
                .withExternal()
                .source("DEPLOYEXECUTE").target("READY");
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
