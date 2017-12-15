package com.strive.ssm.samples.eventservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @ClassName: StateMachineController
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月10日 18:00
 */
@Controller
public class StateMachineController {

    @Autowired
    private StateMachine<States, Events> stateMachine;

    @Autowired
    private StateMachinePersister<States, Events, String> stateMachinePersister;

    @Autowired
    private String stateChartModel;

    @RequestMapping("/")
    public String home() {
        return "redirect:/state";
    }

    @RequestMapping("/state")
    public String feedAndGetState(@RequestParam(value = "user", required = false) String user,
                                  @RequestParam(value = "id", required = false) Events id, Model model) throws Exception {
        model.addAttribute("user", user);
        model.addAttribute("allTypes", Events.values());
        model.addAttribute("stateChartModel", stateChartModel);
        // we may get into this page without a user so
        // do nothing with a state machine
        if (StringUtils.hasText(user)) {
            resetStateMachineFromStore(user);
            if (id != null) {
                feedMachine(user, id);
            }
            model.addAttribute("states", stateMachine.getState().getIds());
            model.addAttribute("extendedState", stateMachine.getExtendedState().getVariables());
        }
        return "states";
    }

    @RequestMapping(value = "/feed", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void feedPageview(@RequestBody() Pageview event) throws Exception {
        Assert.notNull(event.getUser(), "User must be set");
        Assert.notNull(event.getId(), "Id must be set");

        this.resetStateMachineFromStore(event.getUser());
        this.feedMachine(event.getUser(), event.getId());
    }

    private void feedMachine(String user, Events id) throws Exception {
        stateMachine.sendEvent(id);
        stateMachinePersister.persist(stateMachine, "testprefix:" + user);
    }

    private StateMachine<States, Events> resetStateMachineFromStore(String user) throws Exception {
        return stateMachinePersister.restore(stateMachine, "testprefix:" + user);
    }
}