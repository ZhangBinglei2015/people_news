package com.jsonhu.people_news.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jsonhu.people_news.R;
import com.jsonhu.people_news.base.BaseActivity;
import com.jsonhu.people_news.widget.InputView;
import com.jsonhu.people_news.widget.TopBarView;

/**
 * Created by Administrator on 2016/1/25.
 */
public class PeoplePassLoginActivity extends BaseActivity implements InputView.TextIsNull {
    private TopBarView topBarView;
    private InputView input_user,input_pwd;
    private Button btn_login;
    private boolean userIsNull=true;
    private boolean pwdIsNull=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_people_pass_login);
        initView();
    }
    public void initView(){
        btn_login=(Button)findViewById(R.id.btn_login);
        topBarView=(TopBarView)findViewById(R.id.topBar);
        topBarView.setActivity(this);
        topBarView.setTitle("登录");

        input_user=(InputView)findViewById(R.id.input_user);
        input_user.setListener(0, this);
        input_user.setEditHint("请输入手机号/邮箱");
        input_user.setIcon(R.mipmap.icon_user);
        input_user.isPassLayout(1);
        input_pwd=(InputView)findViewById(R.id.input_pwd);
        input_pwd.setListener(1, this);
        input_pwd.setEditHint("请输入密码");
        input_pwd.setIcon(R.mipmap.icon_password);
        input_pwd.isPassLayout(2);

        btn_login.setOnClickListener(this);
    }

    @Override
    public void textIsNullListener(int tag, boolean isNull) {
        switch (tag){
            case 0:
                userIsNull=isNull;
                break;
            case 1:
                pwdIsNull=isNull;
                break;
        }
        if (userIsNull==false&&pwdIsNull==false&&userIsNull==false){
            setBtnBg(btn_login,0);
        }else{
            setBtnBg(btn_login,1);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }
}
