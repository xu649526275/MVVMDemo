package com.mvvm.ui.fragment;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arms.mvvm.base.BaseFragment;
import com.arms.mvvm.utils.MLog;
import com.arms.mvvm.utils.Resource;
import com.arms.mvvm.utils.Status;
import com.arms.mvvm.view.baseadapter.BaseQuickAdapter;
import com.arms.mvvm.view.refresh.api.RefreshLayout;
import com.arms.mvvm.view.refresh.listener.OnRefreshListener;
import com.arms.mvvm.view.refresnheader.DeliveryHeader;
import com.mvvm.BR;
import com.mvvm.R;
import com.mvvm.bean.GankBean;
import com.mvvm.databinding.FragmentHomeBinding;
import com.mvvm.ui.adapter.GankGrilAdapter;
import com.mvvm.viewmodel.HomeViewModel;

/**
 * @Description:
 * @Author: xuwenlong
 * @Email: xuwenlong @ebrun.com
 * @Version:
 * @Create: 2019/1/24 0024
 * @Modify:
 */



public class HomeFragment extends BaseFragment<FragmentHomeBinding,HomeViewModel> {

    private GankGrilAdapter mAdapter;
    private int page;


    @Override
    protected void initData() {
        mViewModel.getGankInit().observe(HomeFragment.this, new Observer<Resource<GankBean>>() {
            @Override
            public void onChanged(@Nullable Resource<GankBean> gankBeanResource) {
                page=1;
                showData(gankBeanResource,true);
            }
        });

    }

    private void showData(Resource<GankBean> gankBeanResource, boolean isRefresh) {
        gankBeanResource.handle(new Resource.OnHandleCallback<GankBean>() {
            @Override
            public void onSuccess(GankBean data) {
                page++;
                if(isRefresh){
                    mAdapter.setNewData(gankBeanResource.data.getResults());
                }else{
                    mAdapter.addData(gankBeanResource.data.getResults());
                }
                mAdapter.loadMoreComplete();
            }
            @Override
            public void onEmpiy() {
                mAdapter.loadMoreEnd();
            }

            @Override
            public void onError() {
                if(!isRefresh){
                    mAdapter.loadMoreFail();
                }
            }

            @Override
            public void onCompleted() {
                if(isRefresh){
                    mBinding.sRefresh.finishRefresh(2000);
                }
            }
        });
    }

    @Override
    protected void initView() {
        StaggeredGridLayoutManager manager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mBinding.rlList.setLayoutManager(manager);
        mAdapter=new GankGrilAdapter();
        mBinding.rlList.setAdapter(mAdapter);
        //加载更多
        mAdapter.setOnLoadMoreListener(()->{
            mViewModel.getEbrunMore(page).observe(HomeFragment.this, new Observer<Resource<GankBean>>() {
                @Override
                public void onChanged(@Nullable Resource<GankBean> gankBeanResource) {
                    showData(gankBeanResource,false);
                }
            });
        },mBinding.rlList);
        //下拉刷新
        mBinding.sRefresh.setOnRefreshListener(refreshLayout -> {
            mAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
            mViewModel.getEbrunRefresh().observe(HomeFragment.this,newData->{
                page=1;
                showData(newData,true);
                mAdapter.setEnableLoadMore(true);
            });
        });
        mBinding.sRefresh.setRefreshHeader(new DeliveryHeader(getActivity()));

    }

    @Override
    protected int initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_home;
    }


    @Override
    protected int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (this.getView() != null)
            this.getView()
                    .setVisibility(menuVisible ? View.VISIBLE : View.GONE);
    }
}
