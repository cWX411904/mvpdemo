package com.stkj.mvp.adapter.imp;

import android.view.View;
import android.view.ViewGroup;

import com.stkj.mvp.adapter.holder.ViewHolder;


/**
 *
 * item点击回调
 */
public interface OnItemClickListener {
    /**
     * @param helper
     * @param parent   如果是RecyclerView的话，parent为空
     * @param itemView
     * @param position
     */
    void onItemClick(ViewHolder helper, ViewGroup parent, View itemView, int position);
}
