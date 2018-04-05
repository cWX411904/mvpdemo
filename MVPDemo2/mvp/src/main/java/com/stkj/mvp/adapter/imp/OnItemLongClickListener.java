package com.stkj.mvp.adapter.imp;

import android.view.View;
import android.view.ViewGroup;

import com.stkj.mvp.adapter.holder.ViewHolder;


/**
 *
 * item的长按回调
 */
public interface OnItemLongClickListener {
    /**
     * @param helper
     * @param parent   如果是RecyclerView的话，parent为空
     * @param itemView
     * @param position
     */
    boolean onItemLongClick(ViewHolder helper, ViewGroup parent, View itemView, int position);
}
