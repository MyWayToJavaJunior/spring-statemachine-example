package com.strive.ssm.samples.deploy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: StateMachineController
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月10日 18:00
 */
@Controller
public class StateMachineController {

    private final static String[] EVENTS = new String[]{"DEPLOY", "UNDEPLOY"};
    private final static String[] FAILS = new String[]{"isInstalled", "installedOk", "isRunning", "hasError"};

    @Autowired
    private StateMachine<String, String> stateMachine;

    @Autowired
    private StateMachineLogListener listener;

    @RequestMapping("/")
    public String home() {
        return "redirect:/state";
    }

    @RequestMapping("/state")
    public String feedAndGetState(@RequestParam(value = "event", required = false) String event,
                                  @RequestParam(value = "fail", required = false) Collection<String> fails, Model model) throws Exception {
        model.addAttribute("allTypes", EVENTS);
        model.addAttribute("failTypes", FAILS);

        if (StringUtils.hasText(event)) {
            Map<String, Object> headers = new HashMap<>();
            if (fails != null) {
                for (String fail : fails) {
                    headers.put(fail, true);
                }
            }
            listener.resetMessages();
            stateMachine.sendEvent(MessageBuilder.createMessage(event, new MessageHeaders(headers)));
        }
        model.addAttribute("states", stateMachine.getState().getIds());
        model.addAttribute("messages", listener.getMessages());
        return "states";
    }
}