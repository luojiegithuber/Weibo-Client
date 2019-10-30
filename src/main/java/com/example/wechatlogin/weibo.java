package com.example.wechatlogin;


public class weibo {
    private int wHead;
    private String wName;
    private String wTime;
    private String wSpeak;
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
