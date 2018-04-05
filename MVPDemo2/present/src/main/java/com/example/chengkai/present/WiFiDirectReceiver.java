package com.example.chengkai.present;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;


public class WiFiDirectReceiver extends BroadcastReceiver {

    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;
    private MainPresent mMainPresent;

    public WiFiDirectReceiver() {
    }

    public WiFiDirectReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel, MainPresent pMainPresent) {
        this.mManager = manager;
        this.mChannel = channel;
        this.mMainPresent = pMainPresent;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.e("tag", "===============wifi direct action: " + action);

        switch (action) {
            case WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION://初始化后会收到这个广播
                int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
                if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {//表示状态为开
                    //进行发现设备操作
                    mMainPresent.discoverPeers();
                } else if (state == WifiP2pManager.WIFI_P2P_STATE_DISABLED) {//表示状态为关
                    //不用做什么
                    Log.e("yzy", "onReceive: p2p关闭了");
                }
                break;
            case WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION://发现成功会收到这个广播
                if (null != mManager) {
                    mManager.requestPeers(mChannel, mMainPresent);
                }
                break;
            case WifiP2pManager.WIFI_P2P_DISCOVERY_CHANGED_ACTION://扫描状态发生改变
                int State = intent.getIntExtra(WifiP2pManager.EXTRA_DISCOVERY_STATE, -1);
                if (State == WifiP2pManager.WIFI_P2P_DISCOVERY_STOPPED) {
                    Log.e("yzy", "再次搜索");
                    //在收到停止扫描广播的时候再次进行搜索，以此来保证设备可见性
                    mMainPresent.discoverPeers();
                }
                break;
            case WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION://连接状态改变
                if (mManager != null) {
                    NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);
                    if (networkInfo.isConnected()) {
                        Log.i("yzy", "请求连接着的设备信息");
                        mManager.requestConnectionInfo(mChannel, mMainPresent);
                    }
                }
                break;
            case WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION:

                break;
            default:
        }

    }

}