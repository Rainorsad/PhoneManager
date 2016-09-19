package com.example.administrator.phonemanager.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2016/8/22.
 */
public class MyViewPagerAdapter extends PagerAdapter{

    private List<View> views;
    public MyViewPagerAdapter(List<View> views){
        this.views = views;
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v = views.get(position);
        if (v.getParent()==null){
            container.addView(v);
        }
        return v;
    }


}
