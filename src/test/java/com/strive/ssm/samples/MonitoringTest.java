package com.strive.ssm.samples;

import com.strive.ssm.samples.monitoring.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @ClassName: MonitoringTest
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月17日 17:16
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Application.class}, properties = {"endpoints.default.web.enabled=true"})
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MonitoringTest {

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
    public void testSendEventE1() throws Exception {
        mvc.
                perform(get("/state").param("events", "E1")).
                andExpect(status().isOk()).
                andExpect(content().string(containsString("Exit S1")));
    }

    @Test
    public void testSendEventsE1E2() throws Exception {
        mvc.
                perform(get("/state").param("events", "E1").param("events", "E2")).
                andExpect(status().isOk()).
                andExpect(content().string(allOf(
                        containsString("Exit S1"),
                        containsString("Exit S2"))));
    }

    @Test
    public void testMetrics() throws Exception {
        mvc.
                perform(get("/state").param("events", "E1")).
                andExpect(status().isOk());
        mvc.
                perform(get("/application/metrics")).
                andExpect(jsonPath("$.names", hasItems("ssm.transition.duration", "ssm.transition.transit")));
        mvc.
                perform(get("/application/metrics/ssm.transition.transit")).
                andDo(print()).
                andExpect(jsonPath("$.name", is("ssm.transition.transit"))).
                andExpect(jsonPath("$.measurements[0].value", notNullValue())).
                andExpect(jsonPath("$.availableTags[0].tag", is("transitionName"))).
                andExpect(jsonPath("$.availableTags[0].values", hasItems("EXTERNAL_S1_S2", "INITIAL_S1")));
    }

    @Test
    public void testTrace() throws Exception {
        mvc.
                perform(get("/state")).
                andExpect(status().isOk());
        mvc.
                perform(get("/application/trace")).
                andExpect(jsonPath("$.traces.*.info.transition", containsInAnyOrder("INITIAL_S1")));
    }

    @Before
    public void setup() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
}