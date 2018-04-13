package edu.nju.coursesystem.VO;

import edu.nju.coursesystem.model.Order;

import java.text.SimpleDateFormat;


public class OrderVO {
    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    private Integer orderid;

    public long getMillis() {
        return millis;
    }

    public void setMillis(long millis) {
        this.millis = millis;
    }

    private long millis;

    public OrderVO (Order order){
        this.orderid = order.getOrderid();
        this.courseName=order.getCourseName();
        this.classNum = order.getClassNum();
        this.orderClass = order.getOrderClass();
        this.orderState = order.getOrderState();
        this.total = order.getTotal();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        this.orderDate = dateFormat.format(order.getOrderDate());
        this.millis = order.getOrderDate().getTime();
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    //课程名称
    private String courseName;

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }
    //选课人数
    private Integer classNum;
    //订单状态，退订，预定,未支付
    private String orderState;
    //订单金额

    public String getOrderState() {
        return orderState;
    }

    private  Integer total;
    //订单时间
    private String orderDate;
    //订单种类,是否选班
    private String orderClass;

    public String getOrderClass() {
        return orderClass;
    }

    public void setOrderClass(String orderClass) {
        this.orderClass = orderClass;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getClassNum() {
        return classNum;
    }

    public void setClassNum(Integer classNum) {
        this.classNum = classNum;
    }


    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

}
