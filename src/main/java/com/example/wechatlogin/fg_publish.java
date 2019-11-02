package com.example.wechatlogin;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wechatlogin.util.HttpUtil;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Response;

public class fg_publish extends Fragment {

    private String username;
    private String time;

    private String web_string;
    private EditText wcontent;
    private View view;
    private Button btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fg_publish,null);

        btn=(Button)view.findViewById(R.id.button_publish);//获取【发表】按钮
        wcontent=(EditText)view.findViewById(R.id.edit_content);//获取编辑的文字【框】（记得转文字）

        return view;
    }

//在fragment不能直接进行点击事件，需要放到oncreatActivity中
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);//？？？？？？？？？


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

    private void sendok( String content){

        HttpUtil.sendOkHttpRequestPostPublish(wcontent.getText().toString(),"http://10.0.2.2/publish.php",new okhttp3.Callback(){
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


}
