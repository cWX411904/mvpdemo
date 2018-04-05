package com.example.wangsujuan.mvpdemo1.presenter;

import com.example.wangsujuan.mvpdemo1.bean.Girl;
import com.example.wangsujuan.mvpdemo1.model.GirlModelImpl;
import com.example.wangsujuan.mvpdemo1.model.IGirlModel;
import com.example.wangsujuan.mvpdemo1.view.IGirlView;

import java.util.List;

/**
 * Created by wangsujuan on 2018/4/5.
 */

public class GirlPresenter<T extends IGirlView> extends BasePresenter<T> {

    IGirlModel girlModel = new GirlModelImpl();

    public void fetch() {
        if (mViewRef.get() != null) {
            mViewRef.get().showLoading();
            if (girlModel != null) {
                girlModel.loadGirl(new IGirlModel.GirlOnLoadListener() {
                    @Override
                    public void onComplete(List<Girl> girls) {
                        if (mViewRef.get() != null) {
                            mViewRef.get().showGirls(girls);
                        }
                    }
                });
            }
        }
    }
}
