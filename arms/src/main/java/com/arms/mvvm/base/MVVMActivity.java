package com.arms.mvvm.base;

import android.app.Activity;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import com.arms.mvvm.utils.common.KeyboardUtils;
import com.arms.mvvm.utils.rx.RxManager;
import com.arms.mvvm.view.statusbar.barlibrary.ImmersionBar;


/**
 * @author Jenly <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public abstract class MVVMActivity<VM extends BaseViewModel,VDB extends ViewDataBinding> extends BaseActivity{

    protected VDB mBinding;

    protected VM mViewModel;
    public String mFrom;
    public ImmersionBar mImmersionBar;
    private RxManager mRxManager;

    @Override
    public void initView() {
        //初始化沉浸式
        if (isImmersionBarEnabled()) {
            initImmersionBar();
        }
        mRxManager = new RxManager();
        mBinding = DataBindingUtil.setContentView(this,getLayoutId());
        mViewModel = createViewModel();
        if (mViewModel != null) {
            getLifecycle().addObserver((LifecycleObserver) mViewModel);
            mBinding.setVariable(getViewModelId(),mViewModel);
        }
    }


    public <T extends ViewModel> T getViewModel(@NonNull Class<T> modelClass){
        return ViewModelProviders.of(this).get(modelClass);
    }

    public <T extends ViewModel> T getViewModel(@NonNull Class<T> modelClass, @Nullable ViewModelProvider.Factory factory){
        return ViewModelProviders.of(this,factory).get(modelClass);
    }


    protected abstract VM createViewModel();

    protected abstract  int getLayoutId();

    protected abstract  int getViewModelId();


    public VDB getDataBinding(){
        return mBinding;
    }


    @Override
    public void finish() {
        super.finish();
        KeyboardUtils.hideSoftInput(this);//隐藏软键盘
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            Log.d("XYY", "mFrom --->" + mFrom);
//            if (!TextUtils.isEmpty(mFrom) && "push".equals(mFrom)) {
//                if (RamData.RECORD_ID.containsKey("index")) {
//                    CC.obtainBuilder(ComponentConstant.APP_COMPONENT)
//                            .setActionName(ComponentConstant.APP_COMPONENT_MAIN)
//                            .build().call();
//                    RamData.RECORD_ID.remove("index");
//                } else if (AppManager.getAppManager().findMainActivity() == null) {
//                    CC.obtainBuilder(ComponentConstant.APP_COMPONENT)
//                            .setActionName(ComponentConstant.APP_COMPONENT_SPLASH)
//                            .build().call();
//                }
//                finish();
//                return false;
//            }
//        }
        return super.onKeyDown(keyCode, event);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mViewModel != null) {
            getLifecycle().removeObserver((LifecycleObserver) mViewModel);
        }

        if(isImmersionBarEnabled()){
            mImmersionBar.destroy();
        }
        if(mRxManager!=null){
            mRxManager.clear();
            mRxManager=null;
        }
        mViewModel.removeRxBus();
        mViewModel=null;
        mBinding.unbind();
        mBinding=null;
    }


    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean.
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    /**
     * Init immersion bar.
     */
    protected void initImmersionBar() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
        initBarWhite(this);
    }


    /**
     * Init bar white.
     *
     * @param activity
     *         the activity
     */
    public void initBarWhite(Activity activity) {
        if (activity == null || activity.isFinishing()) {
            return;
        }
        mImmersionBar.statusBarDarkFont(true, 0.3f)
                .init();
    }

    /**
     * Init bar blue.
     *
     * @param activity
     *         the activity
     */
    public void initBarBluekeyboard(Activity activity) {
        if (activity == null || activity.isFinishing()) {
            return;
        }
        mImmersionBar.statusBarDarkFont(false, 0.3f)
                .keyboardEnable(true)
                .init();

    }
}
