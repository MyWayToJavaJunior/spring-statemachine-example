package com.strive.ssm.config.interceptor;

import org.springframework.context.ApplicationListener;
import org.springframework.statemachine.event.OnStateMachineError;

/**
 * @ClassName: ErrorApplicationEventListener
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月06日 16:36
 */
public class ErrorApplicationEventListener implements ApplicationListener<OnStateMachineError> {

    @Override
    public void onApplicationEvent(OnStateMachineError event) {
        // do something with error
    }
}