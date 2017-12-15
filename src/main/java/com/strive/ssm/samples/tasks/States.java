package com.strive.ssm.samples.tasks;

/**
 * @ClassName: States
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月10日 15:05
 */
public enum States {

    READY,
    FORK, JOIN, CHOICE,
    TASKS, T1, T1E, T2, T2E, T3, T3E,
    ERROR, AUTOMATIC, MANUAL
}
