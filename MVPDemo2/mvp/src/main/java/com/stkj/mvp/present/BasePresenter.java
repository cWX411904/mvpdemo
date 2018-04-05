package com.stkj.mvp.present;

import com.stkj.mvp.processor.IProcessCallback;
import com.stkj.mvp.view.IView;
import com.stkj.mvp.view.IViewListener;

/**
 * Created by jarrahwu on 20/03/2018.
 */

public class BasePresenter<VIEW extends IView> implements IViewListener<VIEW>, IProcessCallback{

    protected VIEW view;

    public BasePresenter(VIEW view) {
        this.view = view;
        this.view.setViewListener(this);
    }

    @Override
    public void onResult(int what, Object result) {

    }

    @Override
    public void onCreated(VIEW view) {

    }

    @Override
    public void onResume(VIEW view) {

    }

    @Override
    public void onPause(VIEW view) {

    }

    @Override
    public void onDestroy(VIEW view) {

    }

    @Override
    public void onInteraction(int what, VIEW view, Object... params) {

    }
}
