package com.strive.ssm.samples.eventservice;

/**
 * @ClassName: Pageview
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月14日 17:16
 */
public class Pageview {

    private String user;
    private Events id;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Events getId() {
        return id;
    }

    public void setId(Events id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Pageview [user=" + user + ", id=" + id + "]";
    }

}
