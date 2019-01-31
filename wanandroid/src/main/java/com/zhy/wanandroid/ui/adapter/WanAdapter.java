package com.zhy.wanandroid.ui.adapter;

import android.support.annotation.Nullable;
import android.text.format.DateUtils;

import com.arms.mvvm.utils.common.TimeUtils;
import com.arms.mvvm.view.baseadapter.BaseQuickAdapter;
import com.arms.mvvm.view.baseadapter.BaseViewHolder;
import com.zhy.wanandroid.R;
import com.zhy.wanandroid.bean.ArticleBean;


public class WanAdapter extends BaseQuickAdapter<ArticleBean,BaseViewHolder> {


    public WanAdapter() {
        super(R.layout.item_wan, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticleBean item) {
        helper.setText(R.id.tvTitle,item.getTitle());
        helper.setText(R.id.tvTime,TimeUtils.getFriendlyTimeSpanByNow(item.getPublishTime()));
        helper.setText(R.id.tvAuthor,item.getAuthor());
        if(item.isCollect()){
            helper.setImageResource(R.id.ivCollect,R.drawable.collected);
        }else{
            helper.setImageResource(R.id.ivCollect,R.drawable.collect);
        }
    }
}
