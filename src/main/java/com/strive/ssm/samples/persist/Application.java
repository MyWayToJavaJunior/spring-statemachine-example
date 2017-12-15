package com.strive.ssm.samples.persist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.Bootstrap;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.recipes.persist.PersistStateMachineHandler;

/**
 * @ClassName: Application
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月10日 15:58
 */
@SpringBootApplication
public class Application {

    @Configuration
    @EnableStateMachine
    static class StateMachineConfig extends StateMachineConfigurerAdapter<String, String> {

        @Override
        public void configure(StateMachineStateConfigurer<String, String> states) throws Exception {
            states
                    .withStates()
                    .initial("PLACED")
                    .state("PROCESSING")
                    .state("SENT")
                    .state("DELIVERED");
        }

        @Override
        public void configure(StateMachineTransitionConfigurer<String, String> transitions) throws Exception {
            transitions
                    .withExternal()
                    .source("PLACED").target("PROCESSING")
                    .event("PROCESS")
                    .and()
                    .withExternal()
                    .source("PROCESSING").target("SENT")
                    .event("SEND")
                    .and()
                    .withExternal()
                    .source("SENT").target("DELIVERED")
                    .event("DELIVER");
        }

    }

    @Configuration
    static class PersistHandlerConfig {

        @Autowired
        private StateMachine<String, String> stateMachine;

        @Bean
        public Persist persist() {
            return new Persist(persistStateMachineHandler());
        }

        @Bean
        public PersistStateMachineHandler persistStateMachineHandler() {
            return new PersistStateMachineHandler(stateMachine);
        }

    }

    public static void main(String[] args) throws Exception {
        Bootstrap bootstrap = new Bootstrap(args,new String[]{"classpath*:/persist/spring-shell-plugin.xml"});
        bootstrap.run();
    }
}
