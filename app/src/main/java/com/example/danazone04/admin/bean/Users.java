package com.example.danazone04.admin.bean;

import java.io.Serializable;

public class Users implements Serializable {
    private int id;
    private String username;
    private String phone;
    private String avatar;
    private String email;
    private String birthday;
    private String sex;
    private String bike;
    private String province;

    public Users(){

    }

    public Users(int id, String username, String phone, String avatar, String email, String birthday, String sex, String bike, String province) {
        this.id = id;
        this.username = username;
        this.phone = phone;
        this.avatar = avatar;
        this.email = email;
        this.birthday = birthday;
        this.sex = sex;
        this.bike = bike;
        this.province = province;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBike() {
        return bike;
    }

    public void setBike(String bike) {
        this.bike = bike;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
