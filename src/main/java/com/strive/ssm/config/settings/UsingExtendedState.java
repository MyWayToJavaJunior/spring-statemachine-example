package com.strive.ssm.config.settings;

import org.springframework.context.ApplicationListener;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.event.OnExtendedStateChanged;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;

/**
 * @ClassName: UsingExtendedState
 * @Description: 假设我们需要创建一个状态机，跟踪用户在键盘上按下一个键的次数，然后在按键被按下1000次时终止。
 * <p>
 * 这就是扩展状态变量通过不需要添加更多的状态来驱动状态机的变化，而可以在转换过程中进行简单的变量更改。
 * <p>
 * StateMachine有一个getExtendedState()方法用来返回ExtendedState接口，通过这个接口可以访问扩展状态变量。
 * <p>
 * 我们可以用两种方式来获得扩展状态变量更改的通知：
 * ①使用StateMachineListener监听器并且监听extendedStateChanged(key, value)方法的回调
 * ②实现ApplicationListener监听器
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月04日 12:34
 */
public class UsingExtendedState {

    public Action<String, String> myVariableAction() {
        return context -> context.getExtendedState().getVariables().put("mykey", "myvalue");
    }

    public class ExtendedStateVariableListener extends StateMachineListenerAdapter<String, String> {

        @Override
        public void extendedStateChanged(Object key, Object value) {
            // do something with changed variable
        }
    }

    public class ExtendedStateVariableEventListener implements ApplicationListener<OnExtendedStateChanged> {

        @Override
        public void onApplicationEvent(OnExtendedStateChanged event) {
            // do something with changed variable
        }
    }
}
