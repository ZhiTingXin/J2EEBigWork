package edu.nju.coursesystem.VO;

import java.util.List;

public class OrdersIns {
    //对于机构的预订订单和退订的订单信息
    private List<OrderIns> orders;

    public List<OrderIns> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderIns> orders) {
        this.orders = orders;
    }
}
