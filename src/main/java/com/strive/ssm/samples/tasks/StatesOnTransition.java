package com.strive.ssm.samples.tasks;

import org.springframework.statemachine.annotation.OnTransition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: StatesOnTransition
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月10日 15:06
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@OnTransition
public @interface StatesOnTransition {

    States[] source() default {};

    States[] target() default {};
}
