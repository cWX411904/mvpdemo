package com.example.chengkai.present;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Environment;
import android.util.Log;

import com.example.chengkai.processor.StWebManager;
import com.example.chengkai.processor.WebInfo;
import com.example.chengkai.processor.model.DeviceModel;
import com.example.chengkai.processor.rep.BaseRep;
import com.example.chengkai.processor.rep.HelloRep;
import com.example.chengkai.processor.req.HelloReq;
import com.example.chengkai.processor.req.ImgReq;
import com.example.chengkai.view.IMainView;
import com.google.gson.Gson;
import com.stkj.mvp.present.BasePresenter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.chengkai.view.IMainView.Action.CHOOSE_BUTTON_CLICK;
import static com.example.chengkai.view.IMainView.Action.CHOOSE_IMAGE_CALLBACK_SUCCESS;
import static com.example.chengkai.view.IMainView.Action.ON_ITEM_CLICK;
import static com.example.chengkai.view.IMainView.Action.REMOVE_BUTTON_CLICK;
import static com.example.chengkai.view.IMainView.Action.SCAN_BUTTON_CLICK;
import static com.example.chengkai.view.IMainView.Code.CONNECTED_SUCCESS;
import static com.example.chengkai.view.IMainView.Code.DISCONNECT_FAIL;
import static com.example.chengkai.view.IMainView.Code.DISCONNECT_SUCC;
import static com.example.chengkai.view.IMainView.Code.DOWNLOAD_IMG_FAIL;
import static com.example.chengkai.view.IMainView.Code.DOWNLOAD_IMG_SUCC;
import static com.example.chengkai.view.IMainView.Code.REQUEST_IMG_CODE;

/**
 * Created by wangsujuan on 2018/4/5.
 */

public class MainPresent extends BasePresenter<IMainView> implements WifiP2pManager.ConnectionInfoListener, WifiP2pManager.PeerListListener{

    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;
    private Context mContext;
    private WiFiDirectReceiver mReceiver;
    private IntentFilter mFilter;
    private StWebManager mWebManager;
    private Intent mSeiviceIntent;
    private String mGoHost;
    private String mGcHost;
    private String mSelfHost;
    private String mTargetHost;
    private WifiP2pInfo mInfo;

    public MainPresent(IMainView view) {
        super(view);
        mContext = view.getContext();
    }

    @Override
    public void onCreated(IMainView view) {
        super.onCreated(view);
        init();
    }

    private void init() {
        mSeiviceIntent = new Intent(mContext, MainService.class);
        mManager = (WifiP2pManager) mContext.getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(mContext, mContext.getMainLooper(), null);//会触发WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION广播
        mReceiver = new WiFiDirectReceiver(mManager, mChannel, MainPresent.this);
        mFilter = new IntentFilter();
        mFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        mFilter.addAction(WifiP2pManager.WIFI_P2P_DISCOVERY_CHANGED_ACTION);
        mContext.registerReceiver(mReceiver, mFilter);
        StWebManager.getInstance().setmImgCallBack(this);
    }

    @Override
    public void onInteraction(int what, IMainView view, Object... params) {
        super.onInteraction(what, view, params);
        switch (what) {
            //扫描按钮点击
            case SCAN_BUTTON_CLICK:
                discoverPeers();
                break;
            //断开按钮点击
            case REMOVE_BUTTON_CLICK:
                disConnect();
                break;
            //选择图片按钮点击
            case CHOOSE_BUTTON_CLICK:
                chooseImg();
                break;
            //选择图片成功，由View层返回给Presenter层
            case CHOOSE_IMAGE_CALLBACK_SUCCESS:
                onChooseImg((Intent) params[0]);
                break;
            //item点击
            case ON_ITEM_CLICK:
                connect((WifiP2pDevice) params[0]);
                break;
            default:
        }
    }

    public void discoverPeers() {
        mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                view.onResult(IMainView.Code.DISCOVER_SUCCESS,0);
            }

            @Override
            public void onFailure(int reason) {
                view.onResult(IMainView.Code.DISCOVER_FAILED,reason);
            }
        });
    }

    public void connect(WifiP2pDevice vDevice) {
        WifiP2pConfig mConfig = new WifiP2pConfig();
        mConfig.deviceAddress = vDevice.deviceAddress;
        mManager.connect(mChannel, mConfig, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(int reason) {
                view.onResult(IMainView.Code.CONNECTED_FAILED,3);
            }
        });
    }

    @Override
    public void onResult(int what, Object result) {
        super.onResult(what, result);
        if (what == 1) {
            ImgReq vImgReq = (ImgReq) result;
            String vUrl = vImgReq.getUrl();
            downloadImg(vImgReq);
        }
    }

    public void disConnect(){
        mManager.removeGroup(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                view.onResult(DISCONNECT_SUCC,DISCONNECT_SUCC);
            }

            @Override
            public void onFailure(int reason) {
                view.onResult(DISCONNECT_FAIL,DISCONNECT_FAIL);
            }
        });
    }

    @Override
    public void onConnectionInfoAvailable(WifiP2pInfo info) {
        mInfo = info;
        if (info != null && info.groupFormed) {
            mGoHost = info.groupOwnerAddress.getHostAddress();
            mWebManager = StWebManager.getInstance();
            mWebManager.init(mContext);
            mContext.startService(mSeiviceIntent);
            view.onResult(CONNECTED_SUCCESS,CONNECTED_SUCCESS);
            if (info.isGroupOwner) {
                //go
                DeviceModel deviceModelGO = new DeviceModel();
                deviceModelGO.setGo(true);
                deviceModelGO.setAddress(mGoHost);
                deviceModelGO.setName(WebInfo.TAG_GO);
                mWebManager.addDevice(deviceModelGO);
            } else {
                //gc
                DeviceModel  deviceModelGC = new DeviceModel();
                deviceModelGC.setGo(false);
                deviceModelGC.setAddress(mGoHost);
                deviceModelGC.setName(WebInfo.TAG_GC);
                mWebManager.addDevice(deviceModelGC);

                sayHello();
            }

        }
    }

    public void chooseImg() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        Intent wrapperIntent = Intent.createChooser(intent, null);
        view.getActivity().startActivityForResult(wrapperIntent, REQUEST_IMG_CODE);
    }

    public void onChooseImg(Intent pIntent) {
        Uri vUri = pIntent.getData();
        String path = UriUtil.getInstance().uri2PathNew(mContext, vUri);
        Log.e("yzy", "path: " + path);

        if (mInfo.isGroupOwner) {
            DeviceModel vModle = StWebManager.getInstance().getDeviceByName(WebInfo.TAG_GC);
            mTargetHost = vModle.getAddress();
            mSelfHost = mGoHost;
            Log.e("yzy", "256------------mTargetHost" + mTargetHost+";mSelfHost"+mSelfHost);
        }

        sendImg(path);

    }

    public void sendImg(String path) {
        String[] vSplit = path.split("/");
        String name = vSplit[vSplit.length - 1];
        ImgReq imgReq = new ImgReq();
        imgReq.setName(name);
        imgReq.setUrl("http://" + mSelfHost + ":" + WebInfo.DEFAULT_PORT + "/img?path=" + path);
        Log.e("yzy", "sendImg: " + imgReq.getUrl());

        mWebManager.getNetWork(mTargetHost).img(imgReq).enqueue(new Callback<BaseRep>() {
            @Override
            public void onResponse(Call<BaseRep> call, Response<BaseRep> response) {
                Gson gson = new Gson();
                Log.e("yzy", "img" + gson.toJson(response.body()));
            }

            @Override
            public void onFailure(Call<BaseRep> call, Throwable t) {
            }
        });
    }

    private void sayHello() {
        HelloReq helloReq = new HelloReq();
        helloReq.setAddress(mGoHost);
        helloReq.setGo(true);
        helloReq.setName(WebInfo.TAG_GO);
        helloReq.setGcName(WebInfo.TAG_GC);

        mWebManager.getNetWork(mGoHost).hello(helloReq).enqueue(new Callback<HelloRep>() {
            @Override
            public void onResponse(Call<HelloRep> call, Response<HelloRep> response) {
                Gson gson = new Gson();
                HelloRep helloRep = response.body();
                mGcHost = helloRep.getGcAddress();

                if (mInfo.isGroupOwner) {
                    mSelfHost = mGoHost;
                    mTargetHost = mGcHost;
                } else {
                    mSelfHost = mGcHost;
                    mTargetHost = mGoHost;
                }
            }

            @Override
            public void onFailure(Call<HelloRep> call, Throwable t) {

            }
        });
    }

    @Override
    public void onPeersAvailable(WifiP2pDeviceList peers) {
        ArrayList<WifiP2pDevice> mList = new ArrayList<>();
        mList.addAll(peers.getDeviceList());
        view.loadWifiP2pDevices(mList);
    }

    public void downloadImg(ImgReq pImgReq) {
        try {
            final URL vURL = new URL(pImgReq.getUrl());
            final String savePath = Environment.getExternalStorageDirectory().getAbsolutePath();

            final String vName = pImgReq.getName();
            Log.e("yzy", "vName-----------: " + vName);
            new Thread() {
                public void run() {
                    InputStream is = null;
                    FileOutputStream os = null;
                    try {
                        HttpURLConnection vConnection = (HttpURLConnection) vURL.openConnection();
//                        vConnection.setDoOutput(true);
                        vConnection.setDoInput(true);
                        is = vConnection.getInputStream();
                        os = new FileOutputStream(new File(savePath, vName));

                        int len;
                        byte[] buffer = new byte[1024];
                        while ((len = is.read(buffer)) != -1) {
                            os.write(buffer, 0, len);
                        }
                    } catch (IOException pE) {
                        pE.printStackTrace();
                    } finally {
                        try {
                            if (is != null) {
                                is.close();
                            }
                            if (os != null) {
                                os.close();
                            }
                            view.onResult(DOWNLOAD_IMG_SUCC, DOWNLOAD_IMG_SUCC);
                        } catch (IOException pE) {
                            view.onResult(DOWNLOAD_IMG_FAIL, DOWNLOAD_IMG_FAIL);
                            pE.printStackTrace();
                        }
                    }
                }
            }.start();
        } catch (MalformedURLException pE) {
            view.onResult(DOWNLOAD_IMG_FAIL, DOWNLOAD_IMG_FAIL);
            pE.printStackTrace();
        }


    }

    @Override
    public void onDestroy(IMainView view) {
        mContext.unregisterReceiver(mReceiver);
        super.onDestroy(view);
    }


}
