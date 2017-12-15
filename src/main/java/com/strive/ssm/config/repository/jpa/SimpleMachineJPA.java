package com.strive.ssm.config.repository.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.data.StateRepository;
import org.springframework.statemachine.data.TransitionRepository;
import org.springframework.statemachine.data.jpa.JpaRepositoryState;
import org.springframework.statemachine.data.jpa.JpaRepositoryTransition;

/**
 * @ClassName: SimpleMachineJPA
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月07日 17:53
 */
public class SimpleMachineJPA {

    @Autowired
    StateRepository<JpaRepositoryState> stateRepository;

    @Autowired
    TransitionRepository<JpaRepositoryTransition> transitionRepository;

    public void addConfig() {
        JpaRepositoryState stateS1 = new JpaRepositoryState("S1", true);
        JpaRepositoryState stateS2 = new JpaRepositoryState("S2");
        JpaRepositoryState stateS3 = new JpaRepositoryState("S3");

        stateRepository.save(stateS1);
        stateRepository.save(stateS2);
        stateRepository.save(stateS3);

        JpaRepositoryTransition transitionS1ToS2 = new JpaRepositoryTransition(stateS1, stateS2, "E1");
        JpaRepositoryTransition transitionS2ToS3 = new JpaRepositoryTransition(stateS2, stateS3, "E2");

        transitionRepository.save(transitionS1ToS2);
        transitionRepository.save(transitionS2ToS3);
    }
}
