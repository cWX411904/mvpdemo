package com.stkj.mvp.present.helper;

/**
 * Created by jarrahwu on 20/03/2018.
 *
 * 方便处理interaction 的 参数
 *
 */
public class Params {

    public static <T extends Object> T index(int index, Object[] params) {
        if (params == null) {
            throw new RuntimeException("params is null!");
        }

        T ret = (T) params[index];
        return ret;
    }

    public static boolean isEmpty(Object[] params) {
        return params == null || params.length == 0;
    }

    public static <T extends Object> T first(Object[] params) {
        return index(0, params);
    }

    public static <T extends Object> T last(Object[] params) {
        return index(params.length - 1, params);
    }

    public static int length(Object[] params) {
        return params.length;
    }
}
