package com.strive.ssm.config.guard;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import java.util.EnumSet;

/**
 * @ClassName: ConfigGuard2
 * @Description: 状态机配置
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月02日 10:57
 */
@Configuration
@EnableStateMachine
public class ConfigGuard2 extends EnumStateMachineConfigurerAdapter<States, Events> {

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
                .initial(States.SI)
                .states(EnumSet.allOf(States.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions)
            throws Exception {
        transitions
                .withExternal()
                .source(States.SI).target(States.S1).event(Events.E1)
                .guard(this.guard1())
                .and()
                .withExternal()
                .source(States.S1).target(States.S2).event(Events.E1)
                .guard(this.guard2())
                .and()
                .withExternal()
                .source(States.S2).target(States.S3).event(Events.E2)
                .guardExpression("extendedState.variables.get('myvar')");
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

    public Guard<States, Events> guard1() {
        return context -> true;
    }

    public BaseGuard guard2() {
        return new BaseGuard();
    }

    public class BaseGuard implements Guard<States, Events> {

        @Override
        public boolean evaluate(StateContext<States, Events> context) {
            return false;
        }
    }
}
