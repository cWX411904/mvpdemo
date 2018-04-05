package com.example.chengkai.processor;

import android.util.Log;

import com.example.chengkai.processor.model.DeviceModel;
import com.example.chengkai.processor.rep.BaseRep;
import com.example.chengkai.processor.rep.HelloRep;
import com.example.chengkai.processor.rep.ImgDownloadRep;
import com.example.chengkai.processor.req.HelloReq;
import com.example.chengkai.processor.req.ImgReq;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by wangsujuan on 2018/4/5.
 */

public class StWebService extends NanoHTTPD{

    public StWebService(int port) {
        super(port);
    }

    @Override
    public Response serve(IHTTPSession session) {
        Log.i("stkj", "method: " + session.getMethod());

        Gson gson = new Gson();

        if (session.getMethod().equals(Method.POST)) {
            Map<String, String> files = new HashMap<>();
            try {
                session.parseBody(files);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ResponseException e) {
                e.printStackTrace();
            }
            String body = files.get("postData");
            Log.i("stkj", "body = " + body);
            if (session.getUri().equals("/hello")) {
                HelloReq helloReq = gson.fromJson(body,HelloReq.class);
                DeviceModel deviceModel = CoverManager.getInstance().conver(helloReq);
                //存储GO信息
                StWebManager.getInstance().addDevice(deviceModel);

                HelloRep helloRep = new HelloRep();
                helloRep.setGcAddress(session.getRemoteIpAddress());
                helloRep.setGcName(helloReq.getGcName());
                helloRep.setStatus(200);
                String repResult = gson.toJson(helloRep);
                //存储GC信息
                StWebManager.getInstance().addDevice(CoverManager.getInstance().conver(helloRep));

                return newFixedLengthResponse(Response.Status.OK, "text/html", repResult);
            } else if (session.getUri().equals("/img")) {
                //处理传输图片的请求
                ImgReq imgReq = gson.fromJson(body, ImgReq.class);
                if (StWebManager.getInstance().getmImgCallBack() != null) {
                    ImgDownloadRep downloadRep = CoverManager.getInstance().conver(imgReq);
                    StWebManager.getInstance().getmImgCallBack().onResult(WebInfo.ACTION_IMG_RESULT, downloadRep);
                } else {
                    BaseRep baseRep = new BaseRep();
                    baseRep.setStatus(409);
                    baseRep.setMsg("图片回调为空，回调失败");
                    return newFixedLengthResponse(Response.Status.OK, "text/html", gson.toJson(baseRep));
                }
                return newFixedLengthResponse(Response.Status.OK, "text/html", gson.toJson(new BaseRep()));
            }
        }
        else if (session.getMethod().equals(Method.GET)) {
            //下载图片
            if (session.getUri().equals("/img")) {
                InputStream mbuffer = null;
                String queryParams = session.getQueryParameterString();
                String path = null;
                try {
                    path = java.net.URLDecoder.decode(queryParams.replace("path",""), "utf-8").trim();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                File file = new File(path);
                if ( !file.exists() )
                {
                    BaseRep baseRep = new BaseRep();
                    baseRep.setMsg("未找到图片");
                    baseRep.setStatus(400);
                    return newFixedLengthResponse(Response.Status.OK, "text/html", gson.toJson(baseRep));
                }
                long fileSize = 0;
                try {
                    mbuffer = StWebManager.getInstance().getFileIn(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return newFixedLengthResponse(Response.Status.OK, "image/png", mbuffer, fileSize);
            }
        }
        return super.serve(session);
    }

    @Override
    public void start() throws IOException {
        super.start();
    }
}
