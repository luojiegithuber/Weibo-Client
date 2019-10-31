package com.example.wechatlogin;

/**
 * Created by DELL on 2019/10/31.
 */

public class Comment {
    private String name;
    private String say;

    public Comment(String name,String say){
        this.name = name;
        this.say = say;
    }

    public String getName() {
        return name;
    }
    public String getSay() {
        return say;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSay(String say) {
        this.say = say;
    }
}
