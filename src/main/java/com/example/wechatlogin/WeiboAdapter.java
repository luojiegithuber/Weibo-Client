package com.example.wechatlogin;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class WeiboAdapter extends BaseAdapter {

    private ArrayList<weibo> wData;
    private Context wContext;

    public WeiboAdapter(ArrayList<weibo> wData, Context wContext) {
        this.wData = wData;
        this.wContext = wContext;
    }

    @Override
    public int getCount() {
        return wData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void add(weibo data) {
        if (wData == null) {
            wData = new ArrayList<>();
        }//判空
        wData.add(data);
        notifyDataSetChanged();
    }

    public void addAll(ArrayList<weibo> data) {
        if (wData == null) {
            wData = new ArrayList<>();
        }//判空
        wData.addAll(data);
        notifyDataSetChanged();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        convertView = LayoutInflater.from(wContext).inflate(R.layout.fg_list_item,parent,false);


        ImageView img_icon = (ImageView) convertView.findViewById(R.id.imgtou);//头皮
        TextView txt_aName = (TextView) convertView.findViewById(R.id.name);//用户名
        TextView txt_aTime = (TextView) convertView.findViewById(R.id.time);//时间
        TextView txt_aSpeak = (TextView) convertView.findViewById(R.id.speak);//说说内容

        //Log.e("nnnn",wData.get(position).getwHead());
        //img_icon.setImageResource(wData.get(position).getwHead());
        img_icon.setImageResource(R.mipmap.ic_launcher);
        txt_aName.setText(wData.get(position).getwName());
        txt_aTime.setText(wData.get(position).getwTime());
        txt_aSpeak.setText(wData.get(position).getwSpeak());

        return convertView;
    }


}
