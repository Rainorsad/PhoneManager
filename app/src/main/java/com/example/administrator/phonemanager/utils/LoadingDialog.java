package com.example.administrator.phonemanager.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.phonemanager.R;

/**
 * Created by Yao on 2016/9/13.
 */
public class LoadingDialog {
    private static Dialog dialog;
    private static ImageView imageView;
    public static void show(Context context){
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v =layoutInflater.inflate(R.layout.item_loadingdialog,null);
        imageView = (ImageView) v.findViewById(R.id.item_loadingdialog_img);
        dialog = new Dialog(context, R.style.MyDialog);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.gravity = Gravity.CENTER;
        window.setAttributes(layoutParams);
        dialog.setContentView(v,layoutParams);

        Glide.with(context).load(R.drawable.loadingtwo).asGif().into(imageView);
        dialog.show();
    }

    public static void dismiss(){
        if (dialog != null){
            dialog.dismiss();
            dialog = null;
        }
    }
}
