package com.example.wechatlogin;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class WeiboAdapter extends BaseAdapter implements View.OnClickListener {

    private ArrayList<weibo> wData;
    private Context wContext;
    private Callback wcallback;

    /*
     * 自定义接口，用于回调按钮点击事件到Activity
     */
    public interface Callback {
        public void click(View v);
    }



    public WeiboAdapter(ArrayList<weibo> wData, Context wContext,Callback callback) {
        this.wData = wData;
        this.wContext = wContext;
        this.wcallback= callback;
    }

    @Override
    public int getCount() {
        return wData.size();
    }

    @Override
    public Object getItem(int position) {
        return this.wData.get(position);
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
        wData.clear();//清空大数据
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
        Button btn_comment = (Button)convertView.findViewById(R.id.bt_comment);//评论按钮

        //Log.e("nnnn",wData.get(position).getwHead());
        //img_icon.setImageResource(wData.get(position).getwHead());
        img_icon.setImageResource(R.mipmap.ic_launcher);
        txt_aName.setText(wData.get(position).getwName());
        txt_aTime.setText(wData.get(position).getwTime());
        txt_aSpeak.setText(wData.get(position).getwSpeak());

        btn_comment.setOnClickListener(this);
        btn_comment.setTag(position+1);//函数说明

        return convertView;
    }
         //响应按钮点击事件,调用子定义接口，并传入View
         //@Override
         public void onClick(View v) {
                    wcallback.click(v);
            }

}
