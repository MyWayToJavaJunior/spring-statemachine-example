package com.strive.ssm.samples.cdplayer;

/**
 * @ClassName: States
 * @Description: BUSY作为PLAYING和PAUSED的父状态，IDLE作为CLOSED和OPEN的父状态
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月01日 18:01
 */
public enum States {

    // super state of PLAYING and PAUSED
    BUSY,
    PLAYING,
    PAUSED,
    // super state of CLOSED and OPEN
    IDLE,
    CLOSED,
    OPEN
}
