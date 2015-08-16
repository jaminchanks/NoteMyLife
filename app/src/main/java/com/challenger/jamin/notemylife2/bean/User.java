package com.challenger.jamin.notemylife2.bean;

import com.challenger.jamin.notemylife2.Base.DataVar;

import java.io.Serializable;

/**
 * Created by jamin on 7/29/15.
 */
public class User implements Serializable{
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getHead() {
        if (head == null) {
            this.head = DataVar.INIT_HEAD_IMAGE_FILE_NAME;
        }
        return head;
    }

    public void setHead(String head) {
        if (head == null ) {
            head = DataVar.INIT_HEAD_IMAGE_FILE_NAME;
        }
        this.head = head;
    }


    public User(String email, String nickName, String password) {
        this.email = email;
        this.nickName = nickName;
        this.password = password;
    }

    public User(int userId, String email, String nickName, String password) {
        this.userId = userId;
        this.email = email;
        this.nickName = nickName;
        this.password = password;
    }

    public User() {}



    private int userId;
    private String email;
    private String nickName;
    private String password;
    private String head;
}
