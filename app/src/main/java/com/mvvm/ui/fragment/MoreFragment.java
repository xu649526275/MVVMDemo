package com.mvvm.ui.fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arms.mvvm.base.BaseFragment;
import com.arms.mvvm.utils.MLog;
import com.mvvm.BR;
import com.mvvm.R;
import com.mvvm.databinding.FragmentHomeBinding;
import com.mvvm.databinding.FragmentMoreBinding;
import com.mvvm.viewmodel.MoreViewModel;

/**
 * @Description:
 * @Author: xuwenlong
 * @Email: xuwenlong @ebrun.com
 * @Version:
 * @Create: 2019/1/24 0024
 * @Modify:
 */

public class MoreFragment extends BaseFragment<FragmentMoreBinding,MoreViewModel> {

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mBinding.collapsingToolbar.setExpandedTitleColor(ContextCompat.getColor(getContext(),R.color.white));
        mBinding.collapsingToolbar.setCollapsedTitleTextColor(ContextCompat.getColor(getContext(),R.color.white));
        mBinding.collapsingToolbar.setCollapsedTitleGravity(Gravity.CENTER);
        mBinding.ivAbout.setImageResource(R.drawable.bg);
        mBinding.tv.setText(Html.fromHtml(getString(R.string.about_desc)));
        mBinding.tv.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    protected int initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_more;
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
