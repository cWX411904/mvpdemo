package com.example.chengkai.processor.model;

/**
 * Created by wangsujuan on 2018/4/5.
 */

public class DeviceModel {

    private boolean isGo = false;
    private String address;
    private String name;

    public boolean isGo() {
        return isGo;
    }

    public void setGo(boolean go) {
        isGo = go;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
