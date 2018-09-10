package com.danazone.danazone04.admin.bean;

import java.io.Serializable;

public class DaNaPhoto implements Serializable {
    private int id;
    private String username;
    private String phone;
    private String avatar;
    private String email;
    private String birthday;
    private String sex;
    private String bussiness;
    private String province;
    private String date;

    public DaNaPhoto(){

    }

    public DaNaPhoto(int id, String username, String phone, String avatar, String email, String birthday, String sex, String bussiness, String province, String date) {
        this.id = id;
        this.username = username;
        this.phone = phone;
        this.avatar = avatar;
        this.email = email;
        this.birthday = birthday;
        this.sex = sex;
        this.bussiness = bussiness;
        this.province = province;
        this.date = date;
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

    public String getBussiness() {
        return bussiness;
    }

    public void setBussiness(String bussiness) {
        this.bussiness = bussiness;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
