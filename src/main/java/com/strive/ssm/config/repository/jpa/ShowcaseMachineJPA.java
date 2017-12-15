package com.strive.ssm.config.repository.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.data.ActionRepository;
import org.springframework.statemachine.data.GuardRepository;
import org.springframework.statemachine.data.StateRepository;
import org.springframework.statemachine.data.TransitionRepository;
import org.springframework.statemachine.data.jpa.JpaRepositoryAction;
import org.springframework.statemachine.data.jpa.JpaRepositoryGuard;
import org.springframework.statemachine.data.jpa.JpaRepositoryState;
import org.springframework.statemachine.data.jpa.JpaRepositoryTransition;
import org.springframework.statemachine.transition.TransitionKind;

import java.util.Collections;
import java.util.HashSet;

/**
 * @ClassName: ShowcaseMachineJPA
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月08日 16:09
 */
public class ShowcaseMachineJPA {

    /**
     * 访问所有的repositories
     */
    @Autowired
    StateRepository<JpaRepositoryState> stateRepository;
    @Autowired
    TransitionRepository<JpaRepositoryTransition> transitionRepository;
    @Autowired
    ActionRepository<JpaRepositoryAction> actionRepository;
    @Autowired
    GuardRepository<JpaRepositoryGuard> guardRepository;

    public void addConfig() {
        /**
         * 创建Action和Guard
         */
        JpaRepositoryGuard foo0Guard = new JpaRepositoryGuard();
        foo0Guard.setName("foo0Guard");

        JpaRepositoryGuard foo1Guard = new JpaRepositoryGuard();
        foo1Guard.setName("foo1Guard");

        JpaRepositoryAction fooAction = new JpaRepositoryAction();
        fooAction.setName("fooAction");

        guardRepository.save(foo0Guard);
        guardRepository.save(foo1Guard);
        actionRepository.save(fooAction);

        /**
         * 创建State
         */
        JpaRepositoryState stateS0 = new JpaRepositoryState("S0", true);
        stateS0.setInitialAction(fooAction);
        JpaRepositoryState stateS1 = new JpaRepositoryState("S1", true);
        stateS1.setParentState(stateS0);
        JpaRepositoryState stateS11 = new JpaRepositoryState("S11", true);
        stateS11.setParentState(stateS1);
        JpaRepositoryState stateS12 = new JpaRepositoryState("S12");
        stateS12.setParentState(stateS1);
        JpaRepositoryState stateS2 = new JpaRepositoryState("S2");
        stateS2.setParentState(stateS0);
        JpaRepositoryState stateS21 = new JpaRepositoryState("S21", true);
        stateS21.setParentState(stateS2);
        JpaRepositoryState stateS211 = new JpaRepositoryState("S211", true);
        stateS211.setParentState(stateS21);
        JpaRepositoryState stateS212 = new JpaRepositoryState("S212");
        stateS212.setParentState(stateS21);

        stateRepository.save(stateS0);
        stateRepository.save(stateS1);
        stateRepository.save(stateS11);
        stateRepository.save(stateS12);
        stateRepository.save(stateS2);
        stateRepository.save(stateS21);
        stateRepository.save(stateS211);
        stateRepository.save(stateS212);

        /**
         * 创建Transition
         */
        JpaRepositoryTransition transitionS1ToS1 = new JpaRepositoryTransition(stateS1, stateS1, "A");
        transitionS1ToS1.setGuard(foo1Guard);

        JpaRepositoryTransition transitionS1ToS11 = new JpaRepositoryTransition(stateS1, stateS11, "B");
        JpaRepositoryTransition transitionS21ToS211 = new JpaRepositoryTransition(stateS21, stateS211, "B");
        JpaRepositoryTransition transitionS1ToS2 = new JpaRepositoryTransition(stateS1, stateS2, "C");
        JpaRepositoryTransition transitionS1ToS0 = new JpaRepositoryTransition(stateS1, stateS0, "D");
        JpaRepositoryTransition transitionS211ToS21 = new JpaRepositoryTransition(stateS211, stateS21, "D");
        JpaRepositoryTransition transitionS0ToS211 = new JpaRepositoryTransition(stateS0, stateS211, "E");
        JpaRepositoryTransition transitionS1ToS211 = new JpaRepositoryTransition(stateS1, stateS211, "F");
        JpaRepositoryTransition transitionS2ToS21 = new JpaRepositoryTransition(stateS2, stateS21, "F");
        JpaRepositoryTransition transitionS11ToS211 = new JpaRepositoryTransition(stateS11, stateS211, "G");

        JpaRepositoryTransition transitionS0 = new JpaRepositoryTransition(stateS0, stateS0, "H");
        transitionS0.setKind(TransitionKind.INTERNAL);
        transitionS0.setGuard(foo0Guard);
        transitionS0.setActions(new HashSet<>(Collections.singletonList(fooAction)));

        JpaRepositoryTransition transitionS1 = new JpaRepositoryTransition(stateS1, stateS1, "H");
        transitionS1.setKind(TransitionKind.INTERNAL);

        JpaRepositoryTransition transitionS2 = new JpaRepositoryTransition(stateS2, stateS2, "H");
        transitionS2.setKind(TransitionKind.INTERNAL);
        transitionS2.setGuard(foo1Guard);
        transitionS2.setActions(new HashSet<>(Collections.singletonList(fooAction)));

        JpaRepositoryTransition transitionS11ToS12 = new JpaRepositoryTransition(stateS11, stateS12, "I");
        JpaRepositoryTransition transitionS12ToS212 = new JpaRepositoryTransition(stateS12, stateS212, "I");
        JpaRepositoryTransition transitionS211ToS12 = new JpaRepositoryTransition(stateS211, stateS12, "I");

        JpaRepositoryTransition transitionS11 = new JpaRepositoryTransition(stateS11, stateS11, "J");
        JpaRepositoryTransition transitionS2ToS1 = new JpaRepositoryTransition(stateS2, stateS1, "K");

        transitionRepository.save(transitionS1ToS1);
        transitionRepository.save(transitionS1ToS11);
        transitionRepository.save(transitionS21ToS211);
        transitionRepository.save(transitionS1ToS2);
        transitionRepository.save(transitionS1ToS0);
        transitionRepository.save(transitionS211ToS21);
        transitionRepository.save(transitionS0ToS211);
        transitionRepository.save(transitionS1ToS211);
        transitionRepository.save(transitionS2ToS21);
        transitionRepository.save(transitionS11ToS211);
        transitionRepository.save(transitionS0);
        transitionRepository.save(transitionS1);
        transitionRepository.save(transitionS2);
        transitionRepository.save(transitionS11ToS12);
        transitionRepository.save(transitionS12ToS212);
        transitionRepository.save(transitionS211ToS12);
        transitionRepository.save(transitionS11);
        transitionRepository.save(transitionS2ToS1);
    }
}
