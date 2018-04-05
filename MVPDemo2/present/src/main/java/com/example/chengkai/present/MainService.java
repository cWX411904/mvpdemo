package com.example.chengkai.present;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;


import com.example.chengkai.processor.StWebService;
import com.example.chengkai.processor.WebInfo;

import java.io.IOException;

public class MainService extends Service {
    private StWebService mStWebService = null;//这个是HttpServer的句柄。


    public MainService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        //在这里开启HTTP Server。
        mStWebService = new StWebService(WebInfo.DEFAULT_PORT);
        try {
            mStWebService.start();
            Log.e("yzy", "开启服务");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        //在这里关闭HTTP Server
        if(mStWebService != null) {
            mStWebService.stop();
            Log.e("yzy", "销毁服务");
        }
    }
}
