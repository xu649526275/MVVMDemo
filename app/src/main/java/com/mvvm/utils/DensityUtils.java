/*
 *  Copyright ® 2018.   All right reserved.
 *
 *  Last modified 18-4-23 下午1:26
 *
 *
 */

package com.mvvm.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.WindowManager;

import com.arms.mvvm.Common;


/**
 * The type Density utils.
 *
 * @Description: 常用单位转换的辅助类
 * @Author: xuwenlong
 * @Email: xuwenlong @ebrun.com
 * @Version: V4.4.3
 * @Create: 2017 /9/26
 * @Modify:
 */
public class DensityUtils {

    private DensityUtils() {
        throw new UnsupportedOperationException("单位工具类不能初始化对象");
    }

    /**
     * dp转px
     *
     * @param dpVal the dp val
     * @return int int
     */
    public static int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, Resources.getSystem().getDisplayMetrics());
    }

    /**
     * sp转px
     *
     * @param spVal the sp val
     * @return int int
     */
    public static int sp2px(float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, Resources.getSystem().getDisplayMetrics());
    }

    /**
     * px转dp
     *
     * @param pxVal the px val
     * @return float float
     */
    public static float px2dp(float pxVal) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (pxVal / scale);
    }



    /**
     * Gets screenwidth.
     *
     * @return screenwidth screenwidth
     */
    public static int getScreenwidth() {
        try {
            DisplayMetrics dm = new DisplayMetrics();
            WindowManager wm = (WindowManager) Common.getApplication().getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(dm);
            int widthPixels = dm.widthPixels;
            int heightPixels = dm.heightPixels;

            return widthPixels;
        } catch (Exception e) {
            Log.w("","fail to get the resolution", e);
        }
        return -1;
    }

    /**
     * px转sp
     *
     * @param pxVal the px val
     * @return float float
     */
    public static float px2sp(float pxVal) {
        return (pxVal / Resources.getSystem().getDisplayMetrics().scaledDensity);
    }

    /**
     * Dips to pixels int.
     *
     * @param context the context
     * @param dips    the dips
     * @return the int
     */
    public static int dipsToPixels(Context context, float dips) {
        if (context == null){
            context = Common.getApplication();
        }else {
            context = context.getApplicationContext();
        }
        final float scale = context.getResources().getDisplayMetrics().density;
        int px = (int) (dips * scale + 0.5f);
        return px;
    }


}
