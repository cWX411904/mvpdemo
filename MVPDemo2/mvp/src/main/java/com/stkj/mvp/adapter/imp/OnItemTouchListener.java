package com.stkj.mvp.adapter.imp;

import android.view.MotionEvent;
import android.view.View;

import com.stkj.mvp.adapter.holder.ViewHolder;


/**
 *
 * item的触摸回调
 */
public interface OnItemTouchListener {
    boolean onItemTouch(ViewHolder helper, View childView, MotionEvent event, int position);
}
