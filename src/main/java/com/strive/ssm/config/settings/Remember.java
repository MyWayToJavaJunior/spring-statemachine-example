package com.strive.ssm.config.settings;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;

/**
 * @ClassName: Remember
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月03日 11:37
 */
@Configuration
@EnableStateMachine
public class Remember extends StateMachineConfigurerAdapter<String, String> {

    @Override
    public void configure(StateMachineStateConfigurer<String, String> states)
            throws Exception {
        states
                .withStates()
                .initial("S1")
                .state("S2");
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<String, String> transitions)
            throws Exception {
        transitions
                .withExternal()
                .source("S1").target("S2").event("E1").guard(this.guard1(true))
                .and()
                .withExternal()
                .source("S1").target("S2").event("E2").guard(this.guard1(false))
                .and()
                .withExternal()
                .source("S1").target("S2").event("E3").guard(this.guard2(true))
                .and()
                .withExternal()
                .source("S1").target("S2").event("E4").guard(this.guard2(false));
    }

    /**
     * 真实情况不应该使用@Bean注解，因为它将被spring代理产生单实例，因此将导致上面的E1事件发生时就创建一个Guard实例，E2事件产生时虽然
     * 设置了false，但也仍然会被返回值为true的实例
     *
     * @param value
     * @return
     */
    public Guard<String, String> guard1(final boolean value) {
        return context -> value;
    }

    public Guard<String, String> guard2(final boolean value) {
        return context -> value;
    }
}
