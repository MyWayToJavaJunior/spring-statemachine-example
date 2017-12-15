package com.strive.ssm.samples;

import com.strive.ssm.samples.persist.Application;
import com.strive.ssm.samples.persist.Persist;
import com.strive.ssm.samples.persist.StateMachineCommands;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @ClassName: PersistTest
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月10日 16:15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(classes = {CommonConfiguration.class, Application.class, StateMachineCommands.class})
public class PersistTest {

    @Autowired
    private StateMachineCommands commands;
    @Autowired
    private StateMachine<String, String> machine;
    @Autowired
    private Persist persist;

    @Test
    public void testNotStarted() throws Exception {
        assertThat(commands.state(), is("No state"));
    }

    @Test
    public void testInitialState() throws Exception {
        TestListener listener = new TestListener();
        machine.addStateListener(listener);
        machine.start();
        assertThat(listener.stateChangedLatch.await(3, TimeUnit.SECONDS), is(true));
        assertThat(listener.stateEnteredLatch.await(3, TimeUnit.SECONDS), is(true));
        assertThat(machine.getState().getIds(), contains("PLACED"));
        assertThat(listener.statesEntered.size(), is(1));
        assertThat(listener.statesEntered.get(0).getId(), is("PLACED"));
        assertThat(listener.statesExited.size(), is(0));
    }

    @Test
    public void testInitialDbList() {
        // dataOrder [id=1, state=PLACED]Order [id=2, state=PROCESSING]Order [id=3, state=SENT]Order [id=4, state=DELIVERED]
        assertThat(persist.listDbEntries(), containsString("PLACED"));
    }

    @Test
    public void testUpdate1() {
        persist.change(1, "PROCESS");
        assertThat(persist.listDbEntries(), containsString("id=1, state=PROCESSING"));
    }

    @Test
    public void testUpdate2() {
        persist.change(2, "SEND");
        assertThat(persist.listDbEntries(), containsString("id=2, state=SENT"));
    }

    private static class TestListener extends StateMachineListenerAdapter<String, String> {

        volatile CountDownLatch stateChangedLatch = new CountDownLatch(1);
        volatile CountDownLatch stateEnteredLatch = new CountDownLatch(1);
        volatile CountDownLatch stateExitedLatch = new CountDownLatch(0);
        volatile CountDownLatch transitionLatch = new CountDownLatch(0);
        volatile List<Transition<String, String>> transitions = new ArrayList<Transition<String, String>>();
        List<State<String, String>> statesEntered = new ArrayList<State<String, String>>();
        List<State<String, String>> statesExited = new ArrayList<State<String, String>>();

        @Override
        public void stateChanged(State<String, String> from, State<String, String> to) {
            stateChangedLatch.countDown();
        }

        @Override
        public void stateEntered(State<String, String> state) {
            statesEntered.add(state);
            stateEnteredLatch.countDown();
        }

        @Override
        public void stateExited(State<String, String> state) {
            statesExited.add(state);
            stateExitedLatch.countDown();
        }

        @Override
        public void transition(Transition<String, String> transition) {
            transitions.add(transition);
            transitionLatch.countDown();
        }
    }
}
