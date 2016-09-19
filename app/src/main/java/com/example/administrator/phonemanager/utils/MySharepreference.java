package com.example.administrator.phonemanager.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/8/22.
 */
public class MySharepreference {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public MySharepreference(Context context){
        sharedPreferences = context.getSharedPreferences("useinfo",Context.MODE_PRIVATE);
    }

    /**
     * 判断是否为第一次登陆
     */
    public void setInfo(){
        editor = sharedPreferences.edit();
        editor.putInt("number",1);
        editor.apply();
    }

    public int getInfo(){
        int s1 = sharedPreferences.getInt("number",0);
        return s1;
    }

    /**
     * 是否开机启动
     */
    public void setIsStart(boolean bo){
        editor = sharedPreferences.edit();
        editor.putBoolean("isstart",bo);
        editor.apply();
    }
    public boolean getIsStart(){
        boolean isstart = sharedPreferences.getBoolean("isstart",false);
        return isstart;
    }

    /**
     * 通知图标
     */
    public void setTell(boolean bo){
        editor = sharedPreferences.edit();
        editor.putBoolean("istell",bo);
        editor.apply();
    }

    public boolean getIsTell(){
        boolean isstart = sharedPreferences.getBoolean("istell",false);
        return isstart;
    }

    /**
     * 消息推送
     */
    public void setInfoPush(Boolean bo){
        editor = sharedPreferences.edit();
        editor.putBoolean("isinfopush",bo);
        editor.apply();
    }

    public boolean getIsInfoPush(){
        boolean isstart = sharedPreferences.getBoolean("isinfopush",false);
        return isstart;
    }
}
