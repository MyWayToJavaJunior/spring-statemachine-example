package com.strive.ssm.config.annotation;

import org.springframework.messaging.Message;
import org.springframework.statemachine.ExtendedState;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.annotation.EventHeaders;
import org.springframework.statemachine.annotation.OnEventNotAccepted;
import org.springframework.statemachine.annotation.WithStateMachine;

import java.util.Map;

/**
 * @ClassName: EventAnnotation
 * @Description: 将该bean与id为myMachineId的状态机关联
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月04日 18:55
 */
@WithStateMachine(id = "myMachineId")
public class EventAnnotation {

    @OnEventNotAccepted
    public void anyEventNotAccepted() {
    }

    @OnEventNotAccepted
    public void anyEventNotAcceptedWithParameter(
            @EventHeaders Map<String, Object> headers,
            ExtendedState extendedState,
            StateMachine<String, String> stateMachine,
            Message<String> message,
            Exception e) {
    }

    @OnEventNotAccepted(event = "E1")
    public void e1EventNotAccepted() {
    }

    @OnEventNotAccepted(event = "E1")
    public void e1EventNotAcceptedWithParameter(
            @EventHeaders Map<String, Object> headers,
            ExtendedState extendedState,
            StateMachine<String, String> stateMachine,
            Message<String> message,
            Exception e) {
    }
}
