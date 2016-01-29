package com.jsonhu.people_news.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.share.ShareApi;
import com.jsonhu.people_news.R;
import com.jsonhu.people_news.base.BaseActivity;
import com.jsonhu.people_news.widget.InputView;
import com.jsonhu.people_news.widget.TopBarView;
import com.umeng.socialize.UMShareAPI;

/**
 * Created by Administrator on 2016/1/21.
 */
public class LoginAct extends BaseActivity implements InputView.TextIsNull {
    private TopBarView topBarView;
    private RelativeLayout rel_people_login,rel_qq_login,rel_wx_login,rel_sina_login;
    private TextView find_psw,tv_register;
    private Button btn_login;
    private UMShareAPI mShareAPI;
    private InputView input_user,input_pwd;
    private boolean userIsNull=true;
    private boolean pwdIsNull=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        initView();

    }
    public void initView(){
        topBarView=(TopBarView)findViewById(R.id.topBar);
        topBarView.setActivity(this);
        topBarView.setTitle("登录");
        rel_people_login=(RelativeLayout)findViewById(R.id.rel_people_login);
        rel_qq_login=(RelativeLayout)findViewById(R.id.rel_qq_login);
        rel_wx_login=(RelativeLayout)findViewById(R.id.rel_wx_login);
        rel_sina_login=(RelativeLayout)findViewById(R.id.rel_sina_login);
        btn_login=(Button)findViewById(R.id.btn_login);
        find_psw=(TextView)findViewById(R.id.find_psw);
        tv_register=(TextView)findViewById(R.id.tv_register);
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


        rel_people_login.setOnClickListener(this);
        rel_qq_login.setOnClickListener(this);
        rel_wx_login.setOnClickListener(this);
        rel_sina_login.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        find_psw.setOnClickListener(this);
        tv_register.setOnClickListener(this);

//        mShareAPI = UMShareAPI.get(this);
    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        mShareAPI.onActivityResult(requestCode, resultCode, data);
//    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.rel_people_login:
                intentTo(PeoplePassLoginActivity.class);
                break;
            case R.id.rel_qq_login:
                break;
            case R.id.rel_wx_login:
                break;
            case R.id.rel_sina_login:
                break;
            case R.id.find_psw:
                intentTo(FindPwActivity.class);
                break;
            case R.id.tv_register:
                intentTo(PhoneRegisterActivity.class);
                break;
            case R.id.btn_login:
                break;
        }
    }
    public void intentTo(Class activity){
        Intent intent =new Intent(this,activity);
        startActivity(intent);
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
        if (userIsNull==false&&pwdIsNull==false){
            setBtnBg(btn_login,0);
        }else{
            setBtnBg(btn_login,1);
        }
    }
}
