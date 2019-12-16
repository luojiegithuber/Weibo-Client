package com.example.wechatlogin;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    //全局掌控的用户名
    private String username;

    Bundle bd=new Bundle();

    private long exittime=0;
    //按钮


    //UI 对象
    private TextView tab_publish;
    private TextView tab_find;
    private TextView tab_list;
    private TextView tab_me;

    //Fragment 对象
    private fg_list fglist;
    private fg_publish fgpublish;
    private fg_me fgme;
    private FragmentManager fManager;//管理者，负责移除，显示，隐藏

    //
    private Context wContext;
    private ArrayList<weibo> datas = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//无标题
        setContentView(R.layout.activity_home);

        Intent it =getIntent();
        bd =it.getExtras();
        username = bd.getString("username");




        //图标太大，变小点。。。
        png_to_icon(R.id.home_icon_1);
        png_to_icon(R.id.home_icon_2);
        png_to_icon(R.id.home_icon_3);
        png_to_icon(R.id.home_icon_4);

        fManager = getFragmentManager();

        bindViews();//初始化绑定
        tab_me.performClick();//模拟第一次点击，首次进入list界面

        datas = new ArrayList<weibo>();


    }

    //UI组件初始化与事件绑定
    private void bindViews() {
        tab_publish = (TextView) findViewById(R.id.home_icon_1);
        tab_list = (TextView) findViewById(R.id.home_icon_2);
        tab_me = (TextView) findViewById(R.id.home_icon_4);

        tab_publish.setOnClickListener(this);
        tab_list.setOnClickListener(this);
        tab_me.setOnClickListener(this);

    }

    //重置所有文本的选中状态
    private void setSelected(){
        tab_publish.setSelected(false);
        tab_list.setSelected(false);
        tab_me.setSelected(false);
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(fgpublish != null)fragmentTransaction.hide(fgpublish);
        if(fglist != null)fragmentTransaction.hide(fglist);
        if(fgme != null)fragmentTransaction.hide(fgme);
    }

    public void onClick(View v) {

        FragmentTransaction fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);

        switch (v.getId()){
            case R.id.home_icon_1:
                setSelected();
                tab_publish.setSelected(true);
                if(fgpublish == null){
                    fgpublish = new fg_publish();
                    fgpublish.setArguments(bd);
                    fTransaction.add(R.id.ly_content,fgpublish);
                }else{
                    fTransaction.show(fgpublish);
                }
                break;
            case R.id.home_icon_2:
                setSelected();
                tab_list.setSelected(true);
                if(fglist == null){
                    //实例化一个fg
                    fglist = new fg_list(fManager, datas);//传递管理者和数据集
                    fglist.setArguments(bd);//将用户名传递过去
                    fTransaction.add(R.id.ly_content,fglist);//将fglist加载到界面上
                    /*list = new fg_list();
                    fTransaction.add(R.id.ly_content,fglist);*/
                }else{
                    fTransaction.show(fglist);
                }
                break;
            case R.id.home_icon_4:
                setSelected();
                tab_me.setSelected(true);
                if(fgme == null){
                    //实例化一个fg
                    fgme = new fg_me();//传递管理者和数据集
                    fgme.setArguments(bd);//将用户名传递过去
                    fTransaction.add(R.id.ly_content,fgme);//将fglist加载到界面上

                }else{
                    fTransaction.show(fgme);
                }
                break;
        }
        fTransaction.commit();
    }




    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){

        //判断是否单击了返回键
        if(keyCode==KeyEvent.KEYCODE_BACK){
            exit();
            return true;          //拦截返回键
        }
        return super.onKeyDown(keyCode,event);//**************************

    }

    public void exit(){
        if((System.currentTimeMillis()-exittime)>2000){//按键时间差是否大于两秒
            Toast.makeText(getApplicationContext(),"再按一次退出程序",Toast.LENGTH_SHORT).show();
            exittime=System.currentTimeMillis();

        }else{
            finish();
            System.exit(0);//退出当前运行的Activity
        }

    }

    public void png_to_icon(int id){
        TextView textview = (TextView) findViewById(id);
        Drawable[] drawable = textview.getCompoundDrawables();
        // 数组下表0~3,依次是:左上右下
        drawable[1].setBounds(0,0, 100,100);
        textview.setCompoundDrawables(null, drawable[1],null, null);
    }

    public String getUsername(){
        return username;
    }

}
