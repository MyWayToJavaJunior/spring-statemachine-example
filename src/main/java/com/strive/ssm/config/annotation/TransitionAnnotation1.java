package com.strive.ssm.config.annotation;

import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

/**
 * @ClassName: TransitionAnnotation1
 * @Description: 将该bean与id为myMachineId的状态机关联
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月04日 18:40
 */
@WithStateMachine(id = "myMachineId")
public class TransitionAnnotation1 {

    /**
     * 匹配状态从S1到S2的转换，由于java语言的限制，因此@OnTransition注解中的source和target必须为字符串
     */
    @OnTransition(source = "S1", target = "S2")
    public void fromS1ToS2() {
    }

    /**
     * 由于@OnTransition注解的source和target为空，因此将匹配任何的状态转换
     */
    @OnTransition
    public void anyTransition() {
    }
}
