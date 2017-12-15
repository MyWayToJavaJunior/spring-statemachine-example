package com.strive.ssm.config.task;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.recipes.tasks.TasksHandler;

/**
 * @ClassName: TaskConfig
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月10日 10:13
 */
public class TaskConfig {

    public void executeTaskOne() {
        TasksHandler handler = TasksHandler.builder()
                .task("1", this.sleepRunnable())
                .task("2", this.sleepRunnable())
                .task("3", this.sleepRunnable())
                .build();

        handler.runTasks();
    }

    /**
     * 定义任务监听器
     */
    public void executeTaskTwo() {
        MyTasksListener listener1 = new MyTasksListener();
        MyTasksListener listener2 = new MyTasksListener();

        TasksHandler handler = TasksHandler.builder()
                .task("1", this.sleepRunnable())
                .task("2", this.sleepRunnable())
                .task("3", this.sleepRunnable())
                .listener(listener1)
                .build();

        handler.addTasksListener(listener2);
        handler.removeTasksListener(listener2);

        handler.runTasks();
    }

    /**
     * 定义子任务
     */
    public void executeTaskThree() {
        TasksHandler handler = TasksHandler.builder()
                .task("1", this.sleepRunnable())
                .task("1", "12", this.sleepRunnable())
                .task("1", "13", this.sleepRunnable())
                .task("2", this.sleepRunnable())
                .task("2", "22", this.sleepRunnable())
                .task("2", "23", this.sleepRunnable())
                .task("3", this.sleepRunnable())
                .task("3", "32", this.sleepRunnable())
                .task("3", "33", this.sleepRunnable())
                .build();

        handler.runTasks();
    }

    public void executeTaskFour() {
        TasksHandler handler = TasksHandler.builder()
                .task("1", sleepRunnable())
                .task("2", sleepRunnable())
                .task("3", sleepRunnable())
                .build();

        handler.runTasks();
        // 当发生错误时，状态机会将这些运行的任务置入ERROR状态，用户可以调用fixcurrentproblems()方法重置当前任务的状态并保存在扩展状态变量中
        handler.fixCurrentProblems();
        // continueFromError()方法可以用来指示状态机将ERROR状态返回到READY状态，从而让该任务可以再次执行
        handler.continueFromError();
    }

    private class MyTasksListener extends TasksHandler.TasksListenerAdapter {

        @Override
        public void onTasksStarted() {
        }

        @Override
        public void onTasksContinue() {
        }

        @Override
        public void onTaskPreExecute(Object id) {
        }

        @Override
        public void onTaskPostExecute(Object id) {
        }

        @Override
        public void onTaskFailed(Object id, Exception exception) {
        }

        @Override
        public void onTaskSuccess(Object id) {
        }

        @Override
        public void onTasksSuccess() {
        }

        @Override
        public void onTasksError() {
        }

        @Override
        public void onTasksAutomaticFix(TasksHandler handler, StateContext<String, String> context) {
        }
    }

    private Runnable sleepRunnable() {
        return () -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
    }
}
