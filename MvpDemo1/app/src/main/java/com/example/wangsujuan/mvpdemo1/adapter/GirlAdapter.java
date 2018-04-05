package com.example.wangsujuan.mvpdemo1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wangsujuan.mvpdemo1.R;
import com.example.wangsujuan.mvpdemo1.bean.Girl;

import java.util.List;

/**
 * Created by wangsujuan on 2018/4/5.
 */

public class GirlAdapter extends BaseAdapter{

    private LayoutInflater inflater;
    private List<Girl> girls;

    public GirlAdapter(Context context, List<Girl> girls) {
        inflater = LayoutInflater.from(context);
        this.girls = girls;
    }

    @Override
    public int getCount() {
        return girls.size();
    }

    @Override
    public Object getItem(int position) {
        return girls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item, null);
        Girl girl = girls.get(position);
        ImageView iv_icon = view.findViewById(R.id.iv_icon);
        iv_icon.setImageResource(girl.icon);

        TextView tv_like = view.findViewById(R.id.tv_like);
        tv_like.setText("喜欢程度：" + girl.like);

        TextView tv_style = (TextView) view.findViewById(R.id.tv_style);
        tv_style.setText(girl.style);
        return view;
    }
}
