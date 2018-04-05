package com.example.chengkai.processor.rep;

/**
 * Created by wangsujuan on 2018/4/5.
 */

public class BaseRep {

    private int status = 200;
    private String msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
