package com.example.wechatlogin;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class CommentActivity extends AppCompatActivity {
    private ArrayList<Comment> cData = null;
    private Context cContext;
    private CommentAdapter cAdapter = null;
    private ListView list_comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        cContext = CommentActivity.this;
        list_comment = (ListView) findViewById(R.id.list_comment);
        cData = new ArrayList<Comment>();
        cAdapter = new CommentAdapter(cData,cContext);

        cData.add(new Comment("Song Zhihao","wo shi zhen de niu bi"));

        list_comment.setAdapter(cAdapter);

    }
}
