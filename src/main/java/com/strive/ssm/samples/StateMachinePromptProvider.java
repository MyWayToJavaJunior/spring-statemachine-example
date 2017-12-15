package com.strive.ssm.samples;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.support.DefaultPromptProvider;
import org.springframework.stereotype.Component;

/**
 * @ClassName: StateMachinePromptProvider
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月17日 17:31
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class StateMachinePromptProvider extends DefaultPromptProvider {

    @Override
    public String getPrompt() {
        return "sm>";
    }


    @Override
    public String getProviderName() {
        return "State machine prompt provider";
    }

}
