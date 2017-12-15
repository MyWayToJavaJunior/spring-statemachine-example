package com.strive.ssm.config.interceptor;

import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;

/**
 * @ClassName: ErrorStateMachineListener
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月06日 16:27
 */
public class ErrorStateMachineListener extends StateMachineListenerAdapter<String, String> {

    @Override
    public void stateMachineError(StateMachine<String, String> stateMachine, Exception exception) {
        // do something with error
    }
}
