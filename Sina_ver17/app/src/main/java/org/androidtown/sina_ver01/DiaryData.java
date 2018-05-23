package org.androidtown.sina_ver01;

public class DiaryData {
    private String id;
    private String text;
    private String date;
    private String title;
    private String subtitle;
    private int emotion;


    public DiaryData() {
    }

    public DiaryData(String title, String subtitle, String date, String text, int emotion) {
        this.title = title;
        this.subtitle = subtitle;
        this.date = date;
        this.text = text;
        this.emotion = emotion;
    }

    public int getEmotion() {
        return emotion;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setEmotion(int emotion) { this.emotion = emotion; }
}
