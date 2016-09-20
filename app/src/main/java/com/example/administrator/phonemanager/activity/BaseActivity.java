package com.example.administrator.phonemanager.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.administrator.phonemanager.activity.utils.SystemManager;

/**
 * Created by Administrator on 2016/8/24.
 */
public abstract class BaseActivity extends AppCompatActivity{
    protected Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout();
        String apkRoot = "chmod 777 "+getPackageCodePath();
        SystemManager.RootCommand(apkRoot);
        setView();
        setDeal();
    }
    //绑定界面
    protected abstract void setLayout();
    //初始化控件'
    protected abstract void setView();
    //处理逻辑事件
    protected abstract void setDeal();
    //弹出框短时间内容显示
    public void ToaS(Context context,String s){
        Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
    }
    //弹出框长时间显示
    public void ToaL(Context context,String s){
        Toast.makeText(context,s,Toast.LENGTH_LONG).show();
    }
}
