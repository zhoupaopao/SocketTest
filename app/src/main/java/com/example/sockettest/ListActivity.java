package com.example.sockettest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

/**
 * Created by Lenovo on 2019/4/26.
 */

public class ListActivity extends Activity {
    Button listview;
    Button recycleview;
    Button nolist;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        listview=findViewById(R.id.listview);
        recycleview=findViewById(R.id.recycleview);
        nolist=findViewById(R.id.nolist);
    }

    private void initData() {

    }

    private void initListener() {
        listview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ListActivity.this,ListViewActivity.class);
                startActivity(intent);
            }
        });
        recycleview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ListActivity.this,RecycleViewActivity.class);
                startActivity(intent);
            }
        });
        nolist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ListActivity.this,NoListActivity.class);
                startActivity(intent);
            }
        });
    }
}
