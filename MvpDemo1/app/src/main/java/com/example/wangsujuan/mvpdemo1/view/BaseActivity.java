package com.example.wangsujuan.mvpdemo1.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.wangsujuan.mvpdemo1.presenter.BasePresenter;

/**
 * Created by wangsujuan on 2018/4/5.
 */

public abstract class BaseActivity<V, T extends BasePresenter<V>> extends AppCompatActivity{

    public T girlPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        girlPresenter = createPresenter();
        girlPresenter.attachView((V)this);
    }

    protected abstract T createPresenter();

    @Override
    protected void onDestroy() {
        girlPresenter.detachView();
        super.onDestroy();
    }
}
