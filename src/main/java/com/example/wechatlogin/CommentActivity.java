package com.example.wechatlogin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.example.wechatlogin.util.HttpUtil;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

public class CommentActivity extends AppCompatActivity {
    private ArrayList<Comment> cData = null;
    private Context cContext;
    private CommentAdapter cAdapter = null;
    private ListView list_comment;
    private int weiboID=new Integer(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /////关闭这个活动记得要清空销毁！！！！！


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        Intent it =getIntent();
        Bundle bd =it.getExtras();
        weiboID = bd.getInt("weiboID");

        //主线程


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sendWeiboID(weiboID);//发送这个微博id，然后获取对应表的评论
                }catch (Exception e){
                     //处理错误
                }
            }
        }).start();


        cContext = CommentActivity.this;
        list_comment = (ListView) findViewById(R.id.list_comment);


        cData = new ArrayList<Comment>();
        cData.add(new Comment("Song Zhihao","wo shi zhen de niu bi"));

        cAdapter = new CommentAdapter(cData,cContext);
        list_comment.setAdapter(cAdapter);

    }//oncreat



    private void sendWeiboID( int weiboId){

        HttpUtil.sendOkHttpRequestPostComment(weiboId,"http://10.0.2.2/getComment.php",new okhttp3.Callback(){
            @Override
            public void onResponse(Call call, Response response)throws IOException {
                //得到服务器返回内容
                String responseData = response.body().string();
                Log.e("评论服务器返回数据", responseData);

                //开始处理数据
            }
            @Override
            public void onFailure(Call call,IOException e){
                //对异常情况处理
            }
        });
    }//sendWeiboID

}
