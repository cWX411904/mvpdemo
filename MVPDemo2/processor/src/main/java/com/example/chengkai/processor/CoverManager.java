package com.example.chengkai.processor;

import com.example.chengkai.processor.model.DeviceModel;
import com.example.chengkai.processor.rep.HelloRep;
import com.example.chengkai.processor.rep.ImgDownloadRep;
import com.example.chengkai.processor.req.HelloReq;
import com.example.chengkai.processor.req.ImgReq;

/**
 * Created by wangsujuan on 2018/4/5.
 * 数据转换器
 */

public class CoverManager {

    private static volatile CoverManager INSTANCE = null;

    public DeviceModel conver(HelloReq helloReq) {
        DeviceModel deviceModel = new DeviceModel();
        deviceModel.setGo(helloReq.isGo());
        deviceModel.setAddress(helloReq.getAddress());
        deviceModel.setName(helloReq.getName());
        return deviceModel;
    }

    public DeviceModel conver(HelloRep helloRep) {
        DeviceModel deviceModel = new DeviceModel();
        deviceModel.setGo(false);
        deviceModel.setAddress(helloRep.getGcAddress());
        deviceModel.setName(helloRep.getGcName());
        return deviceModel;
    }

    public ImgDownloadRep conver(ImgReq imgReq) {
        String localPath = StWebManager.getInstance().getLocalPath(imgReq.getUrl());
        ImgDownloadRep imgDownloadRep = new ImgDownloadRep();
        imgDownloadRep.setUrl(imgReq.getUrl());
        imgDownloadRep.setLocalPath(localPath);
        imgDownloadRep.setName(imgReq.getName());
        return imgDownloadRep;
    }

    public static CoverManager getInstance() {
        if ( INSTANCE == null )
        {
            synchronized (CoverManager.class)
            {
                INSTANCE = new CoverManager();
            }
        }
        return INSTANCE;
    }
}
