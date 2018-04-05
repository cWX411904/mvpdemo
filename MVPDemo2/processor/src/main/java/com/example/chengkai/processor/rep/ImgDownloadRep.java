package com.example.chengkai.processor.rep;

import com.example.chengkai.processor.req.ImgReq;

/**
 * Created by wangsujuan on 2018/4/5.
 */

public class ImgDownloadRep extends ImgReq {

    private String localPath;

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }
}
