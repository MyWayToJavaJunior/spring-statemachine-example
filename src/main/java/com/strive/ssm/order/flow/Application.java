package com.strive.ssm.order.flow;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.StateMachine;

import javax.annotation.Resource;

@SpringBootApplication
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Resource
    private StateMachine<States, Events> orderStateMachine;

    @Override
    public void run(String... args) throws Exception {
        orderStateMachine.start();
        orderStateMachine.sendEvent(Events.PAY);
        orderStateMachine.sendEvent(Events.RECEIVE);
    }
}
