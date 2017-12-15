package com.strive.ssm.config.settings;

import org.springframework.beans.factory.support.StaticListableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;

/**
 * @ClassName: CommonStateMachine
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月03日 10:23
 */
@Configuration
@EnableStateMachine
public class CommonStateMachine extends EnumStateMachineConfigurerAdapter<States, Events> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config)
            throws Exception {
        config
                .withConfiguration()
                // 设置状态机是否自动启动，它只会对最顶层的状态机有效，所有子状态的处理都由状态机自身控制，它们不能被自动启动
                .autoStartup(true)
                .beanFactory(new StaticListableBeanFactory())
                .taskExecutor(new SyncTaskExecutor())
                .taskScheduler(new ConcurrentTaskScheduler())
                // 注册状态机监听器，如果用户想要捕获状态机执行期间的回调信息(比如状态机启动或停止事件的通知)，则必须配置它
                .listener(new StateMachineListenerAdapter<>());
    }
}
