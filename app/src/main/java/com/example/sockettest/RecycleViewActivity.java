package com.example.sockettest;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.header.FunGameBattleCityHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

/**
 * Created by Lenovo on 2019/4/26.
 */

public class RecycleViewActivity extends Activity {
    RecyclerView recyclerView;
    ArrayList<String> arrayList = new ArrayList<>();
    RefreshLayout refreshLayout;
    LinearAdapter adapter;
    int num = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerview);
        refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
    }

    private void initData() {
        for (int i = 0; i < 15; i++) {
            arrayList.add("数据" + i);
        }
        adapter = new LinearAdapter(this, arrayList);
    }


    private void initListener() {
        recyclerView.setLayoutManager(new LinearLayoutManager(RecycleViewActivity.this));
        recyclerView.setAdapter(adapter);
        refreshLayout.setEnableRefresh(true);//是否启用下拉刷新功能
        refreshLayout.setEnableLoadMore(true);//是否启用上拉加载功能
        refreshLayout.setEnableFooterFollowWhenLoadFinished(false);
        refreshLayout.setRefreshHeader(new FunGameBattleCityHeader(this));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        arrayList.clear();
                        for (int i = 0; i < 15; i++) {
                            arrayList.add("数据" + i);
                        }
//                        adapter.notifyItemRangeChanged(0,5) ;//列表从positionStart位置到itemCount数量的列表项进行数据刷新
//                        adapter.notifyItemRangeInserted(names.size()-1, names.size());//批量插入。从起始位置到结束位置
                        adapter.notifyDataSetChanged();//整个数据刷新
                        refreshlayout.finishRefresh();
                    }
                }, 2000);
//                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(final RefreshLayout refreshlayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (num == 2) {
//                            refreshlayout.finishLoadMoreWithNoMoreData();
                            refreshlayout.setNoMoreData(true);
//                            refreshlayout.finishLoadMore();
                            refreshlayout.closeHeaderOrFooter();
//                            refreshlayout.setEnableLoadMore(false);//是否启用上拉加载功能
                        } else {
                            arrayList.add("11111");
                            num++;
                            adapter.notifyItemRangeInserted(arrayList.size() - 1, arrayList.size());//批量插入。从起始位置到结束位置
                            refreshlayout.finishLoadMore();
                        }

                    }
                }, 2000);
            }
        });
    }
}
