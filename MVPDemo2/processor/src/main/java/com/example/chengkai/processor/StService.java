package com.example.chengkai.processor;

import com.example.chengkai.processor.rep.BaseRep;
import com.example.chengkai.processor.rep.HelloRep;
import com.example.chengkai.processor.req.HelloReq;
import com.example.chengkai.processor.req.ImgReq;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

/**
 * Created by wangsujuan on 2018/4/5.
 * StWebService的网络请求
 */

public interface StService {

    /**
     * 建立P2P连接后，gc向go发送请求，返回gc的地址
     */
    @POST
    Call<HelloRep> hello(@Body HelloReq helloReq);

    /**
     * 传输图片请求
     */
    @POST
    Call<BaseRep> img(@Body ImgReq imgReq);

    /**
     * 下载图片
     *
     * @param path 发起方的图片的绝对路径
     */
    @GET("img")
    @Streaming
    Call<ResponseBody> fetchImg(@Query("path") String path);
}
