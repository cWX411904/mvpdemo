package com.example.wangsujuan.mvpdemo1.presenter;

import java.lang.ref.WeakReference;

/**
 * Created by wangsujuan on 2018/4/5.
 */

public class BasePresenter<T> {

    public WeakReference<T> mViewRef;

    public void attachView(T view) {
        mViewRef = new WeakReference<T>(view);
    }

    public void detachView() {
        mViewRef.clear();
    }
}
