package com.stkj.mvp.adapter.base;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by karma on 18-3-14.
 * 类描述：
 */

public class CommonFragmentPagerAdapter extends FragmentPagerAdapter
{
    public static int MAIN_VIEW_PAGER = 1;//主界面的ViewPager

    private int mViewPagerType = 0;
    public String[] mainViewPagerTitle = null;
    private List<Fragment> mFragments;
    private Context mContext;

    public CommonFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    //根据传入的viewPagerType，在getTitle中返回不同的标题信息
    public CommonFragmentPagerAdapter(Context context, FragmentManager fm, List<Fragment> fragments, int viewPagerType) {
        this(fm, fragments);
        mViewPagerType = viewPagerType;
        this.mContext = context;
    }


    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments != null ? mFragments.size() : 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
//        if (mViewPagerType == MAIN_VIEW_PAGER) {
//            if (mainViewPagerTitle == null) {
//                mainViewPagerTitle = mContext.getResources().getStringArray(R.array.main_view_pager_title);
//            }
//            return mainViewPagerTitle[position];
//        }

        //默认的ViewPager(不需要返回title)
        return super.getPageTitle(position);
    }
}
