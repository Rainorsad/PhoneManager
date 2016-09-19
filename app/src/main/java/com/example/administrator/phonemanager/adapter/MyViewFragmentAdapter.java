package com.example.administrator.phonemanager.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/8/24.
 */
public class MyViewFragmentAdapter extends FragmentPagerAdapter{
    private List<Fragment> fragments;
    private List<String> titles;
    public MyViewFragmentAdapter(FragmentManager fm,List<Fragment> listfragments, List<String> list_title) {
        super(fm);
        this.fragments = listfragments;
        this.titles = list_title;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
