package com.strive.ssm.samples.deploy;

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

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @ClassName: DeployTest
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月16日 9:58
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Application.class})
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DeployTest {

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
    public void testDeploy() throws Exception {
        mvc.
                perform(get("/state").param("event", "DEPLOY")).
                andExpect(status().isOk()).
                andExpect(content().string(containsString("States: [READY]")));
    }

    @Test
    public void testUndeploy() throws Exception {
        mvc.
                perform(get("/state").param("event", "UNDEPLOY")).
                andExpect(status().isOk()).
                andExpect(content().string(containsString("States: [READY]")));
    }

    @Before
    public void setup() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
}
