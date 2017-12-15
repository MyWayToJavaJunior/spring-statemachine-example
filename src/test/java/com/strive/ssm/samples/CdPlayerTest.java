package com.strive.ssm.samples;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

import com.strive.ssm.samples.cdplayer.Application;
import com.strive.ssm.samples.cdplayer.Cd;
import com.strive.ssm.samples.cdplayer.CdPlayer;
import com.strive.ssm.samples.cdplayer.Events;
import com.strive.ssm.samples.cdplayer.Library;
import com.strive.ssm.samples.cdplayer.States;
import com.strive.ssm.samples.cdplayer.Track;
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
 * @ClassName: CdPlayerTest
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月10日 14:56
 */
public class CdPlayerTest {

    private AnnotationConfigApplicationContext context;

    private StateMachine<States, Events> machine;

    private CdPlayer player;

    private Library library;

    private TestListener listener;

    @Test
    public void testInitialState() throws InterruptedException {
        assertThat(listener.stateChangedLatch.await(2, TimeUnit.SECONDS), is(true));
        assertThat(listener.stateChangedCount, is(2));
        assertThat(machine.getState().getIds(), contains(States.IDLE, States.CLOSED));
        assertLcdStatusStartsWith("No CD");
    }

    @Test
    public void testEjectTwice() throws Exception {
        listener.reset(1, 0, 0);
        player.eject();
        assertThat(listener.stateChangedLatch.await(2, TimeUnit.SECONDS), is(true));
        assertThat(listener.stateChangedCount, is(1));
        assertThat(machine.getState().getIds(), contains(States.IDLE, States.OPEN));
        listener.reset(1, 0, 0);
        player.eject();
        assertThat(listener.stateChangedLatch.await(2, TimeUnit.SECONDS), is(true));
        assertThat(listener.stateChangedCount, is(1));
        assertThat(machine.getState().getIds(), contains(States.IDLE, States.CLOSED));
    }

    @Test
    public void testPlayWithCdLoaded() throws Exception {
        listener.reset(4, 0, 0);
        player.eject();
        player.load(library.getCollection().get(0));
        player.eject();
        player.play();
        assertThat(listener.stateChangedLatch.await(5, TimeUnit.SECONDS), is(true));
        assertThat(listener.stateChangedCount, is(4));
        assertThat(machine.getState().getIds(), contains(States.BUSY, States.PLAYING));
        assertLcdStatusContains("cd1");
    }

    @Test
    public void testPlayWithCdLoadedDeckOpen() throws Exception {
        listener.reset(4, 0, 0);
        player.eject();
        player.load(library.getCollection().get(0));
        player.play();
        assertThat(listener.stateChangedLatch.await(5, TimeUnit.SECONDS), is(true));
        assertThat(listener.stateChangedCount, is(4));
        assertThat(machine.getState().getIds(), contains(States.BUSY, States.PLAYING));
        assertLcdStatusContains("cd1");
    }

    @Test
    public void testPlayWithNoCdLoaded() throws Exception {
        listener.reset(0, 0, 0);
        player.play();
        assertThat(listener.stateChangedLatch.await(2, TimeUnit.SECONDS), is(true));
        assertThat(listener.stateChangedCount, is(0));
        assertThat(machine.getState().getIds(), contains(States.IDLE, States.CLOSED));
        assertLcdStatusStartsWith("No CD");
    }

    @Test
    public void testPlayLcdTimeChanges() throws Exception {
        listener.reset(4, 0, 0);
        player.eject();
        player.load(library.getCollection().get(0));
        player.eject();
        player.play();
        assertThat(listener.stateChangedLatch.await(2, TimeUnit.SECONDS), is(true));
        assertThat(listener.stateChangedCount, is(4));
        assertThat(machine.getState().getIds(), contains(States.BUSY, States.PLAYING));
        assertLcdStatusContains("cd1");

        listener.reset(0, 0, 0, 1);
        assertThat(listener.transitionLatch.await(2, TimeUnit.SECONDS), is(true));
        assertThat(listener.transitionCount, is(1));
        assertLcdStatusContains("00:01");

        listener.reset(0, 0, 0, 1);
        assertThat(listener.transitionLatch.await(2, TimeUnit.SECONDS), is(true));
        assertLcdStatusContains("00:02");
        assertThat(listener.transitionCount, is(1));

        listener.reset(0, 0, 0, 2);
        assertThat(listener.transitionLatch.await(4, TimeUnit.SECONDS), is(true));
        assertThat(listener.transitionCount, is(2));
        // ok we have some timing problems with
        // this test, so for now just check it's
        // not previous
        assertLcdStatusNotContains("00:02");
    }

    @Test
    public void testPlayPause() throws Exception {
        listener.reset(4, 0, 0);
        player.eject();
        player.load(library.getCollection().get(0));
        player.eject();
        player.play();
        assertThat(listener.stateChangedLatch.await(2, TimeUnit.SECONDS), is(true));
        assertThat(listener.stateChangedCount, is(4));
        assertThat(machine.getState().getIds(), contains(States.BUSY, States.PLAYING));
        assertLcdStatusContains("cd1");

        listener.reset(0, 0, 0, 1);
        assertThat(listener.transitionLatch.await(2, TimeUnit.SECONDS), is(true));
        assertThat(listener.transitionCount, is(1));
        assertLcdStatusContains("00:01");

        listener.reset(0, 0, 0, 1);
        assertThat(listener.transitionLatch.await(2, TimeUnit.SECONDS), is(true));
        assertLcdStatusContains("00:02");
        assertThat(listener.transitionCount, is(1));


        listener.reset(1, 0, 0, 0);
        player.pause();
        assertThat(listener.stateChangedLatch.await(2, TimeUnit.SECONDS), is(true));
        assertThat(listener.stateChangedCount, is(1));
        assertLcdStatusContains("00:02");

        listener.reset(1, 0, 0, 1);
        player.pause();
        assertThat(listener.stateChangedLatch.await(2, TimeUnit.SECONDS), is(true));
        listener.transitionLatch.await(2, TimeUnit.SECONDS);
        assertThat(listener.stateChangedCount, is(1));
        assertThat(listener.transitionCount, is(1));

        listener.reset(0, 0, 0, 2);
        assertThat(listener.transitionLatch.await(2100, TimeUnit.MILLISECONDS), is(true));
        assertThat(listener.transitionCount, is(2));
        assertLcdStatusNotContains("00:02");
    }

    @Test
    public void testPlayStop() throws Exception {
        listener.reset(4, 0, 0);
        player.eject();
        player.load(library.getCollection().get(0));
        player.eject();
        player.play();

        assertThat(listener.stateChangedLatch.await(2, TimeUnit.SECONDS), is(true));
        assertThat(listener.stateChangedCount, is(4));
        assertThat(machine.getState().getIds(), contains(States.BUSY, States.PLAYING));

        listener.reset(2, 0, 0);
        player.stop();
        assertThat(listener.stateChangedLatch.await(2, TimeUnit.SECONDS), is(true));
        assertThat(listener.stateChangedCount, is(2));
        assertLcdStatusIs("cd1 ");
    }

    @Test
    public void testPlayDeckOpenNoCd() throws Exception {
        listener.reset(2, 0, 0);
        player.eject();
        player.play();
        assertThat(listener.stateChangedLatch.await(2, TimeUnit.SECONDS), is(true));
        assertThat(listener.stateChangedCount, is(2));
        assertThat(machine.getState().getIds(), contains(States.IDLE, States.CLOSED));
    }

    private void assertLcdStatusIs(String text) {
        assertThat(player.getLdcStatus(), is(text));
    }

    private void assertLcdStatusStartsWith(String text) {
        assertThat(player.getLdcStatus(), startsWith(text));
    }

    private void assertLcdStatusContains(String text) {
        assertThat(player.getLdcStatus(), containsString(text));
    }

    private void assertLcdStatusNotContains(String text) {
        assertThat(player.getLdcStatus(), not(containsString(text)));
    }

    @SuppressWarnings("unchecked")
    @Before
    public void setup() throws Exception {
        context = new AnnotationConfigApplicationContext();
        context.register(CommonConfiguration.class, Application.class, TestConfig.class);
        context.refresh();
        machine = context.getBean(StateMachineSystemConstants.DEFAULT_ID_STATEMACHINE, ObjectStateMachine.class);
        player = context.getBean(CdPlayer.class);
        library = context.getBean(Library.class);
        listener = context.getBean(TestListener.class);
        machine.start();
        // lets do a little sleep to wait sm to start
        Thread.sleep(1000);
    }

    @After
    public void clean() {
        machine.stop();
        context.close();
        context = null;
        machine = null;
        player = null;
        library = null;
        listener = null;
    }

    static class TestConfig {

        @Autowired
        private StateMachine<States, Events> machine;

        @Bean
        public StateMachineListener<States, Events> stateMachineListener() {
            TestListener listener = new TestListener();
            machine.addStateListener(listener);
            return listener;
        }

        @Bean
        public Library library() {
            // override library to make it easier to test
            Track cd1track1 = new Track("cd1track1", 30);
            Track cd1track2 = new Track("cd1track2", 30);
            Cd cd1 = new Cd("cd1", new Track[]{cd1track1, cd1track2});
            Track cd2track1 = new Track("cd2track1", 30);
            Track cd2track2 = new Track("cd2track2", 30);
            Cd cd2 = new Cd("cd2", new Track[]{cd2track1, cd2track2});
            return new Library(new Cd[]{cd1, cd2});
        }

    }

    static class TestListener extends StateMachineListenerAdapter<States, Events> {

        volatile CountDownLatch stateChangedLatch = new CountDownLatch(1);
        volatile CountDownLatch stateEnteredLatch = new CountDownLatch(2);
        volatile CountDownLatch stateExitedLatch = new CountDownLatch(0);
        volatile CountDownLatch transitionLatch = new CountDownLatch(0);
        volatile int stateChangedCount = 0;
        volatile int transitionCount = 0;
        List<State<States, Events>> statesEntered = new ArrayList<State<States, Events>>();
        List<State<States, Events>> statesExited = new ArrayList<State<States, Events>>();

        @Override
        public void stateChanged(State<States, Events> from, State<States, Events> to) {
            stateChangedCount++;
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
        public void transitionEnded(Transition<States, Events> transition) {
            transitionCount++;
            transitionLatch.countDown();
        }

        public void reset(int c1, int c2, int c3) {
            reset(c1, c2, c3, 0);
        }

        public void reset(int c1, int c2, int c3, int c4) {
            stateChangedLatch = new CountDownLatch(c1);
            stateEnteredLatch = new CountDownLatch(c2);
            stateExitedLatch = new CountDownLatch(c3);
            transitionLatch = new CountDownLatch(c4);
            stateChangedCount = 0;
            transitionCount = 0;
            statesEntered.clear();
            statesExited.clear();
        }
    }
}
