package com.strive.ssm.config.settings;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineModelConfigurer;
import org.springframework.statemachine.config.model.ConfigurationData;
import org.springframework.statemachine.config.model.DefaultStateMachineModel;
import org.springframework.statemachine.config.model.StateData;
import org.springframework.statemachine.config.model.StateMachineModel;
import org.springframework.statemachine.config.model.StateMachineModelFactory;
import org.springframework.statemachine.config.model.StatesData;
import org.springframework.statemachine.config.model.TransitionData;
import org.springframework.statemachine.config.model.TransitionsData;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @ClassName: ConfigModel
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月03日 10:52
 */
@Configuration
@EnableStateMachine
public class ConfigModel extends StateMachineConfigurerAdapter<String, String> {

    @Override
    public void configure(StateMachineModelConfigurer<String, String> model) throws Exception {
        model
                .withModel()
                .factory(this.modelFactory());
    }

    public StateMachineModelFactory<String, String> modelFactory() {
        return new CustomStateMachineModelFactory();
    }

    public static class CustomStateMachineModelFactory implements StateMachineModelFactory<String, String> {

        @Override
        public StateMachineModel<String, String> build() {
            ConfigurationData<String, String> configurationData = new ConfigurationData<>();
            Collection<StateData<String, String>> stateData = new ArrayList<>();
            stateData.add(new StateData<>("S1", true));
            stateData.add(new StateData<>("S2"));
            StatesData<String, String> statesData = new StatesData<>(stateData);

            Collection<TransitionData<String, String>> transitionData = new ArrayList<>();
            transitionData.add(new TransitionData<>("S1", "S2", "E1"));
            TransitionsData<String, String> transitionsData = new TransitionsData<>(transitionData);

            return new DefaultStateMachineModel<>(configurationData, statesData, transitionsData);
        }

        @Override
        public StateMachineModel<String, String> build(String machineId) {
            return this.build();
        }
    }
}
