package com.strive.ssm.samples.cdplayer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.shell.Bootstrap;
import org.springframework.statemachine.ExtendedState;
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
 * @date: 2017年11月10日 14:31
 */
@Configuration
public class Application {

    @Configuration
    @EnableStateMachine
    static class StateMachineConfig
            extends EnumStateMachineConfigurerAdapter<States, Events> {

        @Override
        public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
            states
                    .withStates()
                    .initial(States.IDLE)
                    .state(States.IDLE)
                    .and()
                    .withStates()
                    .parent(States.IDLE)
                    .initial(States.CLOSED)
                    .state(States.CLOSED, this.closedEntryAction(), null)
                    .state(States.OPEN)
                    .and()
                    .withStates()
                    .state(States.BUSY)
                    .and()
                    .withStates()
                    .parent(States.BUSY)
                    .initial(States.PLAYING)
                    .state(States.PLAYING)
                    .state(States.PAUSED);

        }

        @Override
        public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
            transitions
                    .withExternal()
                    .source(States.CLOSED).target(States.OPEN).event(Events.EJECT)
                    .and()
                    .withExternal()
                    .source(States.OPEN).target(States.CLOSED).event(Events.EJECT)
                    .and()
                    .withExternal()
                    .source(States.OPEN).target(States.CLOSED).event(Events.PLAY)
                    .and()
                    .withExternal()
                    .source(States.PLAYING).target(States.PAUSED).event(Events.PAUSE)
                    .and()
                    .withInternal()
                    .source(States.PLAYING)
                    .action(this.playingAction())
                    .timer(1000)
                    .and()
                    .withInternal()
                    .source(States.PLAYING).event(Events.BACK)
                    .action(this.trackAction())
                    .and()
                    .withInternal()
                    .source(States.PLAYING).event(Events.FORWARD)
                    .action(this.trackAction())
                    .and()
                    .withExternal()
                    .source(States.PAUSED).target(States.PLAYING).event(Events.PAUSE)
                    .and()
                    .withExternal()
                    .source(States.BUSY).target(States.IDLE).event(Events.STOP)
                    .and()
                    .withExternal()
                    .source(States.IDLE).target(States.BUSY).event(Events.PLAY)
                    .action(this.playAction())
                    .guard(this.playGuard())
                    .and()
                    .withInternal()
                    .source(States.OPEN).event(Events.LOAD).action(this.loadAction());
        }

        @Bean
        public ClosedEntryAction closedEntryAction() {
            return new ClosedEntryAction();
        }

        @Bean
        public LoadAction loadAction() {
            return new LoadAction();
        }

        @Bean
        public TrackAction trackAction() {
            return new TrackAction();
        }

        @Bean
        public PlayAction playAction() {
            return new PlayAction();
        }

        @Bean
        public PlayingAction playingAction() {
            return new PlayingAction();
        }

        @Bean
        public PlayGuard playGuard() {
            return new PlayGuard();
        }
    }

    @Bean
    public CdPlayer cdPlayer() {
        return new CdPlayer();
    }

    @Bean
    public Library library() {
        return Library.buildSampleLibrary();
    }

    public static class ClosedEntryAction implements Action<States, Events> {

        @Override
        public void execute(StateContext<States, Events> context) {
            if (context.getTransition() != null
                    && context.getEvent() == Events.PLAY
                    && context.getTransition().getTarget().getId() == States.CLOSED
                    && context.getExtendedState().getVariables().get(Variables.CD) != null) {
                context.getStateMachine().sendEvent(Events.PLAY);
            }
        }
    }

    public static class LoadAction implements Action<States, Events> {

        @Override
        public void execute(StateContext<States, Events> context) {
            Object cd = context.getMessageHeader(Variables.CD);
            context.getExtendedState().getVariables().put(Variables.CD, cd);
        }
    }

    public static class PlayAction implements Action<States, Events> {

        @Override
        public void execute(StateContext<States, Events> context) {
            context.getExtendedState().getVariables().put(Variables.ELAPSEDTIME, 0l);
            context.getExtendedState().getVariables().put(Variables.TRACK, 0);
        }
    }

    public static class PlayGuard implements Guard<States, Events> {

        @Override
        public boolean evaluate(StateContext<States, Events> context) {
            ExtendedState extendedState = context.getExtendedState();
            return extendedState.getVariables().get(Variables.CD) != null;
        }
    }

    public static class PlayingAction implements Action<States, Events> {

        @Override
        public void execute(StateContext<States, Events> context) {
            Map<Object, Object> variables = context.getExtendedState().getVariables();
            Object elapsed = variables.get(Variables.ELAPSEDTIME);
            Object cd = variables.get(Variables.CD);
            Object track = variables.get(Variables.TRACK);
            if (elapsed instanceof Long) {
                long e = ((Long) elapsed) + 1000l;
                if (e > ((Cd) cd).getTracks()[((Integer) track)].getLength() * 1000) {
                    context.getStateMachine().sendEvent(MessageBuilder
                            .withPayload(Events.FORWARD)
                            .setHeader(Headers.TRACKSHIFT.toString(), 1).build());
                } else {
                    variables.put(Variables.ELAPSEDTIME, e);
                }
            }
        }
    }

    public static class TrackAction implements Action<States, Events> {

        @Override
        public void execute(StateContext<States, Events> context) {
            Map<Object, Object> variables = context.getExtendedState().getVariables();
            Object trackshift = context.getMessageHeader(Headers.TRACKSHIFT.toString());
            Object track = variables.get(Variables.TRACK);
            Object cd = variables.get(Variables.CD);
            if (trackshift instanceof Integer && track instanceof Integer && cd instanceof Cd) {
                int next = ((Integer) track) + ((Integer) trackshift);
                if (next >= 0 && ((Cd) cd).getTracks().length > next) {
                    variables.put(Variables.ELAPSEDTIME, 0l);
                    variables.put(Variables.TRACK, next);
                } else if (((Cd) cd).getTracks().length <= next) {
                    context.getStateMachine().sendEvent(Events.STOP);
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Bootstrap bootstrap = new Bootstrap(args,new String[]{"classpath*:/cdplayer/spring-shell-plugin.xml"});
        bootstrap.run();
    }
}
