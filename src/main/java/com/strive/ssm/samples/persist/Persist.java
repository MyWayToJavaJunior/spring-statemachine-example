package com.strive.ssm.samples.persist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.recipes.persist.PersistStateMachineHandler;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

import java.util.List;

/**
 * @ClassName: Persist
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月10日 15:51
 */
public class Persist {

    private final PersistStateMachineHandler handler;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final PersistStateMachineHandler.PersistStateChangeListener listener = new LocalPersistStateChangeListener();

    public Persist(PersistStateMachineHandler handler) {
        this.handler = handler;
        this.handler.addPersistStateChangeListener(listener);
    }

    public String listDbEntries() {
        List<Order> orders = jdbcTemplate.query(
                "select id, state from orders",
                (rs, rowNum) -> new Order(rs.getInt("id"), rs.getString("state")));
        StringBuilder buf = new StringBuilder();
        for (Order order : orders) {
            buf.append(order);
            buf.append("\n");
        }
        return buf.toString();
    }

    public void change(int order, String event) {
        Order o = jdbcTemplate.queryForObject("select id, state from orders where id = ?", new Object[]{order},
                (rs, rowNum) -> new Order(rs.getInt("id"), rs.getString("state")));
        handler.handleEventWithState(MessageBuilder.withPayload(event).setHeader("order", order).build(), o.getState());
    }


    private class LocalPersistStateChangeListener implements PersistStateMachineHandler.PersistStateChangeListener {

        @Override
        public void onPersist(State<String, String> state, Message<String> message,
                              Transition<String, String> transition, StateMachine<String, String> stateMachine) {
            if (message != null && message.getHeaders().containsKey("order")) {
                Integer order = message.getHeaders().get("order", Integer.class);
                jdbcTemplate.update("update orders set state = ? where id = ?", state.getId(), order);
            }
        }
    }
}
