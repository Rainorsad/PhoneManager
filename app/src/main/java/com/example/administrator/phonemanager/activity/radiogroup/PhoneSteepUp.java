package com.example.administrator.phonemanager.activity.radiogroup;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/24.
 */
public class PhoneSteepUp extends BaseActivity {
    private ImageView img_head_left,img_head_right;
    private TextView tv_head,tv_useId,tv_sql,tv_show;
    private RadioGroup rg;
    private RecyclerView myrecy;
    private RadioButton useradio,mainradio;
    private Button bt_clean;
    private ProgressBar progressBar;
    private MyRecycleviewadapter myRecycleviewadapter;
    private List<ModelBean> usemodelBeans,mainmodelBeans,showmodelBeans;
    private int index = 0;//判断线城是主线程还是用户线程 0标识用户 1标识系统
    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_phonesteepup);
    }

    @Override
    protected void setView() {
        img_head_left = (ImageView) findViewById(R.id.head_layout_image_left);
        img_head_right = (ImageView) findViewById(R.id.head_layout_image_right);
        tv_head = (TextView) findViewById(R.id.head_layout_text);
        tv_useId = (TextView) findViewById(R.id.activity_phonesteepup_textID);
        tv_sql = (TextView) findViewById(R.id.activity_phonesteepup_sql);
        tv_show = (TextView) findViewById(R.id.activity_phonesteepup_show);
        bt_clean = (Button) findViewById(R.id.activity_phonesteepup_bt_clean);
        progressBar = (ProgressBar) findViewById(R.id.activity_phonesteepup_progress);
        rg = (RadioGroup) findViewById(R.id.activity_phonesteepup_radgroup);
        useradio = (RadioButton) findViewById(R.id.phsteep_usethread);
        mainradio = (RadioButton) findViewById(R.id.phsteep_mainthread);
        myrecy = (RecyclerView) findViewById(R.id.activity_phonesteepup_recycle);

        myrecy.setLayoutManager(new LinearLayoutManager(this));
        myrecy.setItemAnimator(new DefaultItemAnimator());
        MyRecycleViewItemDecoration m =new MyRecycleViewItemDecoration(MyRecycleViewItemDecoration.VERTICAL);
        m.setColor(0xFFFF0000);
        m.setSize(2);
        myrecy.addItemDecoration(m);

        useradio.setOnClickListener(v);
        mainradio.setOnClickListener(v);
        bt_clean.setOnClickListener(v);
    }

    @Override
    protected void setDeal() {
        setHead();
        setMainView();
    }

    /**
     * 处理主界面数据
     */
    private void setMainView(){
        tv_useId.setText(PhoneUtil.getPhNme());
        tv_sql.setText(PhoneUtil.getVersion());
        loadData();
    }

    /**
     * 加载主要数据
     */
    private void loadData() {
        long maxmemory = 0;
        try {
            maxmemory = PhoneUtil.getMaxMemory();
            long freememory = PhoneUtil.getFreeMemory(this);
            long usememory = maxmemory-freememory;
            final double pro = Double.valueOf(usememory+"")/Double.valueOf(maxmemory+"")*100;

            rg.check(R.id.phsteep_usethread);
            progressBar.setIndeterminate(true);

            String useMe = Formatter.formatFileSize(this, usememory);
            String freeMe = Formatter.formatFileSize(this,freememory);
            String maxMe = Formatter.formatFileSize(this,maxmemory);
            tv_show.setText("已用内存 "+useMe+"/"+maxMe);

            showmodelBeans = new ArrayList<>();
            myRecycleviewadapter = new MyRecycleviewadapter(PhoneSteepUp.this,showmodelBeans);
            myrecy.setAdapter(myRecycleviewadapter);
            MyDialog.show(this);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    setThread(pro);
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过线程获取数据
     * @param pro
     */
    private void setThread(double pro) {
        List<ModelBean> modelBeen = PhoneUtil.getThread(PhoneSteepUp.this);
        usemodelBeans  = new ArrayList<>();
        mainmodelBeans = new ArrayList<>();
        for (int i=0;i<modelBeen.size();i++) {
            ModelBean modelBean = modelBeen.get(i);
            if (modelBean.getType() == 2) {
                modelBean.setType(ModelBean.Y_type);
                modelBean.setIsselect(false);
                mainmodelBeans.add(modelBean);
            } else {
                modelBean.setType(ModelBean.X_type);
                modelBean.setIsselect(true);
                usemodelBeans.add(modelBean);
            }
        }
        Message msg = new Message();
        Bundle bundle = new Bundle();
        bundle.putDouble("pro",pro);
        msg.setData(bundle);
        msg.what=1;
        handler.sendMessage(msg);
    }

    /**
     * 处理头部数据
     */
    private void setHead() {
        Glide.with(this).load(R.drawable.flush).into(img_head_right);
        Glide.with(this).load(R.mipmap.back_ico).into(img_head_left);
        tv_head.setText("手机加速");
        tv_head.setTextColor(Color.BLACK);
        img_head_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        img_head_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData();
            }
        });
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    MyDialog.dismiss();
                    showmodelBeans.clear();
                    if (index == 0){
                        showmodelBeans.addAll(usemodelBeans);
                    }else if (index ==1){
                        showmodelBeans.addAll(mainmodelBeans);
                    }
                    myRecycleviewadapter.notifyDataSetChanged();
                    progressBar.setIndeterminate(false);
                    double pro = msg.getData().getDouble("pro");
                    progressBar.setProgress((int) pro);
                    break;
            }
        }
    };

    View.OnClickListener v =new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.phsteep_usethread:
                    MyDialog.dismiss();
                    index = 0;
                    handler.sendEmptyMessage(1);
                    break;
                case R.id.phsteep_mainthread:
                    MyDialog.dismiss();
                    index = 1;
                    handler.sendEmptyMessage(1);
                    break;
                case R.id.activity_phonesteepup_bt_clean:
//                    PhoneUtil.killOtherApp(usemodelBeans,PhoneSteepUp.this);
                    Kill();
                    loadData();
                    break;
            }
        }
    };

    private void Kill() {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> infoList = am.getRunningAppProcesses();
        List<ActivityManager.RunningServiceInfo> serviceInfos = am.getRunningServices(100);
        if (infoList != null) {
            for (int i = 0; i < infoList.size(); ++i) {
                ActivityManager.RunningAppProcessInfo appProcessInfo = infoList.get(i);
                if (appProcessInfo.importance > ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE) {
                    String[] pkgList = appProcessInfo.pkgList;
                    for (int j = 0; j < pkgList.length; ++j) {//pkgList 得到该进程下运行的包名
                        am.killBackgroundProcesses(pkgList[j]);
                    }
                }
            }
        }
    }
}
