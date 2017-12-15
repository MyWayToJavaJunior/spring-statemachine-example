package com.strive.ssm.config.scope;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName: StateMachineController
 * @Description: 一旦将状态机的范围标记为session级别的话，那么在每个被@Controller标记的controller中自动注入StateMachine时，每个session
 * 都会得到新的状态机实例。当HttpSession失效时，状态机也会被销毁
 * <p>
 * 在session范围内使用状态机时需要仔细规划，主要是因为它是一个相对重量级的组件
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月04日 11:48
 */
@Controller
public class StateMachineController {

    @Autowired
    StateMachine<String, String> stateMachine;

    @RequestMapping(name = "/state", method = RequestMethod.POST)
    public HttpEntity<Void> setState(@RequestParam("event") String event) {
        stateMachine.sendEvent(event);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @RequestMapping(name = "/state", method = RequestMethod.GET)
    @ResponseBody
    public String getState() {
        return stateMachine.getState().getId();
    }
}
