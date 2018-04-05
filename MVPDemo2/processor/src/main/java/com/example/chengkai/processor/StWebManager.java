package com.example.chengkai.processor;

import android.content.Context;
import android.util.Log;

import com.example.chengkai.processor.model.DeviceModel;
import com.stkj.mvp.processor.IProcessCallback;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wangsujuan on 2018/4/5.
 */

public class StWebManager {

    private static volatile StWebManager INSTANCE = null;
    private Map<String, DeviceModel> deviceMap = new HashMap<>();
    private IProcessCallback mImgCallBack = null;
    private Context mContext = null;

    /**
     * 加入连接设备的数据
     * @param devices
     */
    public void addDevice(DeviceModel devices) {
        this.deviceMap.put(devices.getName(), devices);
    }

    /**
     * 根据设备名字返回设备数据
     * @param name
     * @return
     */
    public DeviceModel getDeviceByName(String name) {
        DeviceModel resultModel = null;
        if (this.deviceMap.containsKey(name)) {
            resultModel = this.deviceMap.get(name);
        }
        if (resultModel == null) {
            Log.e("stkj", "找不到此device" + name);
        }
        return resultModel;
    }

    /**
     * 获取GO设备的数据
     * @return
     */
    public DeviceModel getGoDevice() {
        DeviceModel resultModel = new DeviceModel();
        for (String key : this.deviceMap.keySet()) {
            if (this.deviceMap.get(key).isGo()) {
                resultModel = this.deviceMap.get(key);
            }
        }
        return resultModel;
    }

    public int getDevicesSize() {
        return this.deviceMap.size();
    }

    public void clearDevices() {
        this.deviceMap.clear();
    }

    /**
     * 获取网络通信
     *
     * @param address 通信目录的ip
     * @return
     */
    public StService getNetWork(String address) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + address + ":" + WebInfo.DEFAULT_PORT + "/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(StService.class);
    }

    /**
     * 获取文件的输入流
     *
     * @param path 文件位置
     * @return 目标文件的输入流，如果文件不存在，返回空
     * @throws IOException
     */
    public InputStream getFileIn(String path) throws IOException {
        File file = new File(path);

        if (!file.exists()) {
            return null;
        }
        InputStream inputStream = new FileInputStream(file);
        return inputStream;
    }

    public void init(Context context) {
        this.mContext = context;
    }

    /**
     * 清除StWebManager的数据
     */
    public void clean() {
        this.mContext = null;
        this.deviceMap.clear();
    }

    /**
     * 当服务器收到img请求后，回调给本地下载图片
     */
    public IProcessCallback getmImgCallBack() {
        return mImgCallBack;
    }

    public void setmImgCallBack(IProcessCallback mImgCallBack) {
        this.mImgCallBack = mImgCallBack;
    }

    public String getLocalPath(String url) {
        if ( !url.contains("path=") )
            return null;
        String path = url.split("path=")[1];
        return path;
    }

    /**
     * 把ResponseBody的数据写入文件
     *
     * @param targetPath 写入的目标文件
     * @param body       数据源
     */
    public boolean writeResponseBodyToDisk(String targetPath, ResponseBody body) {
        try
        {
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(targetPath);

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try
            {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while ( true )
                {
                    int read = inputStream.read(fileReader);

                    if ( read == -1 )
                    {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d("Jerry", "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch ( IOException e )
            {
                return false;
            } finally
            {
                if ( inputStream != null )
                {
                    inputStream.close();
                }

                if ( outputStream != null )
                {
                    outputStream.close();
                }
            }
        } catch ( IOException e )
        {
            return false;
        }
    }

    public static StWebManager getInstance() {
        if ( INSTANCE == null )
        {
            synchronized (StWebManager.class)
            {
                INSTANCE = new StWebManager();
            }
        }
        return INSTANCE;
    }
}
