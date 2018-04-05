package com.stkj.mvp.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


import com.stkj.mvp.view.IView;
import com.stkj.mvp.view.IViewListener;

import java.lang.ref.WeakReference;

/**
 * Created by jarrahwu on 16/03/2018.
 */

public class BaseActivity extends AppCompatActivity implements IView
{

    private IViewListener mListener;

    private WeakReference<Activity> mActivityRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (mActivityRef.get() == null)
        mActivityRef = new WeakReference<Activity>(this);
        onCreateImpl(savedInstanceState);
        setPresenter();
        if (mListener != null) {
            mListener.onCreated(this);
        }
    }

    //for override
    protected void setPresenter() {

    }

    protected void onCreateImpl(Bundle savedInstanceState) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mListener != null) {
            mListener.onResume(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mListener != null) {
            mListener.onPause(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mListener != null) {
            mListener.onDestroy(this);
        }
    }

    @Override
    public Activity getActivity() {
        return mActivityRef.get();
    }

    @Override
    public Context getContext() {
        return this.getApplicationContext();
    }

    @Override
    public Object getParams() {
        return null;
    }

    @Override
    public void setViewListener(IViewListener listener) {
        mListener = listener;
    }

    public void notifyInteraction(int what, Object... params) {
        if (mListener != null) {
            mListener.onInteraction(what, this, params);
        }
    }
}
