package com.example.administrator.phonemanager.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.phonemanager.R;
import com.example.administrator.phonemanager.activity.radiogroup.ContactsActivity;
import com.example.administrator.phonemanager.activity.radiogroup.FileManageActivity;
import com.example.administrator.phonemanager.activity.radiogroup.PhoneCleanActivity;
import com.example.administrator.phonemanager.activity.radiogroup.PhoneManager;
import com.example.administrator.phonemanager.activity.radiogroup.PhoneSteepUp;
import com.example.administrator.phonemanager.activity.radiogroup.SoftManagerActivity;
import com.example.administrator.phonemanager.utils.PhoneUtil;
import com.example.administrator.phonemanager.view.MyView;

import java.io.IOException;
import java.util.List;

public class MainActivity extends BaseActivity {

    private ImageView img_head_left,img_head_right,img_steep;
    private TextView tv_head,tv_steepup;
    private MyView myView;
    private RadioButton radio_rocket,radio_softmgr,radio_phonemgr,radio_telmgr,radio_filemgr,radio_sdclean;
    private Button bt;
    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void setView() {
        img_head_left = (ImageView) findViewById(R.id.head_layout_image_left);
        img_head_right = (ImageView) findViewById(R.id.head_layout_image_right);
        tv_head = (TextView) findViewById(R.id.head_layout_text);
        radio_rocket = (RadioButton) findViewById(R.id.radio_rocket);
        radio_filemgr = (RadioButton) findViewById(R.id.radio_filemgr);
        radio_phonemgr = (RadioButton) findViewById(R.id.radio_phonemgr);
        radio_sdclean = (RadioButton) findViewById(R.id.radio_sdclean);
        radio_softmgr = (RadioButton) findViewById(R.id.radio_softmgr);
        radio_telmgr = (RadioButton) findViewById(R.id.radio_telmgr);
        myView = (MyView) findViewById(R.id.main_myview);
        tv_steepup = (TextView) findViewById(R.id.main_steepup_tv);
        img_steep = (ImageView) findViewById(R.id.main_steepup_img);
        bt = (Button) findViewById(R.id.main_steepup_but);

    }

    @Override
    protected void setDeal() {
        setHead();
        initMainData();
        setRadio();
    }

    private void initMainData() {
        setSteep();
    }

    /**
     * radio点击事件处理
     */
    private void setRadio() {
        radio_sdclean.setOnClickListener(v);
        radio_telmgr.setOnClickListener(v);
        radio_softmgr.setOnClickListener(v);
        radio_filemgr.setOnClickListener(v);
        radio_rocket.setOnClickListener(v);
        radio_phonemgr.setOnClickListener(v);
        bt.setOnClickListener(v);
        img_steep.setOnClickListener(v);
    }

    /**
     * 主主界面头部处理
    */
    private void setHead() {
        Glide.with(this).load(R.drawable.setting).into(img_head_right);
        Glide.with(this).load(R.mipmap.ic_launcher).into(img_head_left);
        tv_head.setText("手机易管家");
        img_head_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startactivity(SettingActivity.class);
            }
        });



    }

    /**
     * 界面跳转
     */
    private void startactivity(Class c) {
        Intent it = new Intent(MainActivity.this,c);
        startActivity(it);
    }

    View.OnClickListener v = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.radio_rocket:
                    startactivity(PhoneSteepUp.class);
                    break;
                case R.id.radio_softmgr:
                    startactivity(SoftManagerActivity.class);
                    break;
                case R.id.radio_phonemgr:
                    startactivity(PhoneManager.class);
                    break;
                case R.id.radio_telmgr:
                    startactivity(ContactsActivity.class);
                    break;
                case R.id.radio_filemgr:
                    startactivity(FileManageActivity.class);
                    break;
                case R.id.radio_sdclean:
                    startactivity(PhoneCleanActivity.class);
                    break;
                case R.id.main_steepup_but:
                    setSteep();
                    break;
                case R.id.main_steepup_img:
                    setSteep();
                    break;
            }
        }
    };

    private void setSteep() {
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

        long maxmemory = 0;
        try {
            maxmemory = PhoneUtil.getMaxMemory();
            long freememory = PhoneUtil.getFreeMemory(this);
            long usememory = maxmemory-freememory;
            int pro = (int)(Double.valueOf(usememory+"")/Double.valueOf(maxmemory+"")*100);
            tv_steepup.setText(pro+"%");
            int data = (int)((Double.valueOf(usememory+"")/Double.valueOf(maxmemory+"")*360));
            myView.setAngle(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
