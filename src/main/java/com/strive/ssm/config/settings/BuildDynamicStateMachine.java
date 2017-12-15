package com.strive.ssm.config.settings;

import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @ClassName: StateMachineBuilder
 * @Description: 在spring应用之外动态构建状态机
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月03日 17:19
 */
public class BuildDynamicStateMachine {

    StateMachine<String, String> buildMachine1() throws Exception {
        StateMachineBuilder.Builder<String, String> builder = StateMachineBuilder.builder();
        builder.configureStates()
                .withStates()
                .initial("S1")
                .end("SF")
                .states(new HashSet<>(Arrays.asList("S1", "S2", "S3", "S4")));
        return builder.build();
    }

    StateMachine<String, String> buildMachine2() throws Exception {
        StateMachineBuilder.Builder<String, String> builder = StateMachineBuilder.builder();
        builder.configureConfiguration()
                .withConfiguration()
                .autoStartup(false)
                .beanFactory(null)
                .taskExecutor(null)
                .taskScheduler(null)
                .listener(null);
        return builder.build();
    }
}
