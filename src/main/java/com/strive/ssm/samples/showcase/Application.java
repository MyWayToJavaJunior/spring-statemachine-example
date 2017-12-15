package com.strive.ssm.samples.showcase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.Bootstrap;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;

import java.util.Map;

/**
 * @ClassName: Application
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月10日 14:22
 */
@Configuration
public class Application {

    private final static Log log = LogFactory.getLog(Application.class);

    @Configuration
    @EnableStateMachine
    static class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {

        @Override
        public void configure(StateMachineStateConfigurer<States, Events> states)
                throws Exception {
            states
                    .withStates()
                    .initial(States.S0, this.fooAction())
                    .state(States.S0)
                    .and()
                    .withStates()
                    .parent(States.S0)
                    .initial(States.S1)
                    .state(States.S1)
                    .and()
                    .withStates()
                    .parent(States.S1)
                    .initial(States.S11)
                    .state(States.S11)
                    .state(States.S12)
                    .and()
                    .withStates()
                    .parent(States.S0)
                    .state(States.S2)
                    .and()
                    .withStates()
                    .parent(States.S2)
                    .initial(States.S21)
                    .state(States.S21)
                    .and()
                    .withStates()
                    .parent(States.S21)
                    .initial(States.S211)
                    .state(States.S211)
                    .state(States.S212);
        }

        @Override
        public void configure(StateMachineTransitionConfigurer<States, Events> transitions)
                throws Exception {
            transitions
                    .withExternal()
                    .source(States.S1).target(States.S1).event(Events.A)
                    .guard(this.foo1Guard())
                    .and()
                    .withExternal()
                    .source(States.S1).target(States.S11).event(Events.B)
                    .and()
                    .withExternal()
                    .source(States.S21).target(States.S211).event(Events.B)
                    .and()
                    .withExternal()
                    .source(States.S1).target(States.S2).event(Events.C)
                    .and()
                    .withExternal()
                    .source(States.S2).target(States.S1).event(Events.C)
                    .and()
                    .withExternal()
                    .source(States.S1).target(States.S0).event(Events.D)
                    .and()
                    .withExternal()
                    .source(States.S211).target(States.S21).event(Events.D)
                    .and()
                    .withExternal()
                    .source(States.S0).target(States.S211).event(Events.E)
                    .and()
                    .withExternal()
                    .source(States.S1).target(States.S211).event(Events.F)
                    .and()
                    .withExternal()
                    .source(States.S2).target(States.S11).event(Events.F)
                    .and()
                    .withExternal()
                    .source(States.S11).target(States.S211).event(Events.G)
                    .and()
                    .withExternal()
                    .source(States.S211).target(States.S0).event(Events.G)
                    .and()
                    .withInternal()
                    .source(States.S0).event(Events.H)
                    .guard(this.foo0Guard())
                    .action(this.fooAction())
                    .and()
                    .withInternal()
                    .source(States.S2).event(Events.H)
                    .guard(this.foo1Guard())
                    .action(this.fooAction())
                    .and()
                    .withInternal()
                    .source(States.S1).event(Events.H)
                    .and()
                    .withExternal()
                    .source(States.S11).target(States.S12).event(Events.I)
                    .and()
                    .withExternal()
                    .source(States.S211).target(States.S212).event(Events.I)
                    .and()
                    .withExternal()
                    .source(States.S12).target(States.S212).event(Events.I);

        }

        @Bean
        public FooGuard foo0Guard() {
            return new FooGuard(0);
        }

        @Bean
        public FooGuard foo1Guard() {
            return new FooGuard(1);
        }

        @Bean
        public FooAction fooAction() {
            return new FooAction();
        }
    }

    private static class FooAction implements Action<States, Events> {

        @Override
        public void execute(StateContext<States, Events> context) {
            Map<Object, Object> variables = context.getExtendedState().getVariables();
            Integer foo = context.getExtendedState().get("foo", Integer.class);
            if (foo == null) {
                log.info("Init foo to 0");
                variables.put("foo", 0);
            } else if (foo == 0) {
                log.info("Switch foo to 1");
                variables.put("foo", 1);
            } else if (foo == 1) {
                log.info("Switch foo to 0");
                variables.put("foo", 0);
            }
        }
    }

    private static class FooGuard implements Guard<States, Events> {

        private final int match;

        public FooGuard(int match) {
            this.match = match;
        }

        @Override
        public boolean evaluate(StateContext<States, Events> context) {
            Object foo = context.getExtendedState().getVariables().get("foo");
            return !(foo == null || !foo.equals(match));
        }
    }

    public static void main(String[] args) throws Exception {
        Bootstrap bootstrap = new Bootstrap(args,new String[]{"classpath*:/showcase/spring-shell-plugin.xml"});
        bootstrap.run();
    }
}
