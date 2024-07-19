package com.example.ltdd2_crud_nguoithue.Models;

import java.io.Serializable;

public class User implements Serializable {
    private String id, image, name, soDienThoai, password, conFirm;

    public User() {
    }

    public User(String id, String image, String name, String soDienThoai, String password, String conFirm) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.soDienThoai = soDienThoai;
        this.password = password;
        this.conFirm = conFirm;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConFirm() {
        return conFirm;
    }

    public void setConFirm(String conFirm) {
        this.conFirm = conFirm;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", soDienThoai='" + soDienThoai + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
