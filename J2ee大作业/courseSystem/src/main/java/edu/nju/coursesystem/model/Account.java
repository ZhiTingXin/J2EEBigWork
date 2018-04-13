package edu.nju.coursesystem.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "account")
public class Account {
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Id
    private String username;
    //账户余额
    private double account;
    //登录密码
    private String password;
    //支付密码
    private String payword;

    public double getAccount() {
        return account;
    }

    public void setAccount(double account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPayword() {
        return payword;
    }

    public void setPayword(String payword) {
        this.payword = payword;
    }

}
