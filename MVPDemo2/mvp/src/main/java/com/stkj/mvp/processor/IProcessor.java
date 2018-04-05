package com.stkj.mvp.processor;

/**
 * Created by jarrahwu on 16/03/2018.
 */

public interface IProcessor {

    Object syncExec(int command, Object... params);

    void asyncExec(int command, IProcessCallback callback, Object... params);

}
