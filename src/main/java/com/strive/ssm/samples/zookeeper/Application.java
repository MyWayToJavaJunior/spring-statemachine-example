package com.strive.ssm.samples.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.Bootstrap;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.ensemble.StateMachineEnsemble;
import org.springframework.statemachine.zookeeper.ZookeeperStateMachineEnsemble;

/**
 * @ClassName: Application
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月10日 16:38
 */
@Configuration
public class Application {

    @Configuration
    @EnableStateMachine
    static class StateMachineConfig extends StateMachineConfigurerAdapter<String, String> {

        @Override
        public void configure(StateMachineConfigurationConfigurer<String, String> config) throws Exception {
            config
                    .withDistributed()
                    .ensemble(this.stateMachineEnsemble());
        }

        @Override
        public void configure(StateMachineStateConfigurer<String, String> states) throws Exception {
            states
                    .withStates()
                    .initial("LOCKED")
                    .state("UNLOCKED");
        }

        @Override
        public void configure(StateMachineTransitionConfigurer<String, String> transitions)
                throws Exception {
            transitions
                    .withExternal()
                    .source("LOCKED")
                    .target("UNLOCKED")
                    .event("COIN")
                    .and()
                    .withExternal()
                    .source("UNLOCKED")
                    .target("LOCKED")
                    .event("PUSH");
        }

        @Bean
        public StateMachineEnsemble<String, String> stateMachineEnsemble() throws Exception {
            return new ZookeeperStateMachineEnsemble<>(this.curatorClient(), "/foo");
        }

        @Bean
        public CuratorFramework curatorClient() throws Exception {
            CuratorFramework client = CuratorFrameworkFactory.builder().defaultData(new byte[0])
                    .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                    .connectString("localhost:2181").build();
            client.start();
            return client;
        }

    }

    public static void main(String[] args) throws Exception {
        Bootstrap bootstrap = new Bootstrap(args,new String[]{"classpath*:/zookeeper/spring-shell-plugin.xml"});
        bootstrap.run();
    }
}
