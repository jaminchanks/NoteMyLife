package com.challenger.jamin.notemylife2.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Created by jamin on 7/29/15.
 */
public class Diary implements Serializable{

    public int getDiaryId() {
        return diaryId;
    }

    public void setDiaryId(int diaryId) {
        this.diaryId = diaryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTimeStr = simpleDateFormat.format(createTime);
        return createTimeStr;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = Timestamp.valueOf(createTime);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }


    public String getWeather() {
         if (weather == null) {
            this.weather = "未知,点击修改";
        }
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }


    private int diaryId;
    private String title;
    private String content;
    private Timestamp createTime;
    private String address;
    private int bookId;
    private String weather;

}
