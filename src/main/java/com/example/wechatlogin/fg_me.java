package com.example.wechatlogin;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by DELL on 2019/11/4.
 */

public class fg_me extends Fragment {

    TextView textview;
    String username;//用户名

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fg_me,null);//装载布局

        textview = (TextView)view.findViewById(R.id.me_username);
        Bundle bd =this.getArguments();
        username = bd.getString("username");
        textview.setText(username);//变成用户名

        return view;
    }

    //在fragment不能直接进行点击事件，需要放到oncreatActivity中
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
