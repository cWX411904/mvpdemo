package com.example.chengkai.view;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pDevice;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.stkj.mvp.adapter.base.AdapterForRecyclerView;
import com.stkj.mvp.adapter.holder.ViewHolder;
import com.stkj.mvp.adapter.holder.ViewHolderForRecyclerView;
import com.stkj.mvp.adapter.imp.OnItemClickListener;
import com.stkj.mvp.base.BaseActivity;
import com.stkj.mvp.widget.recycleview.CustomRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangsujuan on 2018/4/5.
 */

public class HomeActivity extends BaseActivity implements IMainView, View.OnClickListener {

    public static final String TAG = "stkj";

    private TextView mTv;
    private CustomRecyclerView mRecyclerView;
    private Button mBtn_scan, mBtn_remove, mBtn_choose;

    private List<WifiP2pDevice> mDataList = new ArrayList<>();
    private MyAdapter mAdapter;
    private boolean isConnected = false;
    private MyHandler mHandler;

    @Override
    protected void onCreateImpl(Bundle savedInstanceState) {
        super.onCreateImpl(savedInstanceState);
        setContentView(R.layout.activity_home);

        mHandler = new MyHandler();

        mTv = findViewById(R.id.tv);
        mRecyclerView = findViewById(R.id.recycler_view);
        mTv.setText("请点击扫描按钮开始扫描设备。。。");
        mBtn_scan = findViewById(R.id.scan);
        mBtn_remove = findViewById(R.id.remove);
        mBtn_choose = findViewById(R.id.choose);
        mBtn_scan.setOnClickListener(this);
        mBtn_remove.setOnClickListener(this);
        mBtn_choose.setOnClickListener(this);

        mAdapter = new MyAdapter(getActivity(), mDataList);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onResult(int what, Object result) {
        mHandler.sendEmptyMessage(what);
    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Code.DISCOVER_SUCCESS:
                    mTv.setText("扫描设备成功");
                    break;
                case Code.DISCOVER_FAILED:
                    mTv.setText("扫描失败");
                    break;
                case Code.CONNECTED_FAILED:
                    mTv.setText("连接失败");
                    break;
                case Code.CONNECTED_SUCCESS:
                    mTv.setText("连接成功");
                    isConnected = true;
                    break;
                case Code.DISCONNECT_SUCC:
                    mTv.setText("断开成功");
                    break;
                case Code.DISCONNECT_FAIL:
                    mTv.setText("断开失败");
                    isConnected = false;
                    break;
                case Code.DOWNLOAD_IMG_SUCC:
                    Toast.makeText(HomeActivity.this, "图片下载成功", Toast.LENGTH_SHORT).show();
                    break;
                case Code.DOWNLOAD_IMG_FAIL:
                    Toast.makeText(HomeActivity.this, "图片下载失败", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    mTv.setText("开始扫描设备，请耐心等待。。。");
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult方法响应了，view将Intent数据回调给Present层");
        //Present层点击某个图片后，View层的这个方法被响应，再将Intent数据给Present层
        if (requestCode == Code.REQUEST_IMG_CODE && resultCode == RESULT_OK) {
            notifyInteraction(Action.CHOOSE_IMAGE_CALLBACK_SUCCESS, data);
        }
    }

    private class MyAdapter extends AdapterForRecyclerView<WifiP2pDevice> {

        public MyAdapter(Context context, List<WifiP2pDevice> data) {
            super(context, data, R.layout.item_wifip2pdevice);
        }

        @Override
        public void convert(ViewHolderForRecyclerView helper, WifiP2pDevice item, int position) {
            helper.setText(R.id.device_name, item.deviceName);
            helper.setText(R.id.device_address, item.deviceAddress);

            helper.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(ViewHolder helper, ViewGroup parent, View itemView, int position) {
                    notifyInteraction(Action.ON_ITEM_CLICK, getItem(position));
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.scan) {
            Log.i(TAG, "扫描按钮被点击了");
            mTv.setText("开始扫描设备，请耐心等待。。。");
            notifyInteraction(Action.SCAN_BUTTON_CLICK, v);
        } else if (i == R.id.remove) {
            Log.i(TAG, "断开按钮被点击了");
            mTv.setText("开始断开设备了。。。");
            notifyInteraction(Action.REMOVE_BUTTON_CLICK, v);
        } else if (i == R.id.choose) {
            Log.i(TAG, "选择图片按钮被点击了");
            if (isConnected) {
                Log.i(TAG, "连接成功了，开始跳转到手机系统的图片管理目录");
                //如果连接成功了，才会响应点击图片的按钮给Presenter层
                notifyInteraction(Action.CHOOSE_BUTTON_CLICK, v);
                isConnected = false;
            }

        }
    }

    @Override
    public void loadWifiP2pDevices(List<WifiP2pDevice> wifiP2pDeviceList) {
        if (wifiP2pDeviceList != null) {
            Log.i(TAG, "loadWifiP2pDevices : " + wifiP2pDeviceList.size());
            for (WifiP2pDevice wifiP2pDevice : wifiP2pDeviceList) {
                Log.i(TAG, "wifiP2pDevice.deviceName =  " + wifiP2pDevice.deviceName);
                Log.i(TAG, "wifiP2pDevice.deviceAddress =  " + wifiP2pDevice.deviceAddress);
            }
        }
        this.mDataList = wifiP2pDeviceList;
        mAdapter.setData(mDataList);
    }
}
