package com.example.administrator.phonemanager.activity.radiogroup;

import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.phonemanager.R;
import com.example.administrator.phonemanager.activity.BaseActivity;
import com.example.administrator.phonemanager.adapter.MyrecycleViewAdapterTwo;
import com.example.administrator.phonemanager.bean.PhoneCleanBean;
import com.example.administrator.phonemanager.utils.LoadingDialog;
import com.example.administrator.phonemanager.utils.MyRecycleViewItemDecoration;
import com.example.administrator.phonemanager.utils.PhoneUtil;
import com.example.administrator.phonemanager.utils.PhoneUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 垃圾清理界面
 * Created by Yao on 2016/9/12.
 */
public class PhoneCleanActivity extends BaseActivity {
    private TextView tv_head,tv_showall;
    private RecyclerView recyclerView;
    private Button bt;
    private ImageView img_head_left,img_head_right;
    private List<PhoneCleanBean> phoneCleanBeans;
    private long LongMax = 0;
    private MyrecycleViewAdapterTwo m;
    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_phoneclean);
    }

    @Override
    protected void setView() {
        tv_head = (TextView) findViewById(R.id.head_layout_text);
        tv_showall = (TextView) findViewById(R.id.phoneclean_showall);
        recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        bt = (Button) findViewById(R.id.phoneclean_bt);
        img_head_left = (ImageView) findViewById(R.id.head_layout_image_left);
        img_head_right = (ImageView) findViewById(R.id.head_layout_image_right);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        MyRecycleViewItemDecoration md =new MyRecycleViewItemDecoration(MyRecycleViewItemDecoration.VERTICAL);
        md.setColor(0xFFFF0000);
        md.setSize(2);
        recyclerView.addItemDecoration(md);
        phoneCleanBeans = new ArrayList<>();
    }

    @Override
    protected void setDeal() {
        initHead();
        initMainData();
        m = new MyrecycleViewAdapterTwo(PhoneCleanActivity.this,phoneCleanBeans);
        recyclerView.setAdapter(m);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(PhoneCleanActivity.this);
                /**
                 * 取消时监听
                 */
                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                    }
                });
                builder.setTitle("确定要删除么");
                builder.setMessage("删除后可能会丢失数据");
                builder.setNegativeButton("不删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        LongMax=0;
                        for(int a=0;a<m.fileBeen.size();a++){
                            PhoneCleanBean pc = m.fileBeen.get(a);
                            if (pc.getIsselect()){
                                File f= new File(pc.getFilePath());
                                PhoneUtils.deleteFile(f);
                                pc.setFilesize(0);
                                pc.setIsselect(false);
                            }
                            LongMax = LongMax + pc.getFilesize();
                        }
                        tv_showall.setText(Formatter.formatFileSize(PhoneCleanActivity.this,LongMax));
                        m.notifyDataSetChanged();
                    }
                });
                builder.show();
            }
        });
    }


    /**
     * 显示主要数据
     */
    private void initMainData() {
        LoadingDialog.show(PhoneCleanActivity.this);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String path = PhoneUtil.getPhoneInsid()+"/address.db";
                    InputStream is =null;
                    try {
                        InputStream address = getResources().getAssets().open("address.db");
                        PhoneUtils.CopyDb(address, PhoneUtil.getPhoneInsid()+"/address.db");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    List<PhoneCleanBean> garbageInfo = PhoneUtils.getGarbageInfo(path, PhoneCleanActivity.this);
                    for (int i=0;i<garbageInfo.size();i++){
                        PhoneCleanBean phoneCleanBean = garbageInfo.get(i);
                        phoneCleanBean.setIsselect(false);
                        File f = new File(phoneCleanBean.getFilePath());
                        long l = PhoneUtils.fileSize(f);
                        phoneCleanBean.setFilesize(l);
                        Message message = handler.obtainMessage();
                        message.what = 1;
                        message.obj = l;
                        handler.sendMessage(message);
                    }
                    Message m = handler.obtainMessage();
                    m.what = 2;
                    m.obj = garbageInfo;
                    handler.sendMessage(m);
                }
            }).start();


    }

    /**
     * 处理头部数据
     */
    private void initHead() {
        Glide.with(this).load(R.mipmap.back_ico).into(img_head_left);
        img_head_right.setVisibility(View.INVISIBLE);
        tv_head.setText("垃圾清理");
        img_head_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    long filesize = (long) msg.obj;
                    LongMax = LongMax+filesize;
                    tv_showall.setText(Formatter.formatFileSize(PhoneCleanActivity.this,LongMax));
                    break;
                case 2:
                    LoadingDialog.dismiss();
                    phoneCleanBeans.clear();
                    List<PhoneCleanBean> p = (List<PhoneCleanBean>) msg.obj;
                    phoneCleanBeans.addAll(p);
                    m.notifyDataSetChanged();
                    break;
            }
        }
    };
}
