package com.strive.ssm.samples.ordershipping;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.test.StateMachineTestPlan;
import org.springframework.statemachine.test.StateMachineTestPlanBuilder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @ClassName: StateMachineTest
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月17日 14:44
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {StateMachineConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class StateMachineTest {

    @Autowired
    private StateMachineFactory<String, String> stateMachineFactory;

    private StateMachine<String, String> stateMachine;

    @Before
    public void setup() throws Exception {
        stateMachine = stateMachineFactory.getStateMachine();
        // plan don't know how to wait if machine is started
        // automatically so wait here.
        for (int i = 0; i < 10; i++) {
            if (stateMachine.getState() != null) {
                break;
            } else {
                Thread.sleep(200);
            }
        }
    }

    @Test
    public void testInitial() throws Exception {
        StateMachineTestPlan<String, String> plan =
                StateMachineTestPlanBuilder.<String, String>builder()
                        .stateMachine(stateMachine)
                        .step()
                        .expectState("WAIT_NEW_ORDER")
                        .and()
                        .build();
        plan.test();
    }

    @Test
    public void testNoCustomerOrOrder() throws Exception {
        StateMachineTestPlan<String, String> plan =
                StateMachineTestPlanBuilder.<String, String>builder()
                        .stateMachine(stateMachine)
                        .step()
                        .expectState("WAIT_NEW_ORDER")
                        .and()
                        .step()
                        .sendEvent("PLACE_ORDER")
                        .expectStates("CUSTOMER_ERROR")
                        .expectStateChanged(2)
                        .expectStateMachineStopped(1)
                        .and()
                        .build();
        plan.test();
    }

    @Test
    public void testPlaceOrder() throws Exception {
        StateMachineTestPlan<String, String> plan =
                StateMachineTestPlanBuilder.<String, String>builder()
                        .stateMachine(stateMachine)
                        .step()
                        .expectState("WAIT_NEW_ORDER")
                        .and()
                        .step()
                        .sendEvent(MessageBuilder.withPayload("PLACE_ORDER")
                                .setHeader("customer", "customer1")
                                .setHeader("order", "order1").build())
                        .expectStates("HANDLE_ORDER", "WAIT_PAYMENT", "WAIT_PRODUCT")
                        .expectStateChanged(8)
                        .and()
                        .step()
                        .sendEvent(MessageBuilder.withPayload("RECEIVE_PAYMENT")
                                .setHeader("payment", "1000").build())
                        .expectStates("ORDER_SHIPPED")
                        .expectStateChanged(4)
                        .expectStateMachineStopped(3)
                        .and()
                        .build();
        plan.test();
    }

    @Test
    public void testNoPayment() throws Exception {
        StateMachineTestPlan<String, String> plan =
                StateMachineTestPlanBuilder.<String, String>builder()
                        .stateMachine(stateMachine)
                        .step()
                        .expectState("WAIT_NEW_ORDER")
                        .and()
                        .step()
                        .sendEvent(MessageBuilder.withPayload("PLACE_ORDER")
                                .setHeader("customer", "customer1")
                                .setHeader("order", "order1").build())
                        .expectStates("HANDLE_ORDER", "WAIT_PAYMENT", "WAIT_PRODUCT")
                        .expectStateChanged(8)
                        .and()
                        .step()
                        .sendEvent(MessageBuilder.withPayload("RECEIVE_PAYMENT").build())
                        .expectStates("HANDLE_ORDER", "WAIT_PAYMENT", "WAIT_PRODUCT")
                        .expectStateChanged(4)
                        .and()
                        .build();
        plan.test();
    }

    @Test
    public void testReminder() throws Exception {
        StateMachineTestPlan<String, String> plan =
                StateMachineTestPlanBuilder.<String, String>builder()
                        .stateMachine(stateMachine)
                        .step()
                        .expectState("WAIT_NEW_ORDER")
                        .and()
                        .step()
                        .sendEvent(MessageBuilder.withPayload("PLACE_ORDER")
                                .setHeader("customer", "customer1")
                                .setHeader("order", "order1").build())
                        .expectStates("HANDLE_ORDER", "WAIT_PAYMENT", "WAIT_PRODUCT")
                        .expectStateChanged(8)
                        .and()
                        .step()
                        .expectStateChanged(2)
                        .and()
                        .build();
        plan.test();
    }

}
