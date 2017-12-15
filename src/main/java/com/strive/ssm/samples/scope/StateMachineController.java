package com.strive.ssm.samples.scope;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    @Qualifier("stateChartModel")
    private String stateChartModel;

    @RequestMapping("/")
    public String greeting() {
        return "redirect:/states";
    }

    @RequestMapping("/states")
    public String getStates(@RequestParam(value = "event", required = false) Events event, Model model) {
        if (event != null) {
            stateMachine.sendEvent(event);
        }
        model.addAttribute("states", stateMachine.getState().getIds());
        model.addAttribute("stateChartModel", stateChartModel);
        return "states";
    }

}
