package com.stkj.mvp.view;

/**
 * Created by jarrahwu on 20/03/2018.
 */

public interface IViewListener<VIEW extends IView> {

    void onCreated(VIEW view);

    void onResume(VIEW view);

    void onPause(VIEW view);

    void onDestroy(VIEW view);

    void onInteraction(int what, VIEW view, Object... params);

}
