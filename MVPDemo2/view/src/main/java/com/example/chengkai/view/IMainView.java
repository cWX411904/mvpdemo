package com.example.chengkai.view;

import android.net.wifi.p2p.WifiP2pDevice;

import com.stkj.mvp.processor.IProcessCallback;
import com.stkj.mvp.view.IView;

import java.util.List;

/**
 * Created by wangsujuan on 2018/4/5.
 */

public interface IMainView extends IView, IProcessCallback{

    void loadWifiP2pDevices(List<WifiP2pDevice> list);

    class Action {
        public static final int ON_REFRESH_CLICK = 0x1010;
        public static final int ON_ITEM_CLICK = ON_REFRESH_CLICK + 1;//点击某个ITEM
        public static final int HELP_WISH_IMPL_CLICK = ON_ITEM_CLICK + 1;
        public static final int SCAN_BUTTON_CLICK = HELP_WISH_IMPL_CLICK + 1;//扫描按钮点击
        public static final int REMOVE_BUTTON_CLICK = SCAN_BUTTON_CLICK + 1;//断开按钮点击
        public static final int CHOOSE_BUTTON_CLICK = REMOVE_BUTTON_CLICK + 1;//选择图片按钮点击
        public static final int CHOOSE_IMAGE_CALLBACK_SUCCESS = CHOOSE_BUTTON_CLICK + 1;//选择图片成功，由View层返回给Presenter层
    }

    class Code {
        public static final int DISCOVER_SUCCESS = 0;//扫描成功
        public static final int DISCOVER_FAILED = 1;//扫描失败
        public static final int CONNECTED_SUCCESS = 2;//连接成功
        public static final int CONNECTED_FAILED = 3;//连接失败
        public static final int REQUEST_IMG_CODE = 4;//选择图片请求码
        public static final int DOWNLOAD_IMG_SUCC = 5;//下载图片成功
        public static final int DOWNLOAD_IMG_FAIL = 6;//下载图片失败
        public static final int DISCONNECT_SUCC = 7;//断开连接成功
        public static final int DISCONNECT_FAIL = 8;//断开连接失败
    }
}
