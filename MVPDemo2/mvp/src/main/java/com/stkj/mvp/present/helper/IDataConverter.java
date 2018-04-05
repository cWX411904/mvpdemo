package com.stkj.mvp.present.helper;

/**
 * Created by jarrahwu on 19/03/2018.
 */

public interface IDataConverter<UI, BIZ> {

    UI fromBiz(BIZ biz);

    BIZ fromUi(UI ui);

}
