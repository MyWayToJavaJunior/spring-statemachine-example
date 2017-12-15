package com.strive.ssm.config.annotation;

import org.springframework.messaging.Message;
import org.springframework.statemachine.ExtendedState;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.annotation.EventHeaders;
import org.springframework.statemachine.annotation.OnStateMachineError;
import org.springframework.statemachine.annotation.OnStateMachineStart;
import org.springframework.statemachine.annotation.OnStateMachineStop;
import org.springframework.statemachine.annotation.WithStateMachine;

import java.util.Map;

/**
 * @ClassName: StateMachineAnnotation1
 * @Description: 将该bean与id为myMachineId的状态机关联
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月04日 18:55
 */
@WithStateMachine(id = "myMachineId")
public class StateMachineAnnotation {

    @OnStateMachineStart
    public void onStateMachineStart() {
    }

    @OnStateMachineStart
    public void onStateMachineStartWithParameter(
            @EventHeaders Map<String, Object> headers,
            ExtendedState extendedState,
            StateMachine<String, String> stateMachine,
            Message<String> message,
            Exception e) {
    }

    @OnStateMachineStop
    public void onStateMachineStop() {
    }

    @OnStateMachineStop
    public void onStateMachineStopWithParameter(
            @EventHeaders Map<String, Object> headers,
            ExtendedState extendedState,
            StateMachine<String, String> stateMachine,
            Message<String> message,
            Exception e) {
    }

    @OnStateMachineError
    public void onStateMachineError() {
    }

    @OnStateMachineError
    public void onStateMachineErrorWithParameter(
            @EventHeaders Map<String, Object> headers,
            ExtendedState extendedState,
            StateMachine<String, String> stateMachine,
            Message<String> message,
            Exception e) {
    }
}
