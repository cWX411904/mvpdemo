package com.stkj.mvp.present.helper;

/**
 * Created by jarrahwu on 19/03/2018.
 */

public interface IDataBinder<UI, BIZ> {

    void addBind(UI u, BIZ b);

    void removeBindByBiz(BIZ b);

    void removeBindByUI(UI u);

    BIZ bizFromUI(UI ui);

    UI uiFromBiz(BIZ biz);

    void uiArray(UI[] array);

    void bizArray(BIZ[] array);

    int size();

    void clear();

    void release();
}
