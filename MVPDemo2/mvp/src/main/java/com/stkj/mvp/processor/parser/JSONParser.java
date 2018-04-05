package com.stkj.mvp.processor.parser;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jarrahwu on 19/03/2018.
 *
 *  处理服务器json 返回的解析
 *
 */

public interface JSONParser<T> {

    /**
     * json string -> 对应的实体类
     * @param jsonString
     * @return
     */
    T fromJSON(String jsonString);

    /**
     * json 对象 -> 对应的实体类
     * @param jsonObject
     * @return
     */
    T fromJSON(JSONObject jsonObject);

    class Helper {

        private static final String TAG = "Helper";
        private static final boolean DEBUG = true;

        public static JSONObject fromString(String str) {
            try {
                JSONObject jo = new JSONObject(str);
                return jo;
            } catch (JSONException e) {
                if(DEBUG) Log.e(TAG, "JSONParser."+"str can not be json", e);
                return null;
            }
        }
    }

}
