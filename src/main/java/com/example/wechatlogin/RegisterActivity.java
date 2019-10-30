package com.example.wechatlogin;

import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterActivity extends AppCompatActivity {

    private EditText edit_Username;
    private EditText edit_Password;
    private Button btn_register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edit_Username = (EditText) findViewById(R.id.edit_username);     //获取用于输入用户名的编辑框组件
        edit_Password = (EditText) findViewById(R.id.edit_password);     //获取用于输入密码的编辑框组件
        btn_register =(Button) findViewById(R.id.bt_register);      //注册按钮

        //注册事件
        btn_register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            int result = register();
                            //register()为向php服务器提交请求的函数，返回数据类型为int
                            if (result == 1) {
                                Log.e("log_tag", "注册成功！");
                                Looper.prepare();
                                Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            } else {
                                Log.e("log_tag", "注册失败！");
                                Looper.prepare();
                                Toast.makeText(RegisterActivity.this, "注册失败！", Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }).start();
            }
        });
    }

    private int register()throws Exception{
        int returnResult=0;
    /*获取用户名和密码*/
        String user_id=edit_Username.getText().toString();
        String input_pwd=edit_Password.getText().toString();

        if(user_id==null||user_id.length()<=0){
            Looper.prepare();
            Toast.makeText(RegisterActivity.this,"请输入账号", Toast.LENGTH_LONG).show();
            Looper.loop();
            return 0;

        }
        if(input_pwd==null||input_pwd.length()<=0){
            Looper.prepare();
            Toast.makeText(RegisterActivity.this,"请输入密码", Toast.LENGTH_LONG).show();
            Looper.loop();
            return 0;
        }
        String urlstr="http://10.0.2.2/register.php";

        URL url = new URL(urlstr);
        HttpURLConnection http= (HttpURLConnection) url.openConnection();

        String params="username="+user_id+'&'+"password="+input_pwd;
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
            returnResult=jsonObject.getInt("status");//获取JSON数据中status字段值
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("log_tag", "the Error parsing data "+e.toString());
        }
        return 1;
    };
}
