package com.jsonhu.people_news.fragment;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.jsonhu.people_news.R;
import com.jsonhu.people_news.activity.LoginAct;
import com.jsonhu.people_news.activity.SettingAct;


/**
 * Created by Administrator on 2016/1/21.
 */
public class MinePager extends BaseFragment implements View.OnClickListener {
    public static String FRAGMENT_TAG = MinePager.class.getSimpleName();
    private RelativeLayout rel_title,rel_setting;

    @Override
    protected void lazyLoad() {

    }

    @Override
    public View initView(LayoutInflater inflater) {
        rootView = inflater.inflate(R.layout.act_mine_pager, null);
        rel_title=(RelativeLayout)rootView.findViewById(R.id.rel_title);
        rel_setting=(RelativeLayout)rootView.findViewById(R.id.rel_setting);

        rel_title.setOnClickListener(this);
        rel_setting.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void loadData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rel_title:
                intentTo(LoginAct.class);
                break;
            case R.id.rel_setting:
                intentTo(SettingAct.class);
                break;
        }
    }
    public void intentTo(Class activity){
        Intent intent =new Intent(this.getActivity(),activity);

        startActivity(intent);
    }
}

