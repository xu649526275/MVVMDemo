package com.zhy.wanandroid.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.arms.mvvm.base.BaseFragment;
import com.arms.mvvm.utils.ImageLoader;
import com.arms.mvvm.utils.MLog;
import com.arms.mvvm.utils.Resource;
import com.arms.mvvm.utils.Status;
import com.arms.mvvm.view.recycler.DividerItemDecoration;
import com.arms.mvvm.view.refresnheader.DropBoxHeader;
import com.arms.mvvm.view.refresnheader.WaveSwipeHeader;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.wanandroid.BR;
import com.zhy.wanandroid.R;
import com.zhy.wanandroid.bean.BannerListBean;
import com.zhy.wanandroid.bean.BannerViewBean;
import com.zhy.wanandroid.bean.WanBean;
import com.zhy.wanandroid.databinding.FragmentWanBinding;
import com.zhy.wanandroid.ui.adapter.WanAdapter;
import com.zhy.wanandroid.viewmodel.WanViewModel;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * @Description:
 * @Author: xuwenlong
 * @Email: xuwenlong @ebrun.com
 * @Version:
 * @Create: 2019/1/24 0024
 * @Modify:
 */

public class WanFragment extends BaseFragment<FragmentWanBinding,WanViewModel> {


    private WanAdapter mAdapter;
    private int mPage;
    private boolean isEnd;
    private BGABanner mBanner;
    private List<BannerListBean.BannerBean> mBannerBean;
    @Override
    protected void initData() {
        mViewModel.getArticleInit().observe(WanFragment.this, data->{
            mPage=0;
            showData(data,true);
            Log.d("DaLong_Main返回",data.status.name());
        });
        mViewModel.getBannerInit().observe(WanFragment.this,data->{
            if(data.status==Status.SUCCESS){

                showBanner(data);
            }
        });
    }



    @Override
    protected void initView() {
        mAdapter=new WanAdapter();
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        mBinding.rlList.setLayoutManager(layoutManager);
        mBinding.rlList.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(()->{
            if(isEnd){
                mAdapter.loadMoreEnd();
                return;
            }
            mViewModel.getArticleList(mPage).observe(WanFragment.this,listData->{
                showData(listData,false);
            });
        },mBinding.rlList);
        mBinding.sRefresh.setOnRefreshListener(refreshLayout->{
            mPage=0;
            mViewModel.getArticleList(mPage).observe(WanFragment.this,listData->{
                showData(listData,true);
            });
        });
        mBinding.sRefresh.setRefreshHeader(new DropBoxHeader(getActivity()));

        View view=LayoutInflater.from(getActivity()).inflate(R.layout.header_banner,null);
        mBanner=view.findViewById(R.id.bbBanner);
        mBanner.setAdapter(new BGABanner.Adapter<LinearLayout,String>() {
            @Override
            public void fillBannerItem(BGABanner banner, LinearLayout itemView, @Nullable String model, int position) {
                SimpleDraweeView imageView=itemView.findViewById(R.id.sdv_item_fresco_content);
                ImageLoader.setFrescoImgUrl(imageView,model);
            }
        });
        mAdapter.addHeaderView(view);
    }

    @Override
    protected int initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_wan;
    }



    private void showData(Resource<WanBean> gankBeanResource, boolean isRefresh) {
        gankBeanResource.handle(new Resource.OnHandleCallback<WanBean>() {
            @Override
            public void onSuccess(WanBean data) {
                mPage=data.getData().getCurPage();
                if(isRefresh){
                    mAdapter.setNewData(gankBeanResource.data.getData().getDatas());
                }else{
                    mAdapter.addData(gankBeanResource.data.getData().getDatas());
                }
                isEnd=data.getData().isOver();//获得是否可以加载更多
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
                    mBinding.sRefresh.finishRefresh();
                }
            }
        });
    }



    private void showBanner(Resource<BannerListBean> data) {
        mBannerBean=data.data.getData();
        List<String> urls=new ArrayList<>();
        List<String> titls=new ArrayList<>();
        for(BannerListBean.BannerBean bean:mBannerBean){
            urls.add(bean.getImagePath());
            titls.add(bean.getTitle());
        }

        mBanner.setAutoPlayAble(true);
        mBanner.setData(R.layout.item_banner,urls,titls);
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
