package com.strive.ssm.config.repository.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.data.StateRepository;
import org.springframework.statemachine.data.TransitionRepository;
import org.springframework.statemachine.data.jpa.JpaRepositoryState;
import org.springframework.statemachine.data.jpa.JpaRepositoryTransition;

/**
 * @ClassName: SimpleSubMachineJPA
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月08日 10:30
 */
public class SimpleSubMachineJPA {

    @Autowired
    StateRepository<JpaRepositoryState> stateRepository;

    @Autowired
    TransitionRepository<JpaRepositoryTransition> transitionRepository;

    public void addConfig() {
        JpaRepositoryState stateS1 = new JpaRepositoryState("S1", true);
        JpaRepositoryState stateS2 = new JpaRepositoryState("S2");
        JpaRepositoryState stateS3 = new JpaRepositoryState("S3");

        JpaRepositoryState stateS21 = new JpaRepositoryState("S21", true);
        stateS21.setParentState(stateS2);
        JpaRepositoryState stateS22 = new JpaRepositoryState("S22");
        stateS22.setParentState(stateS2);

        stateRepository.save(stateS1);
        stateRepository.save(stateS2);
        stateRepository.save(stateS3);
        stateRepository.save(stateS21);
        stateRepository.save(stateS22);

        JpaRepositoryTransition transitionS1ToS2 = new JpaRepositoryTransition(stateS1, stateS2, "E1");
        JpaRepositoryTransition transitionS2ToS3 = new JpaRepositoryTransition(stateS21, stateS22, "E2");
        JpaRepositoryTransition transitionS21ToS22 = new JpaRepositoryTransition(stateS2, stateS3, "E3");

        transitionRepository.save(transitionS1ToS2);
        transitionRepository.save(transitionS2ToS3);
        transitionRepository.save(transitionS21ToS22);
    }
}
