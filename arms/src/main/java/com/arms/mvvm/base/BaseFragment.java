package com.arms.mvvm.base;

import android.app.Activity;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arms.mvvm.utils.MLog;
import com.bumptech.glide.Glide;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @Description:
 * @Author: xuwenlong
 * @Email: xuwenlong @ebrun.com
 * @Version:
 * @Create: 2019/1/24 0024
 * @Modify:
 */

public abstract class BaseFragment<VDB extends ViewDataBinding, VM extends BaseViewModel> extends Fragment  {

    public String TAG;
    public VDB mBinding;
    public VM mViewModel;
    private boolean isPrepared;
    /**
     * The Islazy load.
     */
    public boolean islazyLoad = true;

    private boolean isFirst = true;



    private boolean isVisible;

    private Context mContext;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG=this.getClass().getSimpleName();
        mContext = getActivity();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, initContentView(inflater, container, savedInstanceState), container, false);
        mViewModel=initViewModel();
        if (mViewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = BaseViewModel.class;
            }
            mViewModel = (VM) createViewModel(this, modelClass);
        }
            mBinding.setVariable(initVariableId(), mViewModel);
            //让ViewModel拥有View的生命周期感应
            getLifecycle().addObserver(mViewModel);
        return mBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared = true;
        lazyLoad();
        onRestoreInstanceState(savedInstanceState);
    }

    /**
     * restore the fragment
     *
     * @param saveInstanceState
     *         the save instance state
     */
    public void onRestoreInstanceState(Bundle saveInstanceState) {

    }

    /**
     * 初始化数据
     * */
    protected abstract void initData();

    /**
     * 初始化View
     * */
    protected abstract void initView();

    /**
     * 初始化layout
     * */
    protected abstract int initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);


    protected abstract int initVariableId();

    /**
     * 创建ViewModel
     *
     * @param cls
     * @param <T>
     * @return
     */
    public <T extends ViewModel> T createViewModel(Fragment fragment, Class<T> cls) {
        return ViewModelFactory.getInstance(getActivity().getApplication())
                .createViewModel(fragment.getActivity(), cls);
    }


    protected  VM initViewModel(){
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getLifecycle().removeObserver(mViewModel);
        mViewModel.removeRxBus();
        mViewModel = null;
        mBinding.unbind();
        mBinding = null;
    }


    //    public  boolean isNetConnected(){
//        return CacheDataSource.isIsNetConnected();
//    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //可见时执行的操作
            isVisible = true;
            lazyLoad();
            onVisible();
        } else {
            //不可见时执行的操作
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * Called when the fragment change to be invisible.
     */
    public void onVisible() {
        if (isAdded()) {
            Glide.with(this).onStart();
        }
    }

    /**
     * Called when the fragment change to be invisible.
     */
    public void onInvisible() {
        if (isAdded()) {
            Log.d("XYY", " fragment ----->>>" + "onInvisible()");
            Glide.with(this).onStop();
        }
    }

    /**
     * Lazy load data for the fragment.
     */
    private void lazyLoad() {
        if (!islazyLoad) {
            initData();
            isFirst = false;
            return;
        }
        Log.d("DaLong", isPrepared + "    " + isVisible + "    " + isFirst);
        if (!isPrepared || !isVisible || !isFirst) {
            return;
        }
        initData();
        isFirst = false;
    }




    @Override
    public void onDestroyView() {
        try {
            boolean isFinish = mContext != null && mContext instanceof Activity
                    && !((Activity) mContext).isFinishing();
            if (isFinish) {
                Glide.with(this).onDestroy();
                Glide.get(mContext).clearMemory();
                MLog.d("xyy", "Glide.with(this).onDestroy()---->>>>");
            }
        } catch (Exception e) {
            MLog.e("", e);
        }
        super.onDestroyView();
    }


    /**
     * 对Fragment的show, hide进行监听
     * */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){
            MLog.d(TAG,hidden);
        }else{
            MLog.d(TAG,hidden);
        }
    }
}
