package com.strive.ssm.config.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptor;
import org.springframework.statemachine.transition.Transition;

/**
 * @ClassName: StateMachineInterceptor
 * @Description: 代替StateMachineListener监听器的一种方式是使用StateMachineInterceptor拦截器。
 * <p>
 * 一个概念上的区别是拦截器可以用来拦截和停止当前状态变化或转换逻辑。
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月06日 10:58
 */
public class StateMachineInterceptorExample {

    @Autowired
    private StateMachine<String, String> stateMachine;

    /**
     * 给状态机中的所有区域添加拦截器
     */
    public void registerInterceptor() {
        stateMachine.getStateMachineAccessor()
                .withRegion().addStateMachineInterceptor(new StateMachineInterceptor<String, String>() {

            @Override
            public Message<String> preEvent(Message<String> message, StateMachine<String, String> stateMachine) {
                return message;
            }

            @Override
            public StateContext<String, String> preTransition(StateContext<String, String> stateContext) {
                return stateContext;
            }

            @Override
            public void preStateChange(State<String, String> state, Message<String> message,
                                       Transition<String, String> transition, StateMachine<String, String> stateMachine) {
            }

            @Override
            public StateContext<String, String> postTransition(StateContext<String, String> stateContext) {
                return stateContext;
            }

            @Override
            public void postStateChange(State<String, String> state, Message<String> message,
                                        Transition<String, String> transition, StateMachine<String, String> stateMachine) {
            }

            @Override
            public Exception stateMachineError(StateMachine<String, String> stateMachine,
                                               Exception exception) {
                return exception;
            }
        });
    }
}
