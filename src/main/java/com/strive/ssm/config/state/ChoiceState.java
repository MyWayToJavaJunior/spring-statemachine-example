package com.strive.ssm.config.state;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;

import java.util.EnumSet;

/**
 * @ClassName: ChoiceState
 * @Description: 选择状态
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月02日 17:56
 */
@Configuration
@EnableStateMachine
public class ChoiceState extends EnumStateMachineConfigurerAdapter<States, Events> {

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states)
            throws Exception {
        states
                .withStates()
                .initial(States.SI)
                .choice(States.S1)
                .end(States.SF)
                .states(EnumSet.allOf(States.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions)
            throws Exception {
        transitions
                .withChoice() // 让转换器执行choice任务
                .source(States.S1) // 执行choice任务时的源状态
                .first(States.S2, this.s2Guard()) // 指定一个目标状态作为choice的第一个选择，相当于if/else if/else结构中的if
                .then(States.S3, this.s3Guard()) // 指定一个目标状态作为choice的第二个选择，相当于if/else if/else结构中的else if
                .last(States.S4); // 指定一个目标状态作为choice的最后一个选择，相当于if/else if/else结构中的else
    }

    public Guard<States, Events> s2Guard() {
        return context -> false;
    }

    public Guard<States, Events> s3Guard() {
        return context -> true;
    }
}
