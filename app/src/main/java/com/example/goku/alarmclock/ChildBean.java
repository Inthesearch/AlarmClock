package com.example.goku.alarmclock;

import java.io.Serializable;

/**
 * Created by Goku on 10/06/2017.
 */

public class ChildBean implements Serializable{
    int id;

    String song;
    Boolean vibrate;

    public ChildBean(String song, Boolean vibrate) {
        this.song = song;
        this.vibrate = vibrate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public Boolean getVibrate() {
        return vibrate;
    }

    public void setVibrate(Boolean vibrate) {
        this.vibrate = vibrate;
    }

    @Override
    public String toString() {
        return "ChildBean{" +
                "song='" + song + '\'' +
                ", vibrate=" + vibrate +
                '}';
    }


}
