package com.strive.ssm.config.distribute;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.ensemble.StateMachineEnsemble;
import org.springframework.statemachine.zookeeper.ZookeeperStateMachineEnsemble;

/**
 * @ClassName: ZookeeperDistributedStateMachine
 * @Description: 基于Zookeeper实现的分布式状态机
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月03日 10:23
 */
@Configuration
@EnableStateMachine
public class ZookeeperDistributedStateMachine extends StateMachineConfigurerAdapter<String, String> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<String, String> config)
            throws Exception {
        config
                .withDistributed()
                .ensemble(this.stateMachineEnsemble())
                .and()
                .withConfiguration()
                .autoStartup(true);
    }

    @Override
    public void configure(StateMachineStateConfigurer<String, String> states)
            throws Exception {
        // config states
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<String, String> transitions)
            throws Exception {
        // config transitions
    }

    public StateMachineEnsemble<String, String> stateMachineEnsemble() throws Exception {
        return new ZookeeperStateMachineEnsemble<>(this.curatorClient(), "/zkpath");
    }

    public CuratorFramework curatorClient() throws Exception {
        CuratorFramework client = CuratorFrameworkFactory
                .builder()
                .defaultData(new byte[0])
                .connectString("localhost:2181")
                .build();
        client.start();
        return client;
    }
}
