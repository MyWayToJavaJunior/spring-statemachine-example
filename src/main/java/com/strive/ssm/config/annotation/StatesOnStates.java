package com.strive.ssm.config.annotation;

import org.springframework.statemachine.annotation.OnStateChanged;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: StatesOnStates
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月04日 18:57
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@OnStateChanged
public @interface StatesOnStates {

    States[] source() default {};

    States[] target() default {};
}
