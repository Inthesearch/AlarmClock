package com.example.goku.alarmclock;

import java.io.Serializable;

/**
 * Created by Goku on 10/06/2017.
 */

public class ParentBean implements Serializable{

    int request,hour, minute, date, month, year, id;
    boolean status;
    long time;

    public ParentBean(int hour, int minute, boolean status) {
        this.hour = hour;
        this.minute = minute;
        this.date = date;
        this.month = month;
        this.year = year;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRequest() {
        return request;
    }

    public void setRequest(int request) {
        this.request = request;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ParentBean{" +
                "hour=" + hour +
                ", minute=" + minute +
                ", date=" + date +
                ", month=" + month +
                ", year=" + year +
                ", status=" + status +
                '}';
    }
}
