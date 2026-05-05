package models.system;

import models.enums.OrderStatus;

public class Order {
    private String text;
    private OrderStatus status;
    private int id;

    public Order(int id, String text){
        this.id = id;
        this.text = text;
        status = OrderStatus.SENT;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public OrderStatus getStatus() {
        return status;
    }
    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public String toString(){
        return "Order: " + text + "(Status=" + status + ")";
    }
}
