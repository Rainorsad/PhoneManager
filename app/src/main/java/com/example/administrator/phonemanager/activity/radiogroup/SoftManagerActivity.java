package com.example.administrator.phonemanager.activity.radiogroup;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.Formatter;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.phonemanager.R;
import com.example.administrator.phonemanager.activity.BaseActivity;
import com.example.administrator.phonemanager.adapter.MyRecycleviewadapter;
import com.example.administrator.phonemanager.bean.ModelBean;
import com.example.administrator.phonemanager.utils.MyDialog;
import com.example.administrator.phonemanager.utils.MyRecycleViewItemDecoration;
import com.example.administrator.phonemanager.utils.PhoneUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/29.
 */
public class SoftManagerActivity extends BaseActivity {
    private TextView tv_head,tv_insidsql,tv_outsql,tv_outshow;
    private ImageView img_left,img_right,img_frag;
    private ProgressBar proinside,proout;
    private RecyclerView myrecycle;
    private RadioGroup rg;
    private RadioButton rg_all,rg_main,rg_use;
    private int index = 0; //0表示全部程序 1表示用户程序 2表示系统程序
    private List<ModelBean> allModelBeans,showModelBeans,userModelBeans,sysModelBeans;
    private MyRecycleviewadapter myRecycleviewadapter;
    private double insideprocess;
    private Animation am;
    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_softmanager);
    }

    @Override
    protected void setView() {
        ButterKnife.bind(this);
        tv_head = (TextView) findViewById(R.id.head_layout_text);
        tv_insidsql = (TextView) findViewById(R.id.activity_softmanage_insidesql);
        tv_outsql = (TextView) findViewById(R.id.activity_softmanage_outsql);
        tv_outshow = (TextView) findViewById(R.id.activity_softmanage_outshow);
        img_left = (ImageView) findViewById(R.id.head_layout_image_left);
        img_right = (ImageView) findViewById(R.id.head_layout_image_right);
        img_frag = (ImageView) findViewById(R.id.activity_softmanage_image);
        proinside = (ProgressBar) findViewById(R.id.activity_softmanage_insideprogress);
        proout = (ProgressBar) findViewById(R.id.activity_softmanage_outprogress);
        myrecycle = (RecyclerView) findViewById(R.id.activity_softmanage_recycle);
        rg = (RadioGroup) findViewById(R.id.activity_softmanage_radgroup);
        rg_all = (RadioButton) findViewById(R.id.softmanage_allsoft);
        rg_main = (RadioButton) findViewById(R.id.softmanage_mainsoft);
        rg_use = (RadioButton) findViewById(R.id.softmanage_usersoft);

        myrecycle.setLayoutManager(new LinearLayoutManager(this));
        myrecycle.setItemAnimator(new DefaultItemAnimator());
        MyRecycleViewItemDecoration myRecycleViewItemDecoration = new MyRecycleViewItemDecoration(MyRecycleViewItemDecoration.VERTICAL);
        myRecycleViewItemDecoration.setSize(2);
        myRecycleViewItemDecoration.setColor(0xFFADC119);
        myrecycle.addItemDecoration(myRecycleViewItemDecoration);

        rg_all.setOnClickListener(v);
        rg_use.setOnClickListener(v);
        rg_main.setOnClickListener(v);
    }

    @Override
    protected void setDeal() {
        setHead();
        setMain();
    }

    /**
     * 设置主界面数据
     */
    private void setMain() {
        setThread();
    }

    /**
     * 处理主要数据
     */
    private void setThread() {
        MyDialog.show(SoftManagerActivity.this);
        setAnimal();
        setProgressBar();
        showModelBeans = new ArrayList<>();
        myRecycleviewadapter = new MyRecycleviewadapter(this,showModelBeans);
        myrecycle.setAdapter(myRecycleviewadapter);
        new Thread(new Runnable() {
            @Override
            public void run() {
                loadData(insideprocess);
            }
        }).start();
    }

    /**
     * 加载数据
     * @param insideprocess
     */
    private void loadData(double insideprocess) {
        List<ModelBean> modelBeen = PhoneUtil.getAppinfo(SoftManagerActivity.this);
        allModelBeans = new ArrayList<>();
        userModelBeans = new ArrayList<>();
        sysModelBeans = new ArrayList<>();
        for (int i=0;i<modelBeen.size();i++){
            ModelBean modelBean = modelBeen.get(i);
            modelBean.setType(ModelBean.Z_type);
            allModelBeans.add(modelBean);
            if (modelBean.isSoftma_isuse()){
                userModelBeans.add(modelBean);
            }else {
                sysModelBeans.add(modelBean);
            }
        }
        final Message ms = new Message();
        Bundle bundle = new Bundle();
        bundle.putDouble("pro",insideprocess);
        ms.setData(bundle);
        ms.what=1;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.sendMessage(ms);
            }
        },2000);
    }

    /**
     * progressbar设置
     */
    private void setProgressBar() {
        //内置可用空间
        long insidefreememory = PhoneUtil.getInsideMemoryFree();
        //内置最大空间
        long insidemaxmemory = PhoneUtil.getInsideMemoryMax();
        insideprocess = Double.valueOf((insidemaxmemory - insidefreememory) + "") / Double.valueOf(insidemaxmemory+"") * 100;
        proinside.setIndeterminate(true);
        String insideusemem = Formatter.formatFileSize(SoftManagerActivity.this,(insidemaxmemory - insidefreememory));
        String insidemaxmem = Formatter.formatFileSize(SoftManagerActivity.this,insidemaxmemory);
        tv_insidsql.setText("内置已用内存： "+insideusemem+"/"+insidemaxmem);
        //外置空间
        if (PhoneUtil.getPhoneOutPath() == null){
            proout.setVisibility(View.GONE);
            tv_outsql.setVisibility(View.GONE);
            tv_outshow.setVisibility(View.GONE);
        }else {
            long outFreeMemory = PhoneUtil.getOutMemoryFree();
            long outMaxMemory = PhoneUtil.getOutMemoryMax();
            double outprogress = Double.valueOf((outMaxMemory-outFreeMemory)+"")/Double.valueOf(outMaxMemory+"")*100;
            proout.setProgress((int) outprogress);
            String outusmem = Formatter.formatFileSize(SoftManagerActivity.this,(outMaxMemory - outFreeMemory));
            String outmaxmem = Formatter.formatFileSize(SoftManagerActivity.this,outMaxMemory );
            tv_outsql.setText("外置内存已用： "+outusmem+"/"+outmaxmem);
        }
    }

    /**
     * 图片动画效果处理
     */
    private void setAnimal() {
        am = AnimationUtils.loadAnimation(SoftManagerActivity.this,R.anim.animal);
        LinearInterpolator lir = new LinearInterpolator();
        am.setInterpolator(lir);
        am.setFillAfter(true);
        img_frag.setAnimation(am);
    }

    /**
     * 设置头部数据
     */
    private void setHead() {
        Glide.with(this).load(R.mipmap.back_ico).into(img_left);
        img_right.setVisibility(View.INVISIBLE);
        tv_head.setText("软件管理");
        tv_head.setTextColor(Color.BLACK);
        img_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * handler线程
     */
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    MyDialog.dismiss();
                    showModelBeans.clear();
                    if (index == 0){
                        showModelBeans.addAll(allModelBeans);
                    }else if (index == 1){
                        showModelBeans.addAll(userModelBeans);
                    }else {
                        showModelBeans.addAll(sysModelBeans);
                    }
                    double pro =msg.getData().getDouble("pro");
                    myRecycleviewadapter.notifyDataSetChanged();
                    proinside.setIndeterminate(false);
                    proinside.setProgress((int) pro);
                    img_frag.clearAnimation();
                    break;
            }
        }
    };

    /**
     * 监听事件
     */
    View.OnClickListener v = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.softmanage_allsoft:
                    index = 0;
                    handler.sendEmptyMessage(1);
                    break;
                case R.id.softmanage_mainsoft:
                    index = 2;
                    handler.sendEmptyMessage(1);
                    break;
                case R.id.softmanage_usersoft:
                    index = 1;
                    handler.sendEmptyMessage(1);
                    break;
            }
        }
    };
}
