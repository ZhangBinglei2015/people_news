package com.jsonhu.people_news.activity;

import android.os.Bundle;

import com.jsonhu.people_news.R;
import com.jsonhu.people_news.base.BaseActivity;
import com.jsonhu.people_news.widget.TopBarView;

/**
 * Created by Administrator on 2016/1/21.
 */
public class SettingAct extends BaseActivity {
    private TopBarView topBarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_setting);
        initView();
    }
    public void initView(){
        topBarView=(TopBarView)findViewById(R.id.topBar);
        topBarView.setActivity(this);
        topBarView.setTitle("设置");
    }
}
