package com.strive.ssm.config.repository.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.data.StateRepository;
import org.springframework.statemachine.data.TransitionRepository;
import org.springframework.statemachine.data.mongodb.MongoDbRepositoryState;
import org.springframework.statemachine.data.mongodb.MongoDbRepositoryTransition;

/**
 * @ClassName: SimpleMachineMongoDB
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月09日 17:39
 */
public class SimpleMachineMongoDB {

    @Autowired
    StateRepository<MongoDbRepositoryState> stateRepository;

    @Autowired
    TransitionRepository<MongoDbRepositoryTransition> transitionRepository;

    public void addConfig() {
        MongoDbRepositoryState stateS1 = new MongoDbRepositoryState("S1", true);
        MongoDbRepositoryState stateS2 = new MongoDbRepositoryState("S2");
        MongoDbRepositoryState stateS3 = new MongoDbRepositoryState("S3");

        stateRepository.save(stateS1);
        stateRepository.save(stateS2);
        stateRepository.save(stateS3);

        MongoDbRepositoryTransition transitionS1ToS2 = new MongoDbRepositoryTransition(stateS1, stateS2, "E1");
        MongoDbRepositoryTransition transitionS2ToS3 = new MongoDbRepositoryTransition(stateS2, stateS3, "E2");

        transitionRepository.save(transitionS1ToS2);
        transitionRepository.save(transitionS2ToS3);
    }
}
