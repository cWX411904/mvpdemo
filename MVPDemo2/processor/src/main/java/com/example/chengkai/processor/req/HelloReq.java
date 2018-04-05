package com.example.chengkai.processor.req;

/**
 * Created by wangsujuan on 2018/4/5.
 */

public class HelloReq {

    private boolean go = false;
    private String address;
    private String name;
    private String gcName;

    public boolean isGo() {
        return go;
    }

    public void setGo(boolean go) {
        this.go = go;
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

    public String getGcName() {
        return gcName;
    }

    public void setGcName(String gcName) {
        this.gcName = gcName;
    }
}
