package com.strive.ssm.config.annotation;

import org.springframework.messaging.Message;
import org.springframework.statemachine.ExtendedState;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.annotation.EventHeaders;
import org.springframework.statemachine.annotation.OnExtendedStateChanged;
import org.springframework.statemachine.annotation.WithStateMachine;

import java.util.Map;

/**
 * @ClassName: ExtendedStateAnnotation
 * @Description: 将该bean与id为myMachineId的状态机关联
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月05日 10:10
 */
@WithStateMachine(id = "myMachineId")
public class ExtendedStateAnnotation {

    /**
     * 由于@OnExtendedStateChanged注解的key为空，因此将监听任何的扩展状态的变化
     */
    @OnExtendedStateChanged
    public void anyStateChange() {
    }

    @OnExtendedStateChanged
    public void anyStateChangeWithParameter(
            @EventHeaders Map<String, Object> headers,
            ExtendedState extendedState,
            StateMachine<String, String> stateMachine,
            Message<String> message,
            Exception e) {
    }

    /**
     * 只监听指定key的变化
     */
    @OnExtendedStateChanged(key = "key1")
    public void key1Changed() {
    }

    /**
     * 只监听指定key的变化并提供方法参数
     */
    @OnExtendedStateChanged(key = "key1")
    public void key1ChangedWithParameter(
            @EventHeaders Map<String, Object> headers,
            ExtendedState extendedState,
            StateMachine<String, String> stateMachine,
            Message<String> message,
            Exception e) {
    }
}
