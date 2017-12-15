package com.strive.ssm.samples.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

/**
 * @ClassName: TasksCommands
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月10日 15:07
 */
@Component
public class TasksCommands implements CommandMarker {

    @Autowired
    private Tasks tasks;

    @CliCommand(value = "tasks run", help = "Run tasks")
    public void run() {
        tasks.run();
    }

    @CliCommand(value = "tasks list", help = "List tasks")
    public String list() {
        return tasks.toString();
    }

    @CliCommand(value = "tasks fix", help = "Fix tasks")
    public void fix() {
        tasks.fix();
    }

    @CliCommand(value = "tasks fail", help = "Fail task")
    public void fail(@CliOption(key = {"", "task"}, help = "Task id") String task) {
        tasks.fail(task);
    }

}
