package com.strive.ssm.samples.monitoring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @ClassName: StateMachineController
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月10日 18:00
 */
@Controller
public class StateMachineController {

    @Autowired
    private StateMachine<String, String> stateMachine;

    @RequestMapping("/")
    public String home() {
        return "redirect:/state";
    }

    @RequestMapping("/state")
    public String feedAndGetStates(@RequestParam(value = "events", required = false) List<String> events, Model model) throws Exception {
        StateMachineLogListener listener = new StateMachineLogListener();
        stateMachine.addStateListener(listener);
        stateMachine.start();
        if (events != null) {
            for (String event : events) {
                stateMachine.sendEvent(event);
            }
        }
        stateMachine.stop();
        model.addAttribute("allEvents", new String[]{"E1", "E2"});
        model.addAttribute("messages", createMessages(listener.getMessages()));
        return "states";
    }

    private String createMessages(List<String> messages) {
        StringBuilder buf = new StringBuilder();
        for (String message : messages) {
            buf.append(message);
            buf.append("\n");
        }
        return buf.toString();
    }

}