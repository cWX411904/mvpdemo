package com.stkj.mvp.widget.tdialog.listener;

import android.view.View;

import com.stkj.mvp.widget.tdialog.TDialog;
import com.stkj.mvp.widget.tdialog.base.BindViewHolder;


public interface OnViewClickListener {
    void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog);
}
