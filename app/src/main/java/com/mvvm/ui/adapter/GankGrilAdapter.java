package com.mvvm.ui.adapter;
import com.arms.mvvm.view.baseadapter.BaseQuickAdapter;
import com.arms.mvvm.view.baseadapter.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.mvvm.R;
import com.mvvm.bean.GankBean;
import com.mvvm.utils.DensityUtils;
import com.arms.mvvm.utils.ImageLoader;

/**
 * @Description:
 * @Author: xuwenlong
 * @Email: xuwenlong @ebrun.com
 * @Version:
 * @Create: 2019/1/24 0024
 * @Modify:
 */

public class GankGrilAdapter extends BaseQuickAdapter<GankBean.GankItemBean,BaseViewHolder> {

    public GankGrilAdapter() {
        super(R.layout.item_home,null);

    }

    @Override
    protected void convert(BaseViewHolder helper, GankBean.GankItemBean item) {
        SimpleDraweeView imageview=helper.getView(R.id.ivImg);
        ImageLoader.setControllerListener(imageview,item.getUrl(),DensityUtils.getScreenwidth()/2);
    }
}
