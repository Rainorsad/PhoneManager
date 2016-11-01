package com.example.administrator.phonemanager.activity.settingpage;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.administrator.phonemanager.R;
import com.example.administrator.phonemanager.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yao on 2016/9/14.
 */
public class HelpInfoActivity extends BaseActivity{

    private static final int[] mInageId=new int[]{R.drawable.adware_style_applist,R.drawable.adware_style_banner,R.drawable.adware_style_creditswall};
    private ViewPager vp;
    private View view_redpoint,view1,view2,view3;
    private List<View> views;
    private LinearLayout lin;
    private int width; //圆点之间距离
    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_helpinfo);
    }

    @Override
    protected void setView() {
        vp = (ViewPager) findViewById(R.id.helpinfo_viewpager);
        view_redpoint = findViewById(R.id.main_pointred);
        lin = (LinearLayout) findViewById(R.id.helpinfo_lin);
    }

    @Override
    protected void setDeal() {
        setData();
        View view = views.get(views.size()-1);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setData() {
        initView();
        vp.setAdapter(new MyPageAdapter());
        vp.addOnPageChangeListener(new listener());
    }

    /**
     * 初始化界面
     */
    private void initView(){

        //初始化界面
        views = new ArrayList<>();
        LayoutInflater inflater=getLayoutInflater();
        view1 = inflater.inflate(R.layout.item_onelayout,null);
        view2 = inflater.inflate(R.layout.item_twolayout,null);
        view3 = inflater.inflate(R.layout.item_threelayout,null);
        views.add(view1);
        views.add(view2);
        views.add(view3);

        //初始化引导页的小圆点
        for (int i=0;i<mInageId.length;i++){
            View point = new View(this);
            point.setBackgroundResource(R.drawable.shape_point_gray);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(30,30);
            if (i>0){
                params.leftMargin = 20; //设值圆点间间隔
            }
            point.setLayoutParams(params);
            lin.addView(point); //将原点添加给线性布局
        }

        //获取试图树
        lin.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
          //当layout结束后回调此方法
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                lin.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                width = lin.getChildAt(1).getLeft() - lin.getChildAt(0).getLeft();  //得到两个圆点之间距离
            }
        });
    }

    class MyPageAdapter extends PagerAdapter{
        @Override
        public int getCount() {
            return mInageId.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position));
            return views.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    /**
     * ViewPager滑动监听
     */
    class listener implements ViewPager.OnPageChangeListener{

        //状态发生变化，positionOffset偏移百分比，positionOffsetPixels偏移像素
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionrOffsetPixels) {
            int len = (int) (positionOffset * width + position*width);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view_redpoint.getLayoutParams();
            params.leftMargin = len;//设置左边距
            view_redpoint.setLayoutParams(params);//重新设置参数
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

}