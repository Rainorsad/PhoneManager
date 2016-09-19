package com.example.administrator.phonemanager.activity;

import android.animation.Animator;
import android.content.Intent;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.phonemanager.R;
import com.example.administrator.phonemanager.activity.settingpage.AboutUsActivity;
import com.example.administrator.phonemanager.activity.settingpage.HelpInfoActivity;
import com.example.administrator.phonemanager.activity.settingpage.OpinionActivity;
import com.example.administrator.phonemanager.activity.settingpage.VersionActivity;
import com.example.administrator.phonemanager.utils.MySharepreference;
import com.zcw.togglebutton.ToggleButton;

/**
 * Created by Administrator on 2016/9/2.
 */
public class SettingActivity extends BaseActivity {
    private TextView tv_head;
    private ImageView img_head_left,img_head_right, img_help,img_advice,img_friendshap,img_version,img_aboutus;
    private com.zcw.togglebutton.ToggleButton tog_startphone,tog_tell,tog_infopush;
    private MySharepreference myshar;
    private LinearLayout lin_start,lin_tell,lin_info,lin_help,lin_opinion,lin_friend,lin_version,lin_aboutus,lin_main;

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_setting);
        myshar = new MySharepreference(this);
    }

    @Override
    protected void setView() {
        img_head_left = (ImageView) findViewById(R.id.head_layout_image_left);
        img_head_left.setOnClickListener(v);
        img_head_right = (ImageView) findViewById(R.id.head_layout_image_right);
        img_head_right.setOnClickListener(v);
        tv_head = (TextView) findViewById(R.id.head_layout_text);
        initHead();
        lin_main = (LinearLayout) findViewById(R.id.activity_set_main_lin);
        startAnimal();


        lin_start = (LinearLayout) findViewById(R.id.setting_linstart);
        lin_tell = (LinearLayout) findViewById(R.id.setting_tell);
        lin_info = (LinearLayout) findViewById(R.id.setting_lininfo);
        lin_version = (LinearLayout) findViewById(R.id.setting_linversion);
        lin_aboutus = (LinearLayout) findViewById(R.id.setting_linaboutus);
        lin_friend = (LinearLayout) findViewById(R.id.setting_linfriend);
        lin_opinion = (LinearLayout) findViewById(R.id.setting_linopinion);
        lin_help = (LinearLayout) findViewById(R.id.setting_linhelp);
        lin_version.setOnClickListener(v);
        lin_aboutus.setOnClickListener(v);
        lin_friend.setOnClickListener(v);
        lin_help.setOnClickListener(v);
        lin_opinion.setOnClickListener(v);
        img_aboutus = (ImageView) findViewById(R.id.setting_img_aboutus);
        img_aboutus.setOnClickListener(v);

        tog_startphone = (com.zcw.togglebutton.ToggleButton) findViewById(R.id.tog_startphone);
        tog_tell = (com.zcw.togglebutton.ToggleButton) findViewById(R.id.tog_tell);
        tog_infopush = (com.zcw.togglebutton.ToggleButton) findViewById(R.id.tog_infopush);
        img_help = (ImageView) findViewById(R.id.setting_img_help);
        img_help.setOnClickListener(v);
        img_advice = (ImageView) findViewById(R.id.setting_img_advice);
        img_advice.setOnClickListener(v);
        img_friendshap = (ImageView) findViewById(R.id.setting_img_friendshare);
        img_friendshap.setOnClickListener(v);
        img_version = (ImageView) findViewById(R.id.setting_img_versionagain);
        img_version.setOnClickListener(v);

    }


    @Override
    protected void setDeal() {
        initMain();
    }

    private void startAnimal() {
        for (int i=0;i<lin_main.getChildCount();i++){
            View view = lin_main.getChildAt(i);
            view.animate().setStartDelay(100 + i * 30).scaleX(1).scaleY(1);
        }
    }

    /**
     * 处理主要数据
     */
    private void initMain() {
        boolean isstar = myshar.getIsStart();
        boolean istell = myshar.getIsTell();
        boolean isinfopush = myshar.getIsInfoPush();

        if (isstar) {
            tog_startphone.setToggleOff();
        } else {
            tog_startphone.setToggleOn();
        }
        if (istell) {
            tog_tell.setToggleOff();
        } else {
            tog_tell.setToggleOn();
        }
        if (isinfopush) {
            tog_infopush.setToggleOff();
        } else {
            tog_infopush.setToggleOn();
        }

        tog_startphone.setOnToggleChanged(new com.zcw.togglebutton.ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                if (on){
                    tog_startphone.setToggleOn();
                    myshar.setIsStart(false);
                }else {
                    tog_startphone.setToggleOff();
                    myshar.setIsStart(true);
                }
            }
        });

        tog_tell.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                if (on){
                    tog_tell.setToggleOn();
                    myshar.setTell(false);
                }else {
                    tog_tell.setToggleOff();
                    myshar.setTell(true);
                }
            }
        });

        tog_infopush.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                if (on){
                    tog_infopush.setToggleOn();
                    myshar.setInfoPush(false);
                }else {
                    tog_infopush.setToggleOff();
                    myshar.setInfoPush(true);
                }
            }
        });
    }
    /**
     * 处理头部数据
     */
    private void initHead() {
        Glide.with(this).load(R.mipmap.back_ico).into(img_head_left);
        img_head_right.setVisibility(View.INVISIBLE);
        tv_head.setText("设置");
    }

    View.OnClickListener v = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.head_layout_image_left){
                for (int i=lin_main.getChildCount()-1;i>-1;i--){
                    View view1 = lin_main.getChildAt(i);
                    ViewPropertyAnimator viewPropertyAnimator = view1.animate().setStartDelay((lin_main.getChildCount()-1-i)*30)
                            .scaleX(0).scaleY(0);
                    final int finalI = i;
                    viewPropertyAnimator.setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                        }
                        @Override
                        public void onAnimationEnd(Animator animator) {
                            if (finalI ==0){
                                finish();
                            }
                        }
                        @Override
                        public void onAnimationCancel(Animator animator) {
                        }
                        @Override
                        public void onAnimationRepeat(Animator animator) {
                        }
                    });
                }

            } else if (view.getId() == R.id.setting_img_help || view.getId() == R.id.setting_linhelp){
                startactivity(HelpInfoActivity.class);
            }else if (view.getId() == R.id.setting_img_advice|| view.getId() == R.id.setting_linopinion){
                startactivity(OpinionActivity.class);
            }else if (view.getId() == R.id.setting_img_friendshare|| view.getId() == R.id.setting_linfriend){

            }else if (view.getId() == R.id.setting_img_versionagain|| view.getId() == R.id.setting_linversion){
                startactivity(VersionActivity.class);
            }else if (view.getId() == R.id.setting_img_aboutus|| view.getId() == R.id.setting_linaboutus){
                startactivity(AboutUsActivity.class);
            }
        }
    };
    /**
     * 界面跳转
     */
    private void startactivity(Class c) {
        Intent it = new Intent(SettingActivity.this,c);
        startActivity(it);
    }
}
