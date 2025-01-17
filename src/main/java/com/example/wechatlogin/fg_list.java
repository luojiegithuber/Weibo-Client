package com.example.wechatlogin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.wechatlogin.util.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;


public class fg_list extends Fragment implements AdapterView.OnItemClickListener,WeiboAdapter.Callback{

    private String username;

    private Button button_test,button_get_weibo,button_comment;
    private WeiboAdapter myAdapter=null;
    private FragmentManager fManager;
    private ArrayList<weibo> datas,new_datas;
    private ListView list_weibo;

    //微博id，免得重复刷到一样的微博
    private long weibo_id;

    //空构造函数
    public fg_list() {}

    //构造函数
    @SuppressLint("ValidFragment")
    public fg_list(FragmentManager fManager, ArrayList<weibo> datas) {
        this.fManager = fManager;
        this.datas = datas;
    }
    final Handler myHandler = new Handler()
    {
        @Override
        //重写handleMessage方法,根据msg中what的值判断是否执行后续操作
        public void handleMessage(Message msg) {
            if(msg.what == 0x123)
            {

                if(new_datas.isEmpty()){
                    Log.e("newdatas", "空的" );
                }else{
                    myAdapter.addAll(new_datas);
                    new_datas.clear();//清空数据，因为已经是旧数据了
                }
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fg_list,null);
        list_weibo = (ListView) view.findViewById(R.id.list_weibo);
        myAdapter = new WeiboAdapter(datas, getActivity(),this);
        list_weibo.setAdapter(myAdapter);//适配器与ListView关联
        list_weibo.setOnItemClickListener(this);

        new_datas=new ArrayList<weibo>();
        Bundle bd =this.getArguments();
        username = bd.getString("username");

        button_test=(Button)view.findViewById(R.id.button_test);
        button_get_weibo=(Button)view.findViewById(R.id.button_get_weibo);


        return view;
    }
    //在fragment不能直接进行点击事件，需要放到oncreatActivity中
   @Override
   public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);//？？？？？？？？？


       button_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Looper.prepare();//增加部分
                            getWeibo();// 发起网络请求get,得到服务器返回的数据并处理

                            Looper.loop();//增加部分
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }).start();

                    }
                });//****END

       button_get_weibo.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               new Thread(new Runnable() {
                   @Override
                   public void run() {
                       try {
                           getWeibo();// 发起网络请求get,得到服务器返回的数据并处理
                           Toast.makeText(getActivity(),"得到新的微博数据", Toast.LENGTH_SHORT).show();
                       } catch (Exception e) {
                           System.out.println(e.getMessage());
                       }
                   }
               }).start();


           }
       });//****END




    }//oncreat*/

    private void getWeibo( ){

        HttpUtil.sendOkHttpRequest("http://10.0.2.2/testjson.php",new okhttp3.Callback(){
            @Override
            public void onResponse(Call call, Response response)throws IOException {
                //得到服务器返回内容
                String responseData = response.body().string();
                //处理内容：将返还的JSON数据组变成多个对象
                parseJSONwithGSON(responseData);
                //处理结束了
            }
            @Override
            public void onFailure(Call call,IOException e){
                //对异常情况处理
            }
        });
    }//getWeibo

    private void parseJSONwithGSON(String jsonData){
        Gson gson = new Gson();
        new_datas = gson.fromJson(jsonData, new TypeToken<ArrayList<weibo>>(){
        }.getType());//这样一来，新数据变成的多个新对象都在这里面了诶嘿。
        myHandler.sendEmptyMessage(0x123);
    }//parseJSONwithGSON

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Adapter adpter=parent.getAdapter();
        weibo item=(weibo)adpter.getItem(position);
        Toast.makeText(getActivity(),"点击的数据排序编号是："+item.getId(),Toast.LENGTH_SHORT).show();

    }

    @Override
    public void click(View v) {
        //Toast.makeText(getActivity(),"你点击的项目排序："+v.getTag()+"",Toast.LENGTH_SHORT).show();
        Toast.makeText(getActivity(),"你点击的项目id："+((weibo)datas.get((Integer) v.getTag()-1)).getId()+"",Toast.LENGTH_SHORT).show();
        //**********传递ID过去*******//
        long idid=((weibo)datas.get((Integer) v.getTag()-1)).getId();

        Intent it = new Intent(getActivity(), CommentActivity.class);
        Bundle bd = new Bundle();
        bd.putInt("weiboID",(int)idid);
        bd.putString("username",username);
        it.putExtras(bd);
        startActivity(it);

        //*********传递结束*********//

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
         username = ((HomeActivity) activity).getUsername();
    }//通过强转成宿主activity，就可以获取到传递过来的数据



}
