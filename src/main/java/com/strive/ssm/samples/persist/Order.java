package com.strive.ssm.samples.persist;

/**
 * @ClassName: Order
 * @Description: 说明
 * @author: tanggang@winshare-edu.com
 * @date: 2017年11月10日 15:55
 */
public class Order {

    private int id;
    private String state;

    public Order() {

    }

    public Order(int id, String state) {
        this.id = id;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Order [id=" + id + ", state=" + state + "]";
    }
}
