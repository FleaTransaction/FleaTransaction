package cn.fleatransaction.shiro;


import lombok.Data;

import java.io.Serializable;

@Data
public class AccountProfile implements Serializable {
    private Integer userId;

    private String userName;

    private String userEmail;

    private String userPhone;

    private String userAvator;

    private float userCredit;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserAvator() {
        return userAvator;
    }

    public void setUserAvator(String userAvator) {
        this.userAvator = userAvator;
    }

    public float getUserCredit() {
        return userCredit;
    }

    public void setUserCredit(float userCredit) {
        this.userCredit = userCredit;
    }



}
