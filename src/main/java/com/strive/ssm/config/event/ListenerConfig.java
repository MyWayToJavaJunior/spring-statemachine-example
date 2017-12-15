package com.strive.ssm.config.event;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.event.StateMachineEvent;

/**
 * @ClassName: ListenerConfig
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月04日 16:52
 */
@Configuration
public class ListenerConfig {

    @Bean
    public StateMachineApplicationEventListener contextListener() {
        return new StateMachineApplicationEventListener();
    }

    public class StateMachineApplicationEventListener implements ApplicationListener<StateMachineEvent> {

        @Override
        public void onApplicationEvent(StateMachineEvent event) {
        }
    }
}
