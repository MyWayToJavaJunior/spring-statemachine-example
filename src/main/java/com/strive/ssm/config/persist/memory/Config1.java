package com.strive.ssm.config.persist.memory;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;

/**
 * @ClassName: Config1
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月06日 17:29
 */
@Configuration
@EnableStateMachine(name = "machine1")
public class Config1 extends Config {
}
