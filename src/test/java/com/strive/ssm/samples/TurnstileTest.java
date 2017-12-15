package com.strive.ssm.samples;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import com.strive.ssm.samples.turnstile.Application;
import com.strive.ssm.samples.turnstile.Events;
import com.strive.ssm.samples.turnstile.StateMachineCommands;
import com.strive.ssm.samples.turnstile.States;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.statemachine.ObjectStateMachine;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineSystemConstants;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: TurnstileTest
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月10日 14:11
 */
public class TurnstileTest {

    private AnnotationConfigApplicationContext context;

    private StateMachine<States, Events> machine;

    private TestListener listener;

    private StateMachineCommands commands;

    @Test
    public void testNotStarted() throws Exception {
        assertThat(commands.state(), is("No state"));
    }

    @Test
    public void testInitialState() throws Exception {
        machine.start();
        listener.stateChangedLatch.await(1, TimeUnit.SECONDS);
        listener.stateEnteredLatch.await(1, TimeUnit.SECONDS);
        assertThat(machine.getState().getIds(), contains(States.LOCKED));
        assertThat(listener.statesEntered.size(), is(1));
        assertThat(listener.statesEntered.get(0).getId(), is(States.LOCKED));
        assertThat(listener.statesExited.size(), is(0));
    }

    static class Config {

        @Autowired
        private StateMachine<States, Events> machine;

        @Bean
        public StateMachineListener<States, Events> stateMachineListener() {
            TestListener listener = new TestListener();
            machine.addStateListener(listener);
            return listener;
        }
    }

    static class TestListener extends StateMachineListenerAdapter<States, Events> {

        volatile CountDownLatch stateChangedLatch = new CountDownLatch(1);
        volatile CountDownLatch stateEnteredLatch = new CountDownLatch(2);
        volatile CountDownLatch stateExitedLatch = new CountDownLatch(0);
        volatile CountDownLatch transitionLatch = new CountDownLatch(0);
        volatile List<Transition<States, Events>> transitions = new ArrayList<>();
        List<State<States, Events>> statesEntered = new ArrayList<>();
        List<State<States, Events>> statesExited = new ArrayList<>();
        volatile int transitionCount = 0;

        @Override
        public void stateChanged(State<States, Events> from, State<States, Events> to) {
            stateChangedLatch.countDown();
        }

        @Override
        public void stateEntered(State<States, Events> state) {
            statesEntered.add(state);
            stateEnteredLatch.countDown();
        }

        @Override
        public void stateExited(State<States, Events> state) {
            statesExited.add(state);
            stateExitedLatch.countDown();
        }

        @Override
        public void transition(Transition<States, Events> transition) {
            transitions.add(transition);
            transitionLatch.countDown();
            transitionCount++;
        }

        public void reset(int c1, int c2, int c3) {
            reset(c1, c2, c3, 0);
        }

        public void reset(int c1, int c2, int c3, int c4) {
            stateChangedLatch = new CountDownLatch(c1);
            stateEnteredLatch = new CountDownLatch(c2);
            stateExitedLatch = new CountDownLatch(c3);
            transitionLatch = new CountDownLatch(c4);
            statesEntered.clear();
            statesExited.clear();
            transitionCount = 0;
            transitions.clear();
        }

    }

    @SuppressWarnings("unchecked")
    @Before
    public void setup() {
        context = new AnnotationConfigApplicationContext();
        context.register(CommonConfiguration.class, Application.class, Config.class, StateMachineCommands.class);
        context.refresh();
        machine = context.getBean(StateMachineSystemConstants.DEFAULT_ID_STATEMACHINE, ObjectStateMachine.class);
        listener = context.getBean(TestListener.class);
        commands = context.getBean(StateMachineCommands.class);
    }

    @After
    public void clean() {
        machine.stop();
        context.close();
        context = null;
        machine = null;
    }
}
