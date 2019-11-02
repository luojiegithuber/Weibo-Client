package com.example.wechatlogin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.wechatlogin.util.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

public class CommentActivity extends AppCompatActivity {

    private String username;
    private String time;

    private ArrayList<Comment> cData,new_cData;
    private Context cContext;
    private CommentAdapter cAdapter = null;
    private ListView list_comment;
    private int weiboID=new Integer(0);
    private Button btn ;
    private EditText edit_Comment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /////关闭这个活动记得要清空销毁！！！！！


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        Intent it =getIntent();
        Bundle bd =it.getExtras();
        weiboID = bd.getInt("weiboID");
        username = bd.getString("username");


        cData = new ArrayList<Comment>();
        new_cData = new ArrayList<Comment>();
        cContext = CommentActivity.this;
        list_comment = (ListView) findViewById(R.id.list_comment);
        // cData.add(new Comment("Song Zhihao","wo shi zhen de niu bi"));
        cAdapter = new CommentAdapter(cData,cContext);
        list_comment.setAdapter(cAdapter);


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

        btn=(Button)findViewById(R.id.button_comment);//获取【发表】按钮
        edit_Comment=(EditText)findViewById(R.id.edit_comment);//获取【评论】区域

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                           sendComment(username,edit_Comment.getText().toString(),Integer.toString((int)weiboID));
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }).start();


            }
        });//****END


    }//oncreat



    public void sendComment(String name,String say,String weiboID){

        HttpUtil.sendOkHttpRequestPostComment_Publish(name,say,weiboID,"http://10.0.2.2/sendComment.php",new okhttp3.Callback(){
            @Override
            public void onResponse(Call call, Response response)throws IOException{
                //得到服务器返回内容
                String responseData = response.body().string();
                Log.e("log_tag", responseData);
            }
            @Override
            public void onFailure(Call call,IOException e){
                //对异常情况处理
            }
        });

    }

    private void sendWeiboID( int weiboId){

        HttpUtil.sendOkHttpRequestPostComment(weiboId,"http://10.0.2.2/getComment.php",new okhttp3.Callback(){
            @Override
            public void onResponse(Call call, Response response)throws IOException {
                //得到服务器返回内容
                String responseData = response.body().string();
                Log.e("评论服务器返回数据", responseData);


                //开始处理数据
                parseJSONwithGSON(responseData);
                cData.addAll(new_cData);

            }
            @Override
            public void onFailure(Call call,IOException e){
                //对异常情况处理
            }
        });
    }//sendWeiboID




    private void parseJSONwithGSON(String jsonData){
        Gson gson = new Gson();
        new_cData = gson.fromJson(jsonData, new TypeToken<ArrayList<Comment>>(){
        }.getType());//这样一来，新数据变成的多个新对象都在这里面了诶嘿。
    }//parseJSONwithGSON

}
