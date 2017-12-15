package com.strive.ssm.samples.showcase;

import com.strive.ssm.samples.AbstractStateMachineCommands;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

/**
 * @ClassName: StateMachineCommands
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月10日 14:21
 */
@Component
public class StateMachineCommands extends AbstractStateMachineCommands<States, Events> {

    @CliCommand(value = "sm event", help = "Sends an event to a state machine")
    public String event(@CliOption(key = {"", "event"}, mandatory = true, help = "The event") final Events event) {
        getStateMachine().sendEvent(event);
        return "Event " + event + " send";
    }
}
