package com.challenger.jamin.notemylife2.bean;


public class Weather {

    public Weather(){
    }

    public Weather(String city, String date, String weatherType, String temp, String l_temp, String h_temp) {
        this.city = city;
        this.date = date;
        this.weatherType = weatherType;
        this.temp = temp;
        this.l_temp = l_temp;
        this.h_temp = h_temp;
    }

    public String getCity() {

        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeatherType() {
        return weatherType;
    }

    public void setWeatherType(String weatherType) {
        this.weatherType = weatherType;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getL_temp() {
        return l_temp;
    }

    public void setL_temp(String l_temp) {
        this.l_temp = l_temp;
    }

    public String getH_temp() {
        return h_temp;
    }

    public void setH_temp(String h_temp) {
        this.h_temp = h_temp;
    }

    private String city;
    private String date;
    private String weatherType;
    private String temp;
    private String l_temp;
    private String h_temp;


}
