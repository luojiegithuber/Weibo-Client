package com.example.wechatlogin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by DELL on 2019/10/31.
 */

class CommentAdapter extends BaseAdapter {
    private ArrayList<Comment> sayData;
    private Context sayContext;

    public CommentAdapter(ArrayList<Comment> sayData, Context sayContext) {
        this.sayData = sayData;
        this.sayContext = sayContext;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        convertView = LayoutInflater.from(sayContext).inflate(R.layout.comment_item,parent,false);



        TextView txt_Name = (TextView) convertView.findViewById(R.id.name_comment);//用户名
        TextView txt_Say = (TextView) convertView.findViewById(R.id.say_comment);//评论

        //Log.e("nnnn",wData.get(position).getwHead());
        //img_icon.setImageResource(wData.get(position).getwHead());

        txt_Name.setText(sayData.get(position).getName());
        txt_Say.setText(sayData.get(position).getSay());

        return convertView;
    }

    @Override
    public int getCount() {
        return sayData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
