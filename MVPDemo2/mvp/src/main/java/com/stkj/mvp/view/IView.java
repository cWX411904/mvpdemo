package com.stkj.mvp.view;

import android.app.Activity;
import android.content.Context;

/**
 * Created by jarrahwu on 16/03/2018.
 */

public interface IView {

    /**
     * 如果view 是Activity 返回对应的activity, 否则 null
     * @return
     */
    Activity getActivity();

    /**
     * 返回 context
     * @return
     */
    Context getContext();

    /**
     * 如果有相关的activity 或者 fragment 或者 view 创建的时候传递的参数, 需要用到可以返回, 否则 null
     * @return
     */
    Object getParams();

    /**
     * 设置view 的生命周期, 以及各种交互触发的回调监听
     * @param listener
     */
    void setViewListener(IViewListener listener);
}
