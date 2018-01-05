package com.yueliao.core.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wanglei on 2016/12/10.
 */

public class FragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    public FragmentAdapter(FragmentManager fm, List<Fragment> fragmentList, String[] titles) {
        super(fm);
        this.fragmentList.clear();
        this.fragmentList.addAll(fragmentList);
        // Arrays.asList该方法将数组与列表链接起来，当更新其中之一时，另一个自动更新
        // 不支持add和remove方法（出现异常）
        this.titles = Arrays.asList(titles);
    }

    public FragmentAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> titles) {
        super(fm);
        this.fragmentList.clear();
        this.fragmentList.addAll(fragmentList);
        this.titles.addAll(titles);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (!titles.isEmpty() && titles.size() > position) {
            return titles.get(position);
        }
        return "";
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
