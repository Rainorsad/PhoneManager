package com.example.administrator.phonemanager.activity.settingpage;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.phonemanager.R;
import com.example.administrator.phonemanager.activity.BaseActivity;

/**
 * Created by Yao on 2016/9/14.
 */
public class OpinionActivity extends BaseActivity{


    private EditText edit;
    private Button bt;
    private TextView tv_head;
    private ImageView img_head_left,img_head_right;
    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_option);
    }

    @Override
    protected void setView() {

        edit = (EditText) findViewById(R.id.opintion_edit);
        bt = (Button) findViewById(R.id.opinion_bt);
        img_head_left = (ImageView) findViewById(R.id.head_layout_image_left);
        img_head_right = (ImageView) findViewById(R.id.head_layout_image_right);
        tv_head = (TextView) findViewById(R.id.head_layout_text);
    }

    @Override
    protected void setDeal() {
        initHead();
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = edit.getText().toString();
                if (s.length()==0 ){
                    ToaS(OpinionActivity.this,"请输入反馈意见");
                }else {
                    ToaS(OpinionActivity.this,"已经接受到你的意见,我们的工程师将会解决");
                    finish();
                }
            }
        });
    }

    private void initHead() {
        img_head_right.setVisibility(View.INVISIBLE);
        Glide.with(this).load(R.mipmap.back_ico).into(img_head_left);
        tv_head.setText("意见反馈");
        img_head_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
