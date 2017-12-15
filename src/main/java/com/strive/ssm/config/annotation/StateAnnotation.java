package com.strive.ssm.config.annotation;

import org.springframework.messaging.Message;
import org.springframework.statemachine.ExtendedState;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.annotation.EventHeaders;
import org.springframework.statemachine.annotation.OnStateChanged;
import org.springframework.statemachine.annotation.OnStateEntry;
import org.springframework.statemachine.annotation.OnStateExit;
import org.springframework.statemachine.annotation.WithStateMachine;

import java.util.Map;

/**
 * @ClassName: StateAnnotation1
 * @Description: 将该bean与id为myMachineId的状态机关联
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月04日 18:55
 */
@WithStateMachine(id = "myMachineId")
public class StateAnnotation {

    /**
     * 由于@OnStateChanged注解的source和target为空，因此将匹配任何的状态变更
     */
    @OnStateChanged
    public void anyStateChange() {
    }

    @OnStateChanged
    public void anyStateChangeWithParameter(
            @EventHeaders Map<String, Object> headers,
            ExtendedState extendedState,
            StateMachine<String, String> stateMachine,
            Message<String> message,
            Exception e) {
    }

    @OnStateChanged(source = "S1", target = "S2")
    public void stateChangeFromS1toS2() {
    }

    @OnStateChanged(source = "S1", target = "S2")
    public void stateChangeFromS1toS2WithParameter(
            @EventHeaders Map<String, Object> headers,
            ExtendedState extendedState,
            StateMachine<String, String> stateMachine,
            Message<String> message,
            Exception e) {
    }

    @StatesOnStates(source = States.S1, target = States.S2)
    public void fromS1ToS2() {
    }

    @StatesOnStates(source = States.S1, target = States.S2)
    public void fromS1ToS2WithParameter(
            @EventHeaders Map<String, Object> headers,
            ExtendedState extendedState,
            StateMachine<String, String> stateMachine,
            Message<String> message,
            Exception e) {
    }

    @OnStateEntry
    public void anyStateEntry() {
    }

    @OnStateEntry
    public void anyStateEntryWithParameter(
            @EventHeaders Map<String, Object> headers,
            ExtendedState extendedState,
            StateMachine<String, String> stateMachine,
            Message<String> message,
            Exception e) {
    }

    @OnStateExit
    public void anyStateExit() {
    }

    @OnStateExit
    public void anyStateExitWithParameter(
            @EventHeaders Map<String, Object> headers,
            ExtendedState extendedState,
            StateMachine<String, String> stateMachine,
            Message<String> message,
            Exception e) {
    }
}
