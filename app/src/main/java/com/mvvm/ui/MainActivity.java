package com.mvvm.ui;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;

import com.arms.mvvm.base.MVVMActivity;

import com.arms.mvvm.cc.ComponentConstant;
import com.arms.mvvm.utils.Resource;
import com.billy.cc.core.component.CC;
import com.mvvm.BR;
import com.mvvm.R;
import com.mvvm.bean.GankBean;
import com.mvvm.databinding.ActivityMainBinding;
import com.mvvm.viewmodel.MainViewModel;

import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;


public class MainActivity extends MVVMActivity<MainViewModel,ActivityMainBinding> {

    private FragmentManager mFragmentManager;
    private FragmentFactory mFragmentFactory;

    @Override
    public void initView() {
        super.initView();
        //初始化Fragment
        initFragment();
        initBottomTab();
    }

    private void initFragment() {
        mFragmentManager = getSupportFragmentManager();
        mFragmentFactory = new FragmentFactory(mFragmentManager, this);
        switchContent(0);
    }

    private void initBottomTab() {
        NavigationController navigationController = mBinding.pagerBottomTab.material()
                .addItem(R.drawable.ic_home, "首页")
                .addItem(R.drawable.ic_wan, "鸿洋")
                .addItem(R.drawable.ic_me, "我的")
                .setDefaultColor(ContextCompat.getColor(this, R.color.color_1c1c1c))
                .build();
        //底部按钮的点击事件监听
        navigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                switchContent(index);
            }

            @Override
            public void onRepeat(int index) {

            }
        });
    }


    @Override
    public void initData() {

    }

    /**
     * 初始化
     * */
    private void  showData(Resource<GankBean> newData, boolean isFalst){

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected int getViewModelId() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected MainViewModel createViewModel() {
        return getViewModel(MainViewModel.class);
    }


    /**
     * fragment切换
     */
    public Fragment switchContent(int resId) {
        Fragment fragment = (Fragment) mFragmentFactory.instantiateItem(
                mBinding.frameLayout, resId);
        mFragmentFactory.setPrimaryItem(mBinding.frameLayout, 0, fragment);
        mFragmentFactory.finishUpdate(mBinding.frameLayout);
        return fragment;
    }


    @Override
    protected boolean isImmersionBarEnabled() {
        return false;
    }
}
