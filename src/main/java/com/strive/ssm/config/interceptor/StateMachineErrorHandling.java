package com.strive.ssm.config.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;

/**
 * @ClassName: StateMachineErrorHandling
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月06日 16:25
 */
public class StateMachineErrorHandling {

    @Autowired
    private StateMachine<String, String> stateMachine;

    public void addInterceptor() {
        stateMachine.getStateMachineAccessor().doWithRegion(function -> function.addStateMachineInterceptor(
                new StateMachineInterceptorAdapter<String, String>() {
                    @Override
                    public Exception stateMachineError(StateMachine<String, String> stateMachine,
                                                       Exception exception) {
                        // 当状态机执行转换时出现异常进行拦截并处理
                        return exception;
                    }
                }
        ));

    }
}
