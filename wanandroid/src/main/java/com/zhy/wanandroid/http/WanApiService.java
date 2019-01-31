package com.zhy.wanandroid.http;


import com.zhy.wanandroid.bean.BannerListBean;
import com.zhy.wanandroid.bean.WanBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @Description:
 * @Author: xuwenlong
 * @Email: xuwenlong @ebrun.com
 * @Version:
 * @Create: 2018/7/24 0024
 * @Modify:
 */

public interface WanApiService {
    /**
     * 获取文章列表
     * @param curPage 页码从0开始
     * @return
     */
    @GET("article/list/{curPage}/json")
    Observable<WanBean> getArticlesList(@Path("curPage") int curPage);


    /**
     * 首页Banner
     * @return
     */
    @GET("banner/json")
    Observable<BannerListBean> getBanner();

}
