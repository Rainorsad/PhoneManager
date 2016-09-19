package com.example.administrator.phonemanager.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.phonemanager.R;
import com.example.administrator.phonemanager.adapter.MyViewPagerAdapter;
import com.example.administrator.phonemanager.utils.MyDialog;
import com.example.administrator.phonemanager.utils.MySharepreference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/22.
 */
public class StartActivity extends BaseActivity{
    private ViewPager vp;
    private ImageView imageView;
    private MySharepreference mysp;
    private int[] res = new int[]{R.mipmap.logo_1,R.mipmap.logo_2,R.mipmap.logo_3,R.mipmap.logo_4,R.mipmap.logo_5};
    private List<View> views;
    private MyViewPagerAdapter myViewPagerAdapter;

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_start);
    }

    @Override
    protected void setView() {
        vp = (ViewPager) findViewById(R.id.activity_start_viewpager);
        imageView = (ImageView) findViewById(R.id.activity_start_imageview);
        mysp = new MySharepreference(StartActivity.this);
        views =  new ArrayList<>();
    }

    @Override
    protected void setDeal() {
        initview();
    }

    private void initview() {
        if (mysp.getInfo()==0){
            mysp.setInfo();
            imageView.setVisibility(View.GONE);
            vp.setVisibility(View.VISIBLE);
            views = getViewPagerData();
            myViewPagerAdapter = new MyViewPagerAdapter(views);
            vp.setAdapter(myViewPagerAdapter);
            View view = views.get(views.size()-1);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent it = new Intent(StartActivity.this,MainActivity.class);
                    startActivity(it);
                    finish();
                }
            });
        }else {
            MyDialog.show(StartActivity.this);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    handler.sendEmptyMessage(1);
                }
            },2000);
        }
    }

    private List<View> getViewPagerData() {
        List<View> vie = new ArrayList<>();
        for (int i=0;i<res.length;i++){
            ImageView image = new ImageView(this);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            image.setLayoutParams(params);
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            image.setImageResource(res[i]);
            vie.add(image);
        }
        return vie;
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1){
                MyDialog.dismiss();
                Intent it = new Intent(StartActivity.this,MainActivity.class);
                startActivity(it);
                finish();
            }
        }
    };
}
