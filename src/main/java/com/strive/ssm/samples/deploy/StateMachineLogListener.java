package com.strive.ssm.samples.deploy;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: StateMachineLogListener
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月16日 9:55
 */
public class StateMachineLogListener extends StateMachineListenerAdapter<String, String> {

    private final List<String> messages = new ArrayList<>();

    public List<String> getMessages() {
        return messages;
    }

    public void resetMessages() {
        messages.clear();
    }

    @Override
    public void stateContext(StateContext<String, String> stateContext) {
        if (stateContext.getStage() == StateContext.Stage.STATE_ENTRY) {
            messages.add("Enter " + stateContext.getTarget().getId());
            if (stateContext.getTarget().getId().equals("ERROR")) {
                messages.add("ERROR got exception " + stateContext.getExtendedState().getVariables().get("error"));
            }
        } else if (stateContext.getStage() == StateContext.Stage.STATE_EXIT) {
            messages.add("Exit " + stateContext.getSource().getId());
        }
    }
}
