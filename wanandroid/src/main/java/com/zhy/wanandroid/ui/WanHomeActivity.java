package com.zhy.wanandroid.ui;



import android.support.v4.app.FragmentTransaction;
import com.arms.mvvm.base.MVVMActivity;
import com.zhy.wanandroid.R;
import com.zhy.wanandroid.databinding.ActivityWanhomeBinding;
import com.zhy.wanandroid.ui.fragment.WanFragment;
import com.zhy.wanandroid.viewmodel.WanHomeViewModel;


public class WanHomeActivity extends MVVMActivity<WanHomeViewModel,ActivityWanhomeBinding> {


    @Override
    protected WanHomeViewModel createViewModel() {
        return getViewModel(WanHomeViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wanhome;
    }

    @Override
    protected int getViewModelId() {
        return 0;
    }

    @Override
    public void initData() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout,new WanFragment());
        transaction.commitAllowingStateLoss();
    }

    @Override
    protected boolean isImmersionBarEnabled() {
        return false;
    }
}



