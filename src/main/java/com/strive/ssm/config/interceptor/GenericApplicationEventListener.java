package com.strive.ssm.config.interceptor;

import org.springframework.context.ApplicationListener;
import org.springframework.statemachine.event.OnStateMachineError;
import org.springframework.statemachine.event.StateMachineEvent;

/**
 * @ClassName: GenericApplicationEventListener
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月06日 16:28
 */
public class GenericApplicationEventListener implements ApplicationListener<StateMachineEvent> {

    @Override
    public void onApplicationEvent(StateMachineEvent event) {
        if (event instanceof OnStateMachineError) {
            // do something with error
        }
    }
}
