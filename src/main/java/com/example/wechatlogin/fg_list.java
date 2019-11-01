package com.example.wechatlogin;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fg_list,null);
        list_weibo = (ListView) view.findViewById(R.id.list_weibo);
        myAdapter = new WeiboAdapter(datas, getActivity(),this);
        list_weibo.setAdapter(myAdapter);//适配器与ListView关联
        list_weibo.setOnItemClickListener(this);

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
                if(new_datas.isEmpty()){
                    Log.e("new是空的", "空的" );
                    Toast.makeText(getActivity(),"还没有新的微博数据", Toast.LENGTH_SHORT).show();
                }else{
                    myAdapter.addAll(new_datas);
                    new_datas.clear();//清空数据，因为已经是旧数据了
                }
                    }
                });//****END

       button_get_weibo.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Toast.makeText(getActivity(),"得到新的微博数据", Toast.LENGTH_SHORT).show();
               getWeibo();// 发起网络请求get,得到服务器返回的数据并处理
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

                Log.e("log_tag", responseData);
            }
            @Override
            public void onFailure(Call call,IOException e){
                //对异常情况处理
            }
        });
    }//getWeibo

    private void parseJSONwithGSON(String jsonData){
        Gson gson = new Gson();
        //new_datas.clear();//清空新数据，保证里面永远是新的事物
        new_datas = gson.fromJson(jsonData, new TypeToken<ArrayList<weibo>>(){
        }.getType());//这样一来，新数据变成的多个新对象都在这里面了诶嘿。
        weibo_id = new_datas.get(new_datas.size()-1).getId();//保存最新的微博id
        /*for (weibo wb :new_datas){
            Log.e("数据",wb.getwSpeak() );
        }*/
    }//parseJSONwithGSON

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.e("点击项目", "发生了啥" );
        Toast.makeText(getActivity(),"你点击了第" + position + "项",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void click(View v) {
        Toast.makeText(getActivity(),"你点击了第"+(Integer)v.getTag()+"按钮项",Toast.LENGTH_SHORT).show();


        //**********传递ID过去*******//

        Intent it = new Intent(getActivity(), CommentActivity.class);
        Bundle bd = new Bundle();
        bd.putInt("weiboID",(Integer)v.getTag());
        it.putExtras(bd);
        startActivity(it);

        //*********传递结束*********//

    }



}
