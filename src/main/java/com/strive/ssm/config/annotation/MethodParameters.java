package com.strive.ssm.config.annotation;

import org.springframework.messaging.Message;
import org.springframework.statemachine.ExtendedState;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.annotation.EventHeaders;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

import java.util.Map;

/**
 * @ClassName: MethodParameters1
 * @Description: 将该bean与id为myMachineId的状态机关联
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月04日 17:43
 */
@WithStateMachine(id = "myMachineId")
public class MethodParameters {

    /**
     * 由于@OnTransition注解的source和target为空，因此将匹配任何的状态转换
     */
    @OnTransition
    public void anyTransition(StateContext<String, String> stateContext) {
    }

    /**
     * 这里的方法的参数对应StateContext接口中的方法返回类型，参数的数量和顺序不受限制
     * <p>
     * 由于@OnTransition注解的source和target为空，因此将匹配任何的状态转换
     *
     * @param headers       事件头信息
     * @param extendedState 扩展状态接口
     * @param stateMachine  状态机接口
     * @param message       消息接口
     * @param e             异常信息
     */
    @OnTransition
    public void anyTransition(
            @EventHeaders Map<String, Object> headers,
            ExtendedState extendedState,
            StateMachine<String, String> stateMachine,
            Message<String> message,
            Exception e) {
    }
}
