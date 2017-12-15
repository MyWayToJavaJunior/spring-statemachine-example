package com.strive.ssm.config.annotation;

import org.springframework.statemachine.ExtendedState;
import org.springframework.statemachine.annotation.EventHeaders;
import org.springframework.statemachine.annotation.WithStateMachine;

import java.util.Map;

/**
 * @ClassName: TransitionAnnotation2
 * @Description: 将该bean与id为myMachineId的状态机关联
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月04日 18:40
 */
@WithStateMachine(id = "myMachineId")
public class TransitionAnnotation2 {

    @StatesOnTransition(source = States.S1, target = States.S2)
    public void fromS1ToS2(@EventHeaders Map<String, Object> headers, ExtendedState extendedState) {
    }

    @StatesOnTransition(source = States.S1, target = States.S2)
    public void fromS1ToS2() {
    }
}
