package com.strive.ssm.config.accessor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.access.StateMachineAccess;

/**
 * @ClassName: StateMachineAccessor
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月06日 10:29
 */
public class StateMachineAccessor {

    @Autowired
    private StateMachine<String, String> stateMachine;

    /**
     * doWithAllRegions方法可以获取对状态机中所有Region实例的访问
     */
    public void accessor_one() {
        stateMachine.getStateMachineAccessor().doWithAllRegions(function -> function.setRelay(stateMachine));
    }

    /**
     * doWithRegion方法可以获取对状态机中单个Region实例的访问
     */
    public void accessor_two() {
        stateMachine.getStateMachineAccessor().doWithRegion(function -> function.setRelay(stateMachine));
    }

    public void accessor_three() {
        for (StateMachineAccess<String, String> access : stateMachine.getStateMachineAccessor().withAllRegions()) {
            access.setRelay(stateMachine);
        }
    }

    public void accessor_four() {
        stateMachine.getStateMachineAccessor().withAllRegions().forEach(access -> access.setRelay(stateMachine));
    }

    public void accessor_five() {
        stateMachine.getStateMachineAccessor().withRegion().setRelay(stateMachine);
    }
}
