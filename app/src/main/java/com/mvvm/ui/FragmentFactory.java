package com.mvvm.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.arms.mvvm.cc.ComponentConstant;
import com.billy.cc.core.component.CC;
import com.mvvm.ui.fragment.HomeFragment;
import com.mvvm.ui.fragment.MoreFragment;


public class FragmentFactory extends FragmentPagerAdapter {

    protected Context context;

    public FragmentFactory(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }


    @Override
    public Fragment getItem(int res) {
        Fragment fragment = null;
        switch (res) {
            case 0:
                fragment=new HomeFragment();
                break;
            case 1:
                Fragment wanFragment = CC
                        .obtainBuilder(ComponentConstant.WAN_COMPONENT)
                        .setActionName(ComponentConstant.WAN_COMPONENT_MAIN)
                        .build()
                        .call()
                        .getDataItem("fragment");
                fragment=wanFragment;
                break;
            case 2:
                fragment=new MoreFragment();
                break;
            default:
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

}
