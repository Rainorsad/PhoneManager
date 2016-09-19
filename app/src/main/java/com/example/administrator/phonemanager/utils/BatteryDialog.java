package com.example.administrator.phonemanager.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.administrator.phonemanager.R;

/**
 * Created by Administrator on 2016/9/1.
 */
public class BatteryDialog {
    private static Dialog dialog;
    private static TextView tv_dianliang,tv_wendu;
    public static void show(Context context,double d,double w){
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v =layoutInflater.inflate(R.layout.item_battery_dialog,null);
        tv_dianliang = (TextView) v.findViewById(R.id.item_battery_dialog_dianliang);
        tv_wendu = (TextView) v.findViewById(R.id.item_battery_dialog_wendu);
        dialog = new Dialog(context, R.style.batterydialog);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.CENTER;
        window.setAttributes(layoutParams);
        dialog.setContentView(v,layoutParams);

        int di = (int) d;
        int we = (int) w;
        tv_dianliang.setText("电量： "+String.valueOf(di)+"%");
        tv_wendu.setText("温度： "+String.valueOf(we)+"%");
        dialog.show();
    }

    public static void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
