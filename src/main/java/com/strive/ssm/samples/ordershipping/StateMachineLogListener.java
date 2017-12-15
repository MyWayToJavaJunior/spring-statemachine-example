package com.strive.ssm.samples.ordershipping;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;

import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName: StateMachineLogListener
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月16日 9:55
 */
public class StateMachineLogListener extends StateMachineListenerAdapter<String, String> {

    private final LinkedList<String> messages = new LinkedList<>();

    public List<String> getMessages() {
        return messages;
    }

    public void resetMessages() {
        messages.clear();
    }

    @Override
    public void stateContext(StateContext<String, String> stateContext) {
        if (stateContext.getStage() == StateContext.Stage.STATE_ENTRY) {
            messages.addFirst(stateContext.getStateMachine().getId() + " enter " + stateContext.getTarget().getId());
        } else if (stateContext.getStage() == StateContext.Stage.STATE_EXIT) {
            messages.addFirst(stateContext.getStateMachine().getId() + " exit " + stateContext.getSource().getId());
        }
    }
}