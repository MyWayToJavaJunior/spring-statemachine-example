package com.strive.ssm.config.annotation;

import org.springframework.statemachine.annotation.WithStateMachine;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: WithMyBean
 * @Description: @WithStateMachine也可以作为一种元注解，在这种情况下，可以使用自定义的注解来注解所需的bean
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月04日 17:24
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@WithStateMachine(name = "myMachineBeanName")
public @interface WithMyBean {
}
