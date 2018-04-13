package edu.nju.coursesystem.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "student")
public class Student{
    @Id
    private String mail;
    //用户的消费金额
    private Integer cost;

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    //积分
    private Integer ingration;
    //用户的状态，是否激活
    private boolean userState;
    private String confirm;

    //会员级别
    private Integer userLevel;
    //会员状态,是否取消
    private boolean vipState;
    //用户名
    private String userName;
    //联系方式
    private String phone;

    //密码
    private String password;

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public boolean isVipState() {
        return vipState;
    }

    public void setVipState(boolean vipState) {
        this.vipState = vipState;
    }
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Integer getIngration() {
        return ingration;
    }

    public void setIngration(Integer ingration) {
        this.ingration = ingration;
    }

    public boolean isUserState() {
        return userState;
    }

    public void setUserState(boolean userState) {
        this.userState = userState;
    }

    public Integer getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
