package com.strive.ssm.config.persist.memory;

import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachineContextRepository;
import org.springframework.statemachine.StateMachinePersist;

import java.util.HashMap;

/**
 * @ClassName: InMemoryStateMachinePersist
 * @Description: 内存持久化
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月06日 17:30
 */
public class InMemoryStateMachinePersist implements StateMachinePersist<String, String, String> {

    private final HashMap<String, StateMachineContext<String, String>> contexts = new HashMap<>();

    @Override
    public void write(StateMachineContext<String, String> context, String contextObj) throws Exception {
        contexts.put(contextObj, context);
    }

    @Override
    public StateMachineContext<String, String> read(String contextObj) throws Exception {
        return contexts.get(contextObj);
    }
}
