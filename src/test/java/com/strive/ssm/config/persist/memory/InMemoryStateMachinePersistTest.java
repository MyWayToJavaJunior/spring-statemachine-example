package com.strive.ssm.config.persist.memory;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.web.context.support.XmlWebApplicationContext;

public class InMemoryStateMachinePersistTest {

    @Test
    public void testPersist() throws Exception {
        ApplicationContext context = new XmlWebApplicationContext();
        InMemoryStateMachinePersist stateMachinePersist = new InMemoryStateMachinePersist();
        StateMachinePersister<String, String, String> persister = new DefaultStateMachinePersister<>(stateMachinePersist);

        StateMachine<String, String> stateMachine1 = context.getBean("machine1", StateMachine.class);
        StateMachine<String, String> stateMachine2 = context.getBean("machine2", StateMachine.class);
        stateMachine1.start();
        stateMachine1.sendEvent("E1");
        Assert.assertThat(stateMachine1.getState().getIds(), Matchers.contains("S2"));

        persister.persist(stateMachine1, "myid");
        persister.restore(stateMachine2, "myid");
        Assert.assertThat(stateMachine2.getState().getIds(), Matchers.contains("S2"));
    }
}