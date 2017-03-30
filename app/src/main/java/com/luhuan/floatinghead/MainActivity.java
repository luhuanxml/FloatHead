package com.luhuan.floatinghead;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    XRecyclerView xRecyclerView;

    TextView floatingHead;

    LayoutInflater inflater;

    int totalChange;
    private int maxDist;
    private GridLayoutManager manager;

    int spanCount=3;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        xRecyclerView= (XRecyclerView) findViewById(R.id.recycler);
        floatingHead= (TextView) findViewById(R.id.head_text);
        inflater=LayoutInflater.from(this);
        View headimg=inflater.inflate(R.layout.head_img,null);
        xRecyclerView.addHeaderView(headimg);
        View headtext=inflater.inflate(R.layout.head_text,null);
        xRecyclerView.addHeaderView(headtext);

        List<String> list=new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add("item"+i);
        }
        manager = new GridLayoutManager(this,spanCount);
        xRecyclerView.setLayoutManager(manager);
        final FloatAdapter floatAdapter=new FloatAdapter(list,this);
        xRecyclerView.setAdapter(floatAdapter);

        //200dp是普通header的高度
        maxDist = DP.d2p(this,200);
        floatingHead.setTranslationY(maxDist);

        xRecyclerView.setPullRefreshEnabled(true);
        xRecyclerView.setLoadingMoreEnabled(true);

        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                xRecyclerView.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                xRecyclerView.loadMoreComplete();
            }
        });

        xRecyclerView.setTop(0);
        xRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                Log.d("y", "onScrolled: "+dy);
                totalChange+=dy;
                int tranY=Math.max(0, maxDist -totalChange);
                //移动距离超过maxDist，就定在0处  ,barHeight是appbar的高度，必须加上
                floatingHead.setTranslationY(tranY);
                int lastPosition=manager.findLastVisibleItemPosition();
                Log.d("yy", "onScrolled: "+lastPosition);
                if (lastPosition <= 10*spanCount) {
                    floatingHead.setVisibility(View.GONE);
                }else {
                    floatingHead.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
