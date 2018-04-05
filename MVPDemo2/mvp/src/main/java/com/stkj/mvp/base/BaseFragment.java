package com.stkj.mvp.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.stkj.mvp.view.IView;
import com.stkj.mvp.view.IViewListener;


/**
 * Created by jarrahwu on 16/03/2018.
 */

public class BaseFragment extends Fragment implements IView
{
    protected IViewListener mListener;

    //for override
    protected void instantiatePresenter() {
        throw new RuntimeException(getClass().getSimpleName() + " instantiatePresenter has not implemented!");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instantiatePresenter();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mListener != null) {
            mListener.onCreated(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mListener != null) {
            mListener.onResume(this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mListener != null) {
            mListener.onPause(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mListener != null) {
            mListener.onDestroy(this);
        }
    }

    @Override
    public Context getContext() {
        return this.getActivity().getApplicationContext();
    }

    @Override
    public Object getParams() {
        return getArguments();
    }

    @Override
    public void setViewListener(IViewListener listener) {
        mListener = listener;
    }

    public void sendInteraction(int what, Object... params) {
        if (mListener != null) {
            mListener.onInteraction(what, this, params);
        }
    }
}
