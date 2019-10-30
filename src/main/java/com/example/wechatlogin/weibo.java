package com.example.wechatlogin;


public class weibo {
    private long id;
    private int wHead=1;
    private String wName;
    private String wTime;
    private String wSpeak;
    private int forward=0,comment=0,like=0;

    public weibo() {
    }

   /* public weibo(int wHead,String wName) {
        this.wHead = wHead;
        this.wName = wName;
        this.wTime=  "88888";
        this.wSpeak = "8888";
    }*/

    public weibo(int wHead,String wName, String wTime, String wSpeak) {
        this.wHead = wHead;
        this.wName = wName;
        this.wTime=  wTime;
        this.wSpeak = wSpeak;
    }

    //*************
    public long getId() {
        return id;
    }

    public int getwHead() {
        return wHead;
    }

    public String getwName() {
        return wName;
    }

    public String getwTime() {
        return wTime;
    }

    public String getwSpeak() {
        return wSpeak;
    }
//********************

    public void setwIcon(int aIcon) {
        this.wHead = aIcon;
    }

    public void setwName(String aName) {
        this.wName = aName;
    }

    public void setwTime(String wTime) {
        this.wName = wTime;
    }

    public void setwSpeak(String aSpeak) {
        this.wSpeak = aSpeak;
    }

//******************

}
