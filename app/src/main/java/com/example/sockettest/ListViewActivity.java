package com.example.sockettest;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import com.scwang.smartrefresh.header.BezierCircleHeader;
import com.scwang.smartrefresh.header.DeliveryHeader;
import com.scwang.smartrefresh.header.DropBoxHeader;
import com.scwang.smartrefresh.header.FlyRefreshHeader;
import com.scwang.smartrefresh.header.FunGameBattleCityHeader;
import com.scwang.smartrefresh.header.FunGameHitBlockHeader;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.header.PhoenixHeader;
import com.scwang.smartrefresh.header.StoreHouseHeader;
import com.scwang.smartrefresh.header.TaurusHeader;
import com.scwang.smartrefresh.header.WaterDropHeader;
import com.scwang.smartrefresh.header.WaveSwipeHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.header.FalsifyHeader;
import com.scwang.smartrefresh.layout.header.TwoLevelHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

/**
 * Created by Lenovo on 2019/4/26.
 */

public class ListViewActivity extends Activity {
    ListView listview;
    ArrayList<String> arrayList=new ArrayList<>();
    RefreshLayout refreshLayout;
    ListViewAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        initView();
        initData();
        initListener();

    }

    private void initView() {
        listview=findViewById(R.id.listview);
         refreshLayout = (RefreshLayout)findViewById(R.id.refreshLayout);

    }

    private void initData() {
        for(int i=0;i<15;i++){
            arrayList.add("数据"+i);
        }
        adapter= new ListViewAdapter(ListViewActivity.this,arrayList);
    }

    private void initListener() {
        listview.setAdapter(adapter);
//        //设置 Header 为 贝塞尔雷达 样式
//        refreshLayout.setRefreshHeader(new BezierRadarHeader(this).setEnableHorizontalDrag(true));
////设置 Footer 为 球脉冲 样式
//        refreshLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));
        refreshLayout.setEnableRefresh(true);//是否启用下拉刷新功能
        refreshLayout.setEnableLoadMore(true);//是否启用上拉加载功能
//        refreshLayout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.black);//全局设置主题颜色
//        refreshLayout.setEnableAutoLoadMore(true);//是否启用列表惯性滑动到底部时自动加载更多
//        refreshLayout.setEnableScrollContentWhenLoaded(true);//是否在加载完成时滚动列表显示新的内容
//        refreshLayout.setEnableHeaderTranslationContent(true);//是否下拉Header的时候向下平移列表或者内容
//        refreshLayout.setEnableFooterTranslationContent(true);//是否上拉Footer的时候向上平移列表或者内容
//        refreshLayout.setEnableLoadMoreWhenContentNotFull(true);//是否在列表不满一页时候开启上拉加载功能
//        refreshLayout.setDisableContentWhenRefresh(false);//是否在刷新的时候禁止列表的操作
//        refreshLayout.setDisableContentWhenLoading(false);//是否在加载的时候禁止列表的操作
//        refreshLayout.setRefreshHeader(new ClassicsHeader(this));//设置Header
//        refreshLayout.setRefreshFooter(new ClassicsFooter(this));//设置Footer
//        refreshLayout.setRefreshHeader(new WaterDropHeader(this));//水滴
//        refreshLayout.setRefreshHeader(new BezierCircleHeader(this));//蓝色圆圈
//        refreshLayout.setRefreshHeader(new ClassicsHeader(this));//最基础的
//        refreshLayout.setRefreshHeader(new DeliveryHeader(this));//会飞的盒子
//        refreshLayout.setRefreshHeader(new DropBoxHeader(this));//掉落的盒子
//        refreshLayout.setRefreshHeader(new FalsifyHeader(this));//无效
//        refreshLayout.setRefreshHeader(new FlyRefreshHeader(this));//无效
//        refreshLayout.setRefreshHeader(new FunGameBattleCityHeader(this));//游戏的界面
//        refreshLayout.setRefreshHeader(new WaveSwipeHeader(this));//掉落的水滴
//        refreshLayout.setRefreshHeader(new PhoenixHeader(this));//风景图
//        refreshLayout.setRefreshHeader(new FunGameHitBlockHeader(this));//游戏
//        refreshLayout.setRefreshHeader(new TwoLevelHeader(this));//基础
//        refreshLayout.setRefreshHeader(new TaurusHeader(this));//飞机
        refreshLayout.setRefreshHeader(new MaterialHeader(this));//最想要的会下来的圈圈
//        refreshLayout.setRefreshHeader(new StoreHouseHeader(this));//文字
//        refreshLayout.setRefreshContent(new View(context));//设置刷新Content（用于非xml布局代替addView）1.0.4
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.refresh();
                        refreshlayout.finishRefresh();
                    }
                },2000);
//                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(final RefreshLayout refreshlayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.additem(arrayList);
                        refreshlayout.finishLoadMore();
                    }
                },2000);
            }
        });

    }
}
