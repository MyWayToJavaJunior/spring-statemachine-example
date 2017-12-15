package com.strive.ssm.config.annotation;

import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

/**
 * @ClassName: ContextIntegration3
 * @Description: 通过@WithStateMachine注解中的id字段可以将指定的bean关联到任何其他的状态机上
 * <p>
 * 有时使用id会更加方便，因为用户可以通过设置状态机的id来区分多个状态机实例。指定的id可以通过StateMachine接口中的getId()方法来获得
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月04日 17:07
 */
@WithStateMachine(id = "myMachineId")
public class ContextIntegration3 {

    @OnTransition
    public void anyTransition() {
    }
}
