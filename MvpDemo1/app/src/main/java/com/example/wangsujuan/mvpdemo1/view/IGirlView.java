package com.example.wangsujuan.mvpdemo1.view;

import com.example.wangsujuan.mvpdemo1.bean.Girl;

import java.util.List;

/**
 * Created by wangsujuan on 2018/4/5.
 */

public interface IGirlView {

    void showLoading();

    void showGirls(List<Girl> girls);
}
