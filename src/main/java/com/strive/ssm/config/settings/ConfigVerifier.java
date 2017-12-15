package com.strive.ssm.config.settings;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.model.verifier.StateMachineModelVerifier;

/**
 * @ClassName: ConfigVerifier
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月03日 10:47
 */
@Configuration
@EnableStateMachine
public class ConfigVerifier extends EnumStateMachineConfigurerAdapter<States, Events> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config)
            throws Exception {
        config
                .withVerifier()
                .enabled(true)
                .verifier(this.verifier());
    }

    public StateMachineModelVerifier<States, Events> verifier() {
        return model -> {
            // throw exception indicating malformed model
        };
    }
}
