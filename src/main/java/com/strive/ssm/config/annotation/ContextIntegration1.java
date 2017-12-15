package com.strive.ssm.config.annotation;

import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

/**
 * @ClassName: ContextIntegration1
 * @Description: @WithStateMachine注解可用于将状态机与存在的bean相关联，然后可以开始将支持的注解添加到该bean的方法中。
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月04日 17:07
 */
@WithStateMachine
public class ContextIntegration1 {

    @OnTransition
    public void anyTransition() {
    }
}
