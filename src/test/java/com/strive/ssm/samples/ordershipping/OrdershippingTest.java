package com.strive.ssm.samples.ordershipping;

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
 * @ClassName: OrdershippingTest
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月16日 14:18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Application.class})
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class OrdershippingTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Test
    public void testHome() throws Exception {
        mvc.
                perform(get("/state")).
                andExpect(status().isOk()).
                andExpect(content().string(containsString("Machines:")));
    }

    @Test
    public void testCreateOrder() throws Exception {
        mvc.
                perform(get("/state").
                        param("action", "order").
                        param("customer", "customer1").
                        param("order", "order1")).
                andExpect(status().isOk()).
                andExpect(content().string(containsString("customer1:order1"))).
                andExpect(content().string(containsString("[WAIT_NEW_ORDER]")));

        mvc.
                perform(get("/state").
                        param("action", "event").
                        param("event", "PLACE_ORDER").
                        param("customer", "customer1").
                        param("order", "order1")).
                andExpect(status().isOk()).
                andExpect(content().string(containsString("customer1:order1"))).
                andExpect(content().string(containsString("[HANDLE_ORDER, WAIT_PRODUCT, WAIT_PAYMENT]")));


        mvc.
                perform(get("/state").
                        param("action", "event").
                        param("event", "RECEIVE_PAYMENT").
                        param("guide", "payment").
                        param("customer", "customer1").
                        param("order", "order1")).
                andExpect(status().isOk()).
                andExpect(content().string(containsString("customer1:order1"))).
                andExpect(content().string(containsString("[ORDER_SHIPPED]")));
    }

    @Before
    public void setup() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
}
