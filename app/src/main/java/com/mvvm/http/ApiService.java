package com.mvvm.http;


import com.mvvm.bean.GankBean;

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

public interface ApiService {




    /**
     * 妹纸列表
     */
    @GET("data/福利/{num}/{page}")
    Observable<GankBean> getGirlList(@Path("num") int num, @Path("page") int page);

}
