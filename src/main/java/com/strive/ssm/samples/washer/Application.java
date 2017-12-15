package com.strive.ssm.samples.washer;

import org.springframework.context.annotation.Configuration;
import org.springframework.shell.Bootstrap;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.config.configurers.StateConfigurer;

/**
 * @ClassName: Application
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月10日 15:14
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
                    .initial(States.RUNNING)
                    .state(States.POWEROFF)
                    .end(States.END)
                    .and()
                    .withStates()
                    .parent(States.RUNNING)
                    .initial(States.WASHING)
                    .state(States.RINSING)
                    .state(States.DRYING)
                    .history(States.HISTORY, StateConfigurer.History.SHALLOW);
        }

        @Override
        public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
            transitions
                    .withExternal()
                    .source(States.WASHING).target(States.RINSING)
                    .event(Events.RINSE)
                    .and()
                    .withExternal()
                    .source(States.RINSING).target(States.DRYING)
                    .event(Events.DRY)
                    .and()
                    .withExternal()
                    .source(States.RUNNING).target(States.POWEROFF)
                    .event(Events.CUTPOWER)
                    .and()
                    .withExternal()
                    .source(States.POWEROFF).target(States.HISTORY)
                    .event(Events.RESTOREPOWER)
                    .and()
                    .withExternal()
                    .source(States.RUNNING).target(States.END)
                    .event(Events.STOP);
        }

    }

    public static void main(String[] args) throws Exception {
        Bootstrap bootstrap = new Bootstrap(args,new String[]{"classpath*:/washer/spring-shell-plugin.xml"});
        bootstrap.run();
    }
}

