package com.strive.ssm.config.event;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;

/**
 * @ClassName: DisableSpringContextEvent
 * @Description: 为了使得状态机具有更好的性能，建议使用StateMachineListener接口。
 * <p>
 * 基于这个原因，可以在@EnableStateMachine注解中将contextEvents标识设置为false从而禁用spring应用上下文事件
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月04日 16:59
 */
@Configuration
@EnableStateMachine(contextEvents = false)
public class DisableSpringContextEvent1 extends EnumStateMachineConfigurerAdapter<States, Events> {
}
