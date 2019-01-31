/*
 *  Copyright ® 2017.   All right reserved.
 *
 *  Last modified 17-2-14 下午2:58
 *
 *
 */

package com.arms.mvvm.utils.rx;

/**
 * @Description: 服务器请求异常
 * @Author: xuyangyang
 * @Email: xuyangyang@ebrun.com
 * @Version: V4.4.0
 * @Create: 2017/11/6 15:02
 * @Modify:
 */
public class ServerException extends Exception {

    /**
     * Instantiates a new Server exception.
     *
     * @param msg
     *         the msg
     */
    public ServerException(String msg){
        super(msg);
    }

}
