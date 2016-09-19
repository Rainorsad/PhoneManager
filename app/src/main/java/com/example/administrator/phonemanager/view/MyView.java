package com.example.administrator.phonemanager.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Yao on 2016/9/18.
 */
public class MyView extends View{

    Paint p = new Paint();
    RectF rectF; //弧度
    int startAngle = -90; //起始位置
    int endAngle = 0;//结束位置
    int[] back = new int[]{-10,-10,-10,-15,-18,-20};//回退数组值
    int bakIndex = 0;//回退下标
    int[] go=new int[]{8,8,8,10,10,15,18};//前进数组
    int goIndex = 0;//前进下标
    int bgcolor = 0;//背景颜色
    boolean isstart=false;
    int state = 0;//0是回退，1是前进
    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        endAngle = 360;
        postInvalidate();//刷新UI
        bgcolor = Color.RED;
        isstart = false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        rectF = new RectF(0,0,width,height);
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        p.setColor(bgcolor);
        p.setAntiAlias(true);//默认不能逆时针画，设置此方法可以
        canvas.drawArc(rectF,startAngle,endAngle,true,p);
    }

    public void setAngle(final int angle){
        if (isstart){
            return;
        }
        isstart = true;
        final Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                switch (state){
                    case 0:
                        endAngle+=back[bakIndex++];
                        if (bakIndex>=back.length-1){
                            bakIndex=0;
                        }
                        postInvalidate();
                        if (endAngle<=0){

                            endAngle=0;
                            bakIndex=0;
                            isstart=false;
                            state =1;
                        }
                        break;
                    case 1:
                        endAngle+=go[goIndex++];
                        if (goIndex>=go.length-1){
                            goIndex=0;
                        }
                        postInvalidate();
                        if (endAngle>=angle){
                            timer.cancel();
                            goIndex=0;
                            isstart=false;
                            endAngle=angle;
                            state=0;
                        }
                        break;
                }
            }
        };

        timer.schedule(task,24,24);
    }
}
