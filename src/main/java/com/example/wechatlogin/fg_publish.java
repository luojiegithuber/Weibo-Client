package com.example.wechatlogin;

import android.app.Fragment;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.wechatlogin.util.HttpUtil;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;

public class fg_publish extends Fragment {

    private String username;
    private String time;

    private String web_string;
    private EditText wcontent;
    private View view;
    private Button btn;

    private ImageView select_img;

    private Uri imageUri;
    private TextView pictureUse;//图片选择

    SimpleDateFormat sd;


    private static final int REQUEST_CODE_CHOOSE = 23;//定义请求码常量


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fg_publish,null);

        btn=(Button)view.findViewById(R.id.button_publish);//获取【发表】按钮
        wcontent=(EditText)view.findViewById(R.id.edit_content);//获取编辑的文字【框】（记得转文字）
        pictureUse=(TextView)view.findViewById(R.id.pictureView) ;//获取【图片选择】文字框
        select_img =(ImageView) view.findViewById(R.id.select_img) ;//获取【图片】

        Bundle bd =this.getArguments();
        username = bd.getString("username");

        sd=new SimpleDateFormat("yyyy-MM-dd HH:mm E");


        int resourceId = R.mipmap.ic_launcher;
        Glide.with(this).load(resourceId).into(select_img);
        return view;
    }

//在fragment不能直接进行点击事件，需要放到oncreatActivity中
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);//？？？？？？？？？

       pictureUse.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Log.e("【点击选择】", "点击了选择图片");
               Matisse
                       .from(getActivity())
                       .choose(MimeType.ofImage())//照片视频全部显示
                       .countable(true)//有序选择图片
                       .maxSelectable(9)//最大选择数量为9
                       .gridExpectedSize(120)//图片显示表格的大小getResources()
                       .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)//图像选择和预览活动所需的方向。
                       .thumbnailScale(0.85f)//缩放比例
                       .theme(R.style.Matisse_Zhihu)//主题  暗色主题 R.style.Matisse_Dracula
                       .imageEngine(new GlideEngine())//加载方式
                       .forResult(REQUEST_CODE_CHOOSE);//请求码
           }});




       btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),wcontent.getText().toString(), Toast.LENGTH_LONG).show();//可行

                //网络部分，BOSS战
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                           // int result = publish();
                            int result =1;
                                    sendok(wcontent.getText().toString());
                            //login()为向php服务器提交请求的函数，返回数据类型为int
                            if (result == 1) {
                                Log.e("log_tag", "成功！");
                                Looper.prepare();
                                Looper.loop();
                            } else if (result == -1) {
                                Log.e("log_tag", "错误！");
                                //Toast toast=null;
                                Looper.prepare();
                                Looper.loop();
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }).start();

                //****END

            }
        });//setOnClickListener*/
    }//oncreat

    private void sendok(String content){
        Date curDate =  new Date(System.currentTimeMillis());
        time   =   sd.format(curDate);
        HttpUtil.sendOkHttpRequestPostPublish(username,time,wcontent.getText().toString(),"http://10.0.2.2/publish.php",new okhttp3.Callback(){
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


    private int publish()throws Exception{
        int returnResult=0;

        String content=wcontent.getText().toString();//获得要推送的文字

        //判空
        if(content==null||content.length()<=0){
            Looper.prepare();
            Toast.makeText(getActivity(),"请输入要发送的内容", Toast.LENGTH_LONG).show();
            Looper.loop();
            return -1;
        }

        String urlstr="http://10.0.2.2/publish.php";//服务器地址

        URL url = new URL(urlstr);
        HttpURLConnection http= (HttpURLConnection) url.openConnection();

        String params="content="+content;
        http.setDoOutput(true);
        http.setRequestMethod("POST");
        OutputStream out=http.getOutputStream();
        out.write(params.getBytes());
        out.flush();
        out.close();
        Log.v("log_tag",params);
        //读取网页返回的数据
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(http.getInputStream()));//获得输入流
        String line="";
        StringBuilder sb=new StringBuilder();//建立输入缓冲区
        while (null!=(line=bufferedReader.readLine())){//结束会读入一个null值
            sb.append(line);//写缓冲区
        }
        String result= sb.toString();//返回结果
        Log.v("log_tag",result);
        try {
        /*获取服务器返回的JSON数据*/
            JSONObject jsonObject= new JSONObject(result);
            returnResult=jsonObject.getInt("content");//获取JSON数据中status字段值
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("log_tag", "the Error parsing data "+e.toString());
        }
        return returnResult;

    }
    List<Uri> mSelected;
    @Override      //接收返回的地址
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            Log.d("Matisse", "mSelected: " + mSelected);
        }
    }

}
