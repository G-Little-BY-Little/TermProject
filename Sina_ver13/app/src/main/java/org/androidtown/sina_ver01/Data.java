package org.androidtown.sina_ver01;

import java.io.Serializable;

public class Data implements Serializable {
    String text;
    String date;
    String title;
    String subtitle;
    String id;
    int emotion;

    public Data() {
    }

    public Data(String text, String date, String title, String subtitle, int emotion) {
        this.text = text;
        this.date = date;
        this.title = title;
        this.subtitle = subtitle;
        this.emotion = emotion;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public int getEmotion(){ return emotion; }

    public void setEmotion(int emotion){this.emotion = emotion;}

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
