package com.example.wechatlogin.util;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by DELL on 2019/10/28.
 */

public class HttpUtil {
    public static void sendOkHttpRequest(String address, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }

    public static void sendOkHttpRequestPostPublish(String content,String address, okhttp3.Callback callback){

        RequestBody requestBody = new FormBody.Builder()
                .add("username","yaojingxian")
                .add("time","123456")
                .add("content",content)
                .build();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }


    public static void sendOkHttpRequestPostComment(int weiboId, String address, okhttp3.Callback callback){

        RequestBody requestBody = new FormBody.Builder()
                .add("weiboID",Integer.toString(weiboId))
                .build();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public static void sendOkHttpRequestPostComment_Publish(String name,String say, String weiboID,String address, okhttp3.Callback callback){

        RequestBody requestBody = new FormBody.Builder()
                .add("name",name)
                .add("say",say)
                .add("weiboID",weiboID)
                .build();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }

}
