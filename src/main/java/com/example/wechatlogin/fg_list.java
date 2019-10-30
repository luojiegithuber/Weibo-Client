package com.example.wechatlogin;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class fg_list extends Fragment {

    private Button button_test;
    private WeiboAdapter myAdapter=null;
    private FragmentManager fManager;
    private ArrayList<weibo> datas;
    private ListView list_weibo;

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
        myAdapter = new WeiboAdapter(datas, getActivity());
        list_weibo.setAdapter(myAdapter);//适配器与ListView关联

        button_test=(Button)view.findViewById(R.id.button_test);

        return view;
    }
    //在fragment不能直接进行点击事件，需要放到oncreatActivity中
   @Override
   public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);//？？？？？？？？？


       button_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"偷我按钮干嘛呀", Toast.LENGTH_LONG).show();//可行
                myAdapter.add(new weibo(R.mipmap.ic_launcher,"Yao Jingxian","昨天00:30","睡不啄"));
                    }
                });
                //****END
    }//oncreat*/

}
