package com.strive.ssm.order.flow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.annotation.*;

/**
 * 该配置实现了com.strive.ssm.order.flow.StateMachineConfig类中定义的状态机监听器
 */
@WithStateMachine(name = "orderStateMachine")
public class EventConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventConfig.class);

    @OnTransition(target = "UNPAID")
    public void create() {
        LOGGER.info("订单创建，待支付");
    }

    @OnTransition(source = "UNPAID", target = "WAITING_FOR_RECEIVE")
    public void pay() {
        LOGGER.info("用户完成支付，待收货");
    }

    @OnTransitionStart(source = "UNPAID", target = "WAITING_FOR_RECEIVE")
    public void payStart() {
        LOGGER.info("用户完成支付，待收货: start");
    }

    @OnTransitionEnd(source = "UNPAID", target = "WAITING_FOR_RECEIVE")
    public void payEnd() {
        LOGGER.info("用户完成支付，待收货: end");
    }

    @OnTransition(source = "WAITING_FOR_RECEIVE", target = "DONE")
    public void receive() {
        LOGGER.info("用户已收货，订单完成");
    }

}