package com.luhuan.floatinghead;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.tencent.stat.MtaSDkException;
import com.tencent.stat.StatConfig;
import com.tencent.stat.StatService;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public class MainActivity extends Activity {
    XRecyclerView xRecyclerView;

    TextView floatingHead;

    LayoutInflater inflater;
    private int maxDist;
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatConfig.init(this);
        try {
            StatService.startStatService(this,"AG2S8XR9D1BV",com.tencent.stat.common.StatConstants.VERSION);
        } catch (MtaSDkException e) {
            // MTA初始化失败
            Log.e("tengxun", "MTA start failed." );
            Log.e("tengxun", "onCreate: "+e.getMessage());
        }
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
        FloatAdapter floatAdapter=new FloatAdapter(list,this.getApplicationContext());
        //200dp是普通header的高度
        maxDist = DP.d2p(this,200);
        FloatHelper.getInstance(this.getApplicationContext())
                .prepare(xRecyclerView,floatingHead)
                .setManagerAndAdapter(2,floatAdapter)
                .setFloatHead(maxDist);
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

        floatingHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"head1", Toast.LENGTH_SHORT).show();
                Properties prop = new Properties();
                prop.setProperty("keadkey","headvalue");
                StatService.trackCustomKVEvent(MainActivity.this, "button_click", prop);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(this);
    }
}
