package com.strive.ssm.config.repository.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.data.StateRepository;
import org.springframework.statemachine.data.TransitionRepository;
import org.springframework.statemachine.data.redis.RedisRepositoryState;
import org.springframework.statemachine.data.redis.RedisRepositoryTransition;

/**
 * @ClassName: SimpleSubMachineRedis
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月09日 17:36
 */
public class SimpleSubMachineRedis {

    @Autowired
    StateRepository<RedisRepositoryState> stateRepository;

    @Autowired
    TransitionRepository<RedisRepositoryTransition> transitionRepository;

    public void addConfig() {
        RedisRepositoryState stateS1 = new RedisRepositoryState("S1", true);
        RedisRepositoryState stateS2 = new RedisRepositoryState("S2");
        RedisRepositoryState stateS3 = new RedisRepositoryState("S3");

        stateRepository.save(stateS1);
        stateRepository.save(stateS2);
        stateRepository.save(stateS3);


        RedisRepositoryTransition transitionS1ToS2 = new RedisRepositoryTransition(stateS1, stateS2, "E1");
        RedisRepositoryTransition transitionS2ToS3 = new RedisRepositoryTransition(stateS2, stateS3, "E2");

        transitionRepository.save(transitionS1ToS2);
        transitionRepository.save(transitionS2ToS3);
    }
}
