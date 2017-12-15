package com.strive.ssm.samples.eventservice;

import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.target.CommonsPool2TargetSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.persist.RepositoryStateMachinePersist;
import org.springframework.statemachine.redis.RedisStateMachineContextRepository;
import org.springframework.statemachine.redis.RedisStateMachinePersister;

import java.io.IOException;
import java.io.InputStream;
import java.util.EnumSet;
import java.util.Scanner;

/**
 * @ClassName: StateMachineConfig
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月10日 17:27
 */
@Configuration
public class StateMachineConfig {

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ProxyFactoryBean stateMachine() {
        ProxyFactoryBean pfb = new ProxyFactoryBean();
        pfb.setTargetSource(this.poolTargetSource());
        return pfb;
    }

    @Bean
    public CommonsPool2TargetSource poolTargetSource() {
        CommonsPool2TargetSource pool = new CommonsPool2TargetSource();
        pool.setMaxSize(3);
        pool.setTargetBeanName("stateMachineTarget");
        return pool;
    }

    @Bean(name = "stateMachineTarget")
    @Scope(scopeName = "prototype")
    public StateMachine<States, Events> stateMachineTarget() throws Exception {
        StateMachineBuilder.Builder<States, Events> builder = StateMachineBuilder.builder();

        builder.configureConfiguration()
                .withConfiguration()
                .autoStartup(true);

        builder.configureStates()
                .withStates()
                .initial(States.HOME)
                .states(EnumSet.allOf(States.class));

        builder.configureTransitions()
                .withInternal()
                .source(States.ITEMS).event(Events.ADD)
                .action(this.addAction())
                .and()
                .withInternal()
                .source(States.CART).event(Events.DEL)
                .action(this.delAction())
                .and()
                .withInternal()
                .source(States.PAYMENT).event(Events.PAY)
                .action(this.payAction())
                .and()
                .withExternal()
                .source(States.HOME).target(States.ITEMS)
                .action(this.pageviewAction())
                .event(Events.VIEW_I)
                .and()
                .withExternal()
                .source(States.CART).target(States.ITEMS)
                .action(this.pageviewAction())
                .event(Events.VIEW_I)
                .and()
                .withExternal()
                .source(States.ITEMS).target(States.CART)
                .action(this.pageviewAction())
                .event(Events.VIEW_C)
                .and()
                .withExternal()
                .source(States.PAYMENT).target(States.CART)
                .action(this.pageviewAction())
                .event(Events.VIEW_C)
                .and()
                .withExternal()
                .source(States.CART).target(States.PAYMENT)
                .action(this.pageviewAction())
                .event(Events.VIEW_P)
                .and()
                .withExternal()
                .source(States.ITEMS).target(States.HOME)
                .action(this.resetAction())
                .event(Events.RESET)
                .and()
                .withExternal()
                .source(States.CART).target(States.HOME)
                .action(this.resetAction())
                .event(Events.RESET)
                .and()
                .withExternal()
                .source(States.PAYMENT).target(States.HOME)
                .action(this.resetAction())
                .event(Events.RESET);

        return builder.build();
    }

    @Bean
    public Action<States, Events> pageviewAction() {
        return context -> {
            String variable = context.getTarget().getId().toString();
            Integer count = context.getExtendedState().get(variable, Integer.class);
            if (count == null) {
                context.getExtendedState().getVariables().put(variable, 1);
            } else {
                context.getExtendedState().getVariables().put(variable, (count + 1));
            }
        };
    }

    @Bean
    public Action<States, Events> addAction() {
        return context -> {
            Integer count = context.getExtendedState().get("COUNT", Integer.class);
            if (count == null) {
                context.getExtendedState().getVariables().put("COUNT", 1);
            } else {
                context.getExtendedState().getVariables().put("COUNT", (count + 1));
            }
        };
    }

    @Bean
    public Action<States, Events> delAction() {
        return context -> {
            Integer count = context.getExtendedState().get("COUNT", Integer.class);
            if (count != null && count > 0) {
                context.getExtendedState().getVariables().put("COUNT", (count - 1));
            }
        };
    }

    @Bean
    public Action<States, Events> payAction() {
        return context -> context.getExtendedState().getVariables().put("PAYED", true);
    }

    @Bean
    public Action<States, Events> resetAction() {
        return context -> context.getExtendedState().getVariables().clear();
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public StateMachinePersist<States, Events, String> stateMachinePersist(RedisConnectionFactory connectionFactory) {
        RedisStateMachineContextRepository<States, Events> repository = new RedisStateMachineContextRepository<>(connectionFactory);
        return new RepositoryStateMachinePersist<>(repository);
    }

    @Bean
    public RedisStateMachinePersister<States, Events> redisStateMachinePersister(StateMachinePersist<States, Events, String> stateMachinePersist) {
        return new RedisStateMachinePersister<>(stateMachinePersist);
    }

    @Bean
    public String stateChartModel() throws IOException {
        ClassPathResource model = new ClassPathResource("statechartmodel.txt");
        InputStream inputStream = model.getInputStream();
        Scanner scanner = new Scanner(inputStream);
        String content = scanner.useDelimiter("\\Z").next();
        scanner.close();
        return content;
    }
}