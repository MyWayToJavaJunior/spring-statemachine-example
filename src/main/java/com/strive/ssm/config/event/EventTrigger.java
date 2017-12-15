package com.strive.ssm.config.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;

/**
 * @ClassName: EventTrigger
 * @Description:
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月04日 15:06
 */
public class EventTrigger {

    @Autowired
    StateMachine<States, Events> stateMachine;

    public void signalMachine() {
        stateMachine.sendEvent(Events.E1);

        Message<Events> message = MessageBuilder
                .withPayload(Events.E2)
                .setHeader("foo", "bar")
                .build();
        stateMachine.sendEvent(message);
    }
}
