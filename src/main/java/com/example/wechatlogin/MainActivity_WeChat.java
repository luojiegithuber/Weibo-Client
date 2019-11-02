package com.example.wechatlogin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

public class MainActivity_WeChat extends AppCompatActivity {


    private EditText edit_Username;
    private EditText edit_Password;
    private Handler handler;
    private String result="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__we_chat);

        edit_Username = (EditText) findViewById(R.id.username);     //获取用于输入用户名的编辑框组件
        edit_Password = (EditText) findViewById(R.id.password);     //获取用于输入密码的编辑框组件
        Button btn_ok =(Button) findViewById(R.id.bt_ok);
        Button btn_Login = (Button) findViewById(R.id.bt_login);    //获取用于登录的按钮控件
        Button btn_zhuce = (Button) findViewById(R.id.bt_zhuce);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//登陆按钮监听事件
                //创建intent对象
                Intent it = new Intent(MainActivity_WeChat.this, HomeActivity.class);
                Bundle bd = new Bundle();
                bd.putString("username",edit_Username.getText().toString());//获得用户名字
                it.putExtras(bd);
                startActivity(it);
            }
        });

        btn_zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//登陆按钮监听事件
                //创建intent对象
                Intent it = new Intent(MainActivity_WeChat.this, RegisterActivity.class);
                startActivity(it);
            }
        });


        btn_Login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){//登陆按钮监听事件
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            int result = login();
                            //login()为向php服务器提交请求的函数，返回数据类型为int
                            if (result == 1) {
                                Log.e("log_tag", "登陆成功！");
                                //Toast toast=null;
                                //创建intent对象
                                Intent it = new Intent(MainActivity_WeChat.this,HomeActivity.class);
                                startActivity(it);
                                Looper.prepare();
                                Toast.makeText(MainActivity_WeChat.this, "登陆成功！", Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            } else if (result == -2) {
                                Log.e("log_tag", "密码错误！");
                                //Toast toast=null;
                                Looper.prepare();
                                Toast.makeText(MainActivity_WeChat.this, "密码错误！", Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            } else if (result == -1) {
                                Log.e("log_tag", "不存在该用户！");
                                //Toast toast=null;
                                Looper.prepare();
                                Toast.makeText(MainActivity_WeChat.this, "不存在该用户！", Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }).start();
            }
        });}


/*
*用户登录提交post请求
* 向服务器提交数据1.user_id用户名，2.input_pwd密码
* 返回JSON数据{"status":"1"
*/
        private int login()throws Exception{
            int returnResult=0;
    /*获取用户名和密码*/
            String user_id=edit_Username.getText().toString();
            String input_pwd=edit_Password.getText().toString();
            if(user_id==null||user_id.length()<=0){
                Looper.prepare();
                Toast.makeText(MainActivity_WeChat.this,"请输入账号", Toast.LENGTH_LONG).show();
                Looper.loop();
                return 0;

            }
            if(input_pwd==null||input_pwd.length()<=0){
                Looper.prepare();
                Toast.makeText(MainActivity_WeChat.this,"请输入密码", Toast.LENGTH_LONG).show();
                Looper.loop();
                return 0;
            }
            String urlstr="http://10.0.2.2/login.php";

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
            return returnResult;
        };


        /*btn_Login.setOnClickListener(new View.OnClickListener() {  //实现单击登录按钮，发送信息与服务器交互
            @Override
            public void onClick(View v) {

                //当用户名、密码为空时给出相应提示
                if ("".equals(edit_Username.getText().toString())
                        || "".equals(edit_Password.getText().toString())) {
                    Toast.makeText(MainActivity_WeChat.this, "请填写用户名或密码！",
                            Toast.LENGTH_SHORT).show();
                    return;
                }


                handler = new Handler() {  //如果服务器返回值为“ok”证明用户名密码正确并跳转登录后界面否则给出相应的提示信息
                    @Override
                    public void handleMessage(Message msg) {
                        if ("ok".equals(result)) { //如果服务器返回值为“ok”，证明用户名、密码输入正确
                            //跳转登录后界面
                            Intent in = new Intent(MainActivity_WeChat.this, HomeActivity.class);
                            startActivity(in);
                        }else {
                            //用户名、密码错误的提示信息
                            Toast.makeText(MainActivity_WeChat.this, "请填写正确的用户名和密码！",
                                    Toast.LENGTH_SHORT).show();
                        }
                        super.handleMessage(msg);///////////////////////////////////////////*****************************
                    }
                };


                new Thread(new Runnable() {  // 创建一个新线程，用于从网络上获取文件
                    public void run() {
                        try {
                            send();     //调用send()方法，用于发送用户名、密码到Web服务器
                        }catch (Exception e){
                            //**********
                        }
                        Message m = handler.obtainMessage();    // 获取一个Message
                        handler.sendMessage(m);      // 发送消息
                    }
                }).start();     // 开启线程
            }
        });*/ //anniu


//******************************************
       /* TextView password = (TextView)findViewById(R.id.wang_password);
        password.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                //创建intent对象
                Intent it = new Intent(MainActivity_WeChat.this,PasswordActivity.class);
                startActivity(it);
            }


        });*/

//***********************************************
       /* Button bt1=(Button) findViewById(R.id.bt_login); //通过ID获取布局文件中的组件按钮
        bt1.setOnClickListener(new OnClickListener() {  //为按钮添加事件监听器
            @Override
            public void onClick(View v){
                //创建intent对象
                Intent it = new Intent(MainActivity_WeChat.this,HomeActivity.class);
                startActivity(it);
            }
        });*/
//**********************************************
    }

    //创建send方法，用于建立一个HTTP连接，并将输入的内容发送到Web服务器
    /*public void send()throws Exception{//记得抛出错误
        String target="http://10.0.2.2/connect.php";//要提交的服务器地址
        URL url;
        try{
            url= new URL(target);  //创建一个HTTP连接

            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setRequestMethod("POST");      //指定使用POST请求方式
            urlConn.setDoInput(true);              //向连接中写入数据
            urlConn.setDoOutput(true);             //从连接中读取数据
            urlConn.setUseCaches(false);           //禁止缓存***
            urlConn.setInstanceFollowRedirects(true);   //自动执行HTTP重定向
            urlConn.setRequestProperty("Content-Type","application/x-xxx-form-urlencoded");
            //设置内容类型为表单数据***
            DataOutputStream out =new DataOutputStream(urlConn.getOutputStream());//获取输出流
            //OutputStream out=http.getOutputStream();
            //连接要提交的数据
            String param="username="
                    + URLEncoder.encode(edit_Username.getText().toString(),"utf-8")
                    +"&password="
                    + URLEncoder.encode(edit_Password.getText().toString(),"utf-8");


            out.write(param.getBytes());//将要传递的数据写入数据输出流，param提交参数***
            out.flush();//输出缓存
            out.close();//关闭输出流

            if (urlConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                //判断是否响应成功
                InputStreamReader in = new InputStreamReader(urlConn.getInputStream()); // 获得读取的内容
                BufferedReader buffer = new BufferedReader(in); // 获取输入流对象
                String inputLine = null;

                while ((inputLine = buffer.readLine()) != null) {  //通过循环逐行读取输入流中的内容
                    result += inputLine;
                }
                in.close();    //关闭字符输入流
            }
            urlConn.disconnect();    //断开连接

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

*/ //send()

