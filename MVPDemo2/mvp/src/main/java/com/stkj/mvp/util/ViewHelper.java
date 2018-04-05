package com.stkj.mvp.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jarrahwu on 16/03/2018.
 */

public class ViewHelper
{

    public static View inflateLayout(LayoutInflater inflater, int res, ViewGroup parent) {
        return inflater.inflate(res, parent, false);
    }

    public static View inflateLayout(Context context, int res, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflateLayout(inflater, res, parent);
    }

    public static View inflateLayout(Context context, int res) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflateLayout(inflater, res, null);
    }

    public static View inflateLayout(LayoutInflater inflater, int res) {
        return inflateLayout(inflater, res, null);
    }
}
