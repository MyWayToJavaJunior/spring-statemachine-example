package com.strive.ssm.config.annotation;

import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

/**
 * @ClassName: ContextIntegration2
 * @Description: 通过@WithStateMachine注解中的name字段可以将指定的bean关联到任何其他的状态机上
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月04日 17:07
 */
@WithStateMachine(name = "myMachineBeanName")
public class ContextIntegration2 {

    @OnTransition
    public void anyTransition() {
    }
}
