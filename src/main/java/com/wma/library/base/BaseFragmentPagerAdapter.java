package com.wma.library.base;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * create by wma
 * on 2020/10/28 0028
 */
public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> mFragments;
    public BaseFragmentPagerAdapter(FragmentManager fm,List<Fragment> fragments) {
        super(fm);
        this.mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
