package com.example.administrator.phonemanager.activity.radiogroup;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.format.Formatter;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.phonemanager.R;
import com.example.administrator.phonemanager.activity.BaseActivity;
import com.example.administrator.phonemanager.utils.BatteryDialog;
import com.example.administrator.phonemanager.utils.PhoneUtil;

import java.io.IOException;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/31.
 */
public class PhoneManager extends BaseActivity {
    private ImageView img_head_left,img_head_right;
    private TextView tv_head,tv_pro,camera_tvone,camera_tvtwo,cpu_tvone,cpu_tvtwo,root_tvone,root_tvtwo,space_tvone,space_tvtwo,version_tvone,version_tvtwo,tv;
    private ProgressBar progressBar;
    private BatteryBr br;
    private BatteryManager battma;
    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_phonemanager);

        //获得运行权限。6.0版本之后必须获得运行权限，而相机属于运行权限之一，不加的话opera()方法会一直调用出错
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            //init(barcodeScannerView, getIntent(), null);
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, 1);//1 can be another integer
        }
    }

    @Override
    protected void setView() {
        img_head_left = (ImageView) findViewById(R.id.head_layout_image_left);
        img_head_right = (ImageView) findViewById(R.id.head_layout_image_right);
        tv_head = (TextView) findViewById(R.id.head_layout_text);
        camera_tvone = (TextView) findViewById(R.id.camera_tvone);
        camera_tvtwo = (TextView) findViewById(R.id.camera_tvtwo);
        cpu_tvone = (TextView) findViewById(R.id.cpu_tvone);
        cpu_tvtwo = (TextView) findViewById(R.id.cpu_tvtwo);
        root_tvone = (TextView) findViewById(R.id.root_tvone);
        root_tvtwo = (TextView) findViewById(R.id.root_tvtwo);
        space_tvone = (TextView) findViewById(R.id.space_tvone);
        space_tvtwo = (TextView) findViewById(R.id.space_tvtwo);
        version_tvone = (TextView) findViewById(R.id.version_tvone);
        version_tvtwo = (TextView) findViewById(R.id.version_tvtwo);
        tv_pro = (TextView) findViewById(R.id.activity_phonemanager_tvbattery);
        progressBar = (ProgressBar) findViewById(R.id.activity_phonemanager_battery);
        tv = (TextView) findViewById(R.id.activity_phonemanager_batterytv);
    }

    @Override
    protected void setDeal() {
        IntentFilter intentFilter= new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        br = new BatteryBr();
        registerReceiver(br,intentFilter);
        setHead();
        setMainData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (br!=null){
            unregisterReceiver(br);
            br=null;
        }
    }

    /**
     * 处理主要数据
     */
    private void setMainData() {
        String name = PhoneUtil.getPhNme(); //设备的名称
        String version = PhoneUtil.getVersion(); //系统版本
        version_tvone.setText("设备名称： "+name);
        version_tvtwo.setText("系统版本： "+version);
        try {
            long maxmemory = PhoneUtil.getMaxMemory(); //运行最大内存
            long freememory = PhoneUtil.getFreeMemory(this); //运行空闲内存
            space_tvone.setText("运行最大内存： "+ Formatter.formatFileSize(this,maxmemory));
            space_tvtwo.setText("运行空闲内存: "+Formatter.formatFileSize(this,freememory));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String cpuname = PhoneUtil.getCpuName();//获得cpu名称
        cpu_tvone.setText("cpu名称:  "+cpuname);
        cpu_tvtwo.setText("cpu数量： "+PhoneUtil.getCpuNumber());
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int screenWidth = screenWidth = display.getWidth();
        int screenHeight = screenHeight = display.getHeight(); //屏幕分辨率
        camera_tvone.setText("手机分辨率： "+String.valueOf(screenWidth)+"*"+String.valueOf(screenHeight));
        camera_tvtwo.setText("相机分辨率： "+PhoneUtil.getCameraPower());
        //是否root
        root_tvone.setText("基带版本： "+ Build.BOARD);
        boolean isroot = PhoneUtil.getRoot();
        if (isroot){
            root_tvtwo.setText("是否root: 是");
        }else {
            root_tvtwo.setText("是否root: 否");
        }


    }

    /**
     * 处理头部数据
     */
    private void setHead() {
        Glide.with(this).load(R.mipmap.back_ico).into(img_head_left);
        img_head_right.setVisibility(View.INVISIBLE);
        tv_head.setText("手机检测");
        img_head_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    class BatteryBr extends BroadcastReceiver{

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())){
                //点亮最大值
                int anInt = intent.getExtras().getInt(BatteryManager.EXTRA_SCALE);
                //电量当前值
                int anInt1 = intent.getExtras().getInt(BatteryManager.EXTRA_LEVEL);
                final double wendu = intent.getExtras().getInt("temperature",0)/10;
                final double pro = Double.valueOf(anInt1)/Double.valueOf(anInt)*100;
                progressBar.setProgress((int)pro);
                tv_pro.setText(((int) pro)+"%");
                if (pro<30){
                    progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.process_back_batlow,null));
                }else if(pro<100){
                    progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.process_back_baterrymi,null));
                }else {
                    progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar_back,null));
                    tv.setBackgroundColor(Color.argb(255,135,218,255));
                }

                progressBar.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        ShowDialog(pro,wendu);
                        return true;
                    }
                });
            }
        }
    }
    /**
     * 电池点击事件
     */
    private void ShowDialog(double pro, double wendu) {
        BatteryDialog.show(PhoneManager.this,pro,wendu);
    }


}
