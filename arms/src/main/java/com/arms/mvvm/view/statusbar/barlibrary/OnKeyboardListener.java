package com.arms.mvvm.view.statusbar.barlibrary;

/**
 *
 * 软键盘监听
 * Created by Administrator on 2018/4/5 0005.
 */

public interface OnKeyboardListener {

    /**
     * On keyboard change.
     *
     * @param isPopup        the is popup  是否弹出
     * @param keyboardHeight the keyboard height  软键盘高度
     */
    void onKeyboardChange(boolean isPopup, int keyboardHeight);

}
