package org.androidtown.sina_ver01;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class DiaryData implements Parcelable,Comparable<DiaryData>{
    private String id;
    private String text;
    private String date;
    private String title;
    private String subtitle;
    private int emotion;
    private int type;
    private String pic;
    private String weather;
    private String draw;


    public DiaryData() {
    }

    public DiaryData(String title, String subtitle, String date, String weather, String text, int emotion,int type) {
        this.title = title;
        this.subtitle = subtitle;
        this.date = date;
        this.weather = weather;
        this.text = text;
        this.emotion = emotion;
        this.type=type;
    }

    public DiaryData(String subtitle, int emotion, String date, String weather, String text, String pic, int type)
    {
        this.subtitle = subtitle;
        this.emotion = emotion;
        this.date = date;
        this.weather = weather;
        this.text = text;
        this.pic = pic;
        this.type = type;
    }

    public DiaryData(String subtitle, int emotion, String date, String weather, String text, int type, String draw)
    {
        this.subtitle = subtitle;
        this.emotion = emotion;
        this.date = date;
        this.weather = weather;
        this.text = text;
        this.type = type;
        this.draw = draw;
    }

    protected DiaryData(Parcel in) {
        id = in.readString();
        text = in.readString();
        date = in.readString();
        weather = in.readString();
        title = in.readString();
        subtitle = in.readString();
        emotion = in.readInt();
        type = in.readInt();
        pic = in.readString();
        draw = in.readString();
    }

    public static final Creator<DiaryData> CREATOR = new Creator<DiaryData>() {
        @Override
        public DiaryData createFromParcel(Parcel in) {
            return new DiaryData(in);
        }

        @Override
        public DiaryData[] newArray(int size) {
            return new DiaryData[size];
        }
    };

    public int getEmotion() {
        return emotion;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getWeather(){return weather;}

    public String getSubtitle() {
        return subtitle;
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

    public String getPic() { return pic; }

    public int getType(){return type;}

    public String getDraw(){return draw;}

    public void setId(String id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setWeather(String weather){this.weather = weather;}

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

    public void setPic(String pic) {this.pic = pic; }

    public void setType(int type){this.type = type;}

    public void setDraw(String draw){this.draw = draw;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(text);
        dest.writeString(date);
        dest.writeString(weather);
        dest.writeString(title);
        dest.writeString(subtitle);
        dest.writeInt(emotion);
        dest.writeInt(type);
        dest.writeString(pic);
        dest.writeString(draw);
    }

    @Override
    public int compareTo(@NonNull DiaryData o) {
        return o.getDate().compareTo(this.getDate());
    }
}
