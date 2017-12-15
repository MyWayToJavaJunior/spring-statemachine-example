package com.strive.ssm.samples.turnstile;

import org.springframework.context.annotation.Configuration;
import org.springframework.shell.Bootstrap;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

/**
 * @ClassName: Application
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月10日 13:56
 */
@Configuration
public class Application {

    @Configuration
    @EnableStateMachine
    static class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {

        @Override
        public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
            states
                    .withStates()
                    .initial(States.LOCKED)
                    .states(EnumSet.allOf(States.class));
        }

        @Override
        public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
            transitions
                    .withExternal()
                    .source(States.LOCKED).target(States.UNLOCKED).event(Events.COIN)
                    .and()
                    .withExternal()
                    .source(States.UNLOCKED).target(States.LOCKED).event(Events.PUSH);
        }

    }

    public static void main(String[] args) throws Exception {
        Bootstrap bootstrap = new Bootstrap(args,new String[]{"classpath*:/turnstile/spring-shell-plugin.xml"});
        bootstrap.run();
    }
}
