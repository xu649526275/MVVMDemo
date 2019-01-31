/*
 *  Copyright ® 2018.   All right reserved.
 *
 *  Last modified 18-7-9 上午10:38
 *
 *
 */

package com.arms.mvvm.utils.cache;

import java.io.Closeable;
import java.io.IOException;

/**
 * The type Close utils.
 *
 * @Description: 供 {@link CacheUtils}的工具;
 * @Author: xuwenlong
 * @Email: xuwenlong @ebrun.com
 * @Version: V4.4.3
 * @Create: 2017 /9/26
 * @Modify:
 */
public final class CloseUtils {
    private CloseUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 关闭IO
     *
     * @param closeables closeables
     */
    public static void closeIO(Closeable... closeables) {
        if (closeables == null) {
            return;
        }
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 安静关闭IO
     *
     * @param closeables closeables
     */
    public static void closeIOQuietly(Closeable... closeables) {
        if (closeables == null) {
            return;
        }
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException ignored) {
                }
            }
        }
    }
}
