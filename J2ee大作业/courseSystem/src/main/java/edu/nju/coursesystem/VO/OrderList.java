package edu.nju.coursesystem.VO;

import edu.nju.coursesystem.model.Order;

import java.util.List;

public class OrderList {
    private List<OrderVO> orders;

    public List<OrderVO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderVO> orders) {
        this.orders = orders;
    }
}
