package com.example.administrator.phonemanager.activity.radiogroup;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.phonemanager.R;
import com.example.administrator.phonemanager.activity.BaseActivity;
import com.example.administrator.phonemanager.adapter.MyRecycleviewadapter;
import com.example.administrator.phonemanager.bean.ModelBean;
import com.example.administrator.phonemanager.utils.ContactsUtils;
import com.example.administrator.phonemanager.utils.MyRecycleViewItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/6.
 */
public class ContactsActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private TextView tv_head;
    private ImageView img_head_left,img_head_right;
    private List<ModelBean> modelBeans;
    private MyRecycleviewadapter myadapter;
    private ContactsUtils c;
    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_contacts);
        //获得运行权限。6.0版本之后必须获得运行权限，而相机属于运行权限之一，不加的话opera()方法会一直调用出错
        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_CONTACTS)
                !=PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_CONTACTS},
                    1);
        }
    }

    @Override
    protected void setView() {
        tv_head = (TextView) findViewById(R.id.head_layout_text);
        img_head_left = (ImageView) findViewById(R.id.head_layout_image_left);
        img_head_right = (ImageView) findViewById(R.id.head_layout_image_right);
        recyclerView = (RecyclerView) findViewById(R.id.recycleview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        MyRecycleViewItemDecoration m =new MyRecycleViewItemDecoration(MyRecycleViewItemDecoration.VERTICAL);
        m.setColor(0xFF000000);
        m.setSize(2);
        recyclerView.addItemDecoration(m);
    }

    @Override
    protected void setDeal() {
        initHead();//处理头部数据
        initMain();//处理主要数据


    }

    /**
     * 处理主要数据
     */
    private void initMain() {
        modelBeans = new ArrayList<>();
        c = new ContactsUtils(this);
        modelBeans = c.getContactsInfo();
        myadapter = new MyRecycleviewadapter(this,modelBeans);
        recyclerView.setAdapter(myadapter);
    }

    /**
     *头部数据处理
     */
    private void initHead() {
        img_head_right.setVisibility(View.INVISIBLE);
        Glide.with(this).load(R.mipmap.back_ico).into(img_head_left);
        tv_head.setText("通讯录");
        tv_head.setTextColor(Color.BLACK);
        img_head_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
