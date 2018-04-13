package edu.nju.coursesystem.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "ordertable")
public class Order {
    @Id
    //订单编号
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer orderid;
    //用户ID
    private String userid;

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

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    //订单金额
    private Integer classId;

    public String getOrderState() {
        return orderState;
    }

    private  Integer total;
    //订单时间
    private Timestamp orderDate;
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
    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer  orderid) {
        this.orderid = orderid;
    }

    public String   getUserid() {
        return userid;
    }

    public void setUserid(String  userid) {
        this.userid = userid;
    }

    public Integer getClassNum() {
        return classNum;
    }

    public void setClassNum(Integer classNum) {
        this.classNum = classNum;
    }


    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

}
