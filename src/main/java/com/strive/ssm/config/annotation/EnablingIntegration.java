package com.strive.ssm.config.annotation;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.annotation.OnStateChanged;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;

/**
 * @ClassName: EnablingIntegration
 * @Description: 可以通过使用@EnableWithStateMachine注解来启用@WithStateMachine注解的所有功能，只需在Spring应用程序上下文中导入
 * 所需的配置即可。由于@EnableStateMachine和@EnableStateMachineFactory注解已经添加了@EnableWithStateMachine注解的功能，因此用户不
 * 需要再次导入@EnableWithStateMachine注解
 * <p>
 * 如果状态机不是通过Bean的方式来创建的，则必须给该状态机指定BeanFactory，否则状态机将不会处理被@WithStateMachine注解的bean中的方法
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月04日 17:34
 */
public class EnablingIntegration {

    public static StateMachine<String, String> buildMachine(BeanFactory beanFactory) throws Exception {
        StateMachineBuilder.Builder<String, String> builder = StateMachineBuilder.builder();

        builder.configureConfiguration()
                .withConfiguration()
                .machineId("myMachineId")
                .beanFactory(beanFactory);

        builder.configureStates()
                .withStates()
                .initial("S1")
                .state("S2");

        builder.configureTransitions()
                .withExternal()
                .source("S1")
                .target("S2")
                .event("E1");

        return builder.build();
    }

    /**
     * 将该bean与id为myMachineId的状态机关联
     */
    @WithStateMachine(id = "myMachineId")
    static class Bean17 {

        @OnStateChanged
        public void onStateChanged() {
        }
    }
}
