package com.strive.ssm.samples.deploy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineModelConfigurer;
import org.springframework.statemachine.config.model.StateMachineModelFactory;
import org.springframework.statemachine.guard.Guard;
import org.springframework.statemachine.uml.UmlStateMachineModelFactory;

/**
 * @ClassName: StateMachineConfig
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月10日 17:27
 */
@Configuration
public class StateMachineConfig {

    @Configuration
    @EnableStateMachine
    public static class Config extends StateMachineConfigurerAdapter<String, String> {

        @Override
        public void configure(StateMachineConfigurationConfigurer<String, String> config)
                throws Exception {
            config
                    .withConfiguration()
                    .autoStartup(true)
                    .listener(this.stateMachineLogListener());
        }

        @Override
        public void configure(StateMachineModelConfigurer<String, String> model)
                throws Exception {
            model
                    .withModel()
                    .factory(modelFactory());
        }

        @Bean
        public StateMachineModelFactory<String, String> modelFactory() {
            return new UmlStateMachineModelFactory("classpath:model.uml");
        }

        @Bean
        public StateMachineLogListener stateMachineLogListener() {
            return new StateMachineLogListener();
        }

        @Bean
        public Action<String, String> errorEntryAction() {
            return (context) -> System.out.println("Should handle error " + context.getMessageHeader("error"));
        }

        @Bean
        public Action<String, String> readyEntryction() {
            return (context) -> context.getExtendedState().getVariables().clear();
        }

        @Bean
        public Action<String, String> preparedeployEntryAction() {
            return (context) -> System.out.println("Handle deploy prepare here");
        }

        @Bean
        public Action<String, String> installEntryAction() {
            return (context) -> System.out.println("Handle install here");
        }

        @Bean
        public Action<String, String> startEntryAction() {
            return (context) -> System.out.println("Handle start here");
        }

        @Bean
        public Action<String, String> stopEntryAction() {
            return (context) -> System.out.println("Handle stop here");
        }

        @Bean
        public Action<String, String> exitErrorAction() {
            return (context) -> {
                if (context.getMessageHeaders().containsKey("hasError")) {
                    context.getExtendedState().getVariables().put("hasError", true);
                    context.getExtendedState().getVariables().put("error", new RuntimeException("Exception set in machine"));
                }
                if (context.getMessageHeaders().containsKey("isInstalled")) {
                    context.getExtendedState().getVariables().put("isInstalled", true);
                }
                if (context.getMessageHeaders().containsKey("installedOk")) {
                    context.getExtendedState().getVariables().put("installedOk", true);
                }
                if (context.getMessageHeaders().containsKey("isRunning")) {
                    context.getExtendedState().getVariables().put("isRunning", true);
                }
            };
        }

        @Bean
        public Guard<String, String> isInstalledGuard() {
            return (context) -> context.getExtendedState().getVariables().containsKey("isInstalled");
        }

        @Bean
        public Guard<String, String> installedOkGuard() {
            return (context) -> context.getExtendedState().getVariables().containsKey("installedOk");
        }

        @Bean
        public Guard<String, String> isRunningGuard() {
            return (context) -> context.getExtendedState().getVariables().containsKey("isRunning");
        }

        @Bean
        public Guard<String, String> hasErrorGuard() {
            return (context) -> context.getExtendedState().getVariables().containsKey("hasError");
        }
    }
}