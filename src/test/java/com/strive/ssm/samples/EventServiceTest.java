package com.strive.ssm.samples;

import com.strive.ssm.samples.eventservice.Application;
import com.strive.ssm.samples.eventservice.Events;
import com.strive.ssm.samples.eventservice.States;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @ClassName: EventServiceTest
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月15日 10:50
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Application.class, EventServiceTest.Config.class})
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class EventServiceTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Test
    public void testHome() throws Exception {
        mvc.
                perform(get("/state")).
                andExpect(status().isOk());
    }

    @Test
    public void testSendEvent() throws Exception {
        mvc.
                perform(get("/state").param("user", "joe").param("id", "VIEW_I")).
                andExpect(status().isOk()).
                andExpect(content().string(containsString("States: [ITEMS]")));
    }

    @Test
    public void testAdd() throws Exception {
        mvc.
                perform(get("/state").param("user", "joe").param("id", "VIEW_I")).
                andExpect(status().isOk()).
                andExpect(content().string(containsString("States: [ITEMS]")));
        mvc.
                perform(get("/state").param("user", "joe").param("id", "ADD")).
                andExpect(status().isOk()).
                andExpect(content().string(containsString("States: [ITEMS]"))).
                andExpect(content().string(containsString("Extended State: {COUNT=1, ITEMS=1}")));
    }

    @Test
    public void testFeed() throws Exception {
        mvc.
                perform(post("/feed").content("{\"user\":\"joe\",\"id\":\"VIEW_I\"}").contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk());
        mvc.
                perform(post("/feed").content("{\"user\":\"joe\",\"id\":\"ADD\"}").contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk());
        mvc.
                perform(get("/state").param("user", "joe")).
                andExpect(status().isOk()).
                andExpect(content().string(containsString("States: [ITEMS]"))).
                andExpect(content().string(containsString("Extended State: {COUNT=1, ITEMS=1}")));
    }

    @Before
    public void setup() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Configuration
    static class Config {

        @Bean
        public StateMachinePersist<States, Events, String> stateMachinePersist() {
            // use stateMachinePersist without redis to ease testing
            return new InMemoryStateMachinePersist();
        }
    }

    static class InMemoryStateMachinePersist implements StateMachinePersist<States, Events, String> {

        private final HashMap<String, StateMachineContext<States, Events>> contexts = new HashMap<>();

        @Override
        public void write(StateMachineContext<States, Events> context, String contextObj) throws Exception {
            contexts.put(contextObj, context);
        }

        @Override
        public StateMachineContext<States, Events> read(String contextObj) throws Exception {
            return contexts.get(contextObj);
        }
    }
}
