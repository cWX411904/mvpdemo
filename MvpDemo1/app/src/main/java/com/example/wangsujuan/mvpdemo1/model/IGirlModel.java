package com.example.wangsujuan.mvpdemo1.model;

import com.example.wangsujuan.mvpdemo1.bean.Girl;

import java.util.List;

/**
 * Created by wangsujuan on 2018/4/5.
 */

public interface IGirlModel {
    void loadGirl(GirlOnLoadListener girlOnLoadListener);

    interface GirlOnLoadListener {
        void onComplete(List<Girl> girls);
    }
}
