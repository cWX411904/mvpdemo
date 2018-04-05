package com.example.wangsujuan.mvpdemo1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.wangsujuan.mvpdemo1.adapter.GirlAdapter;
import com.example.wangsujuan.mvpdemo1.bean.Girl;
import com.example.wangsujuan.mvpdemo1.presenter.GirlPresenter;
import com.example.wangsujuan.mvpdemo1.view.BaseActivity;
import com.example.wangsujuan.mvpdemo1.view.IGirlView;

import java.util.List;

public class MainActivity extends BaseActivity<IGirlView, GirlPresenter<IGirlView>> implements IGirlView{

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listview);
        girlPresenter.fetch();
    }

    @Override
    protected GirlPresenter<IGirlView> createPresenter() {
        return new GirlPresenter();
    }

    @Override
    public void showLoading() {
        Toast.makeText(this, "加载成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showGirls(List<Girl> girls) {
        listView.setAdapter(new GirlAdapter(this, girls));
    }
}
