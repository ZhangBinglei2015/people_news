package com.jsonhu.people_news.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.jsonhu.people_news.R;
import com.jsonhu.people_news.base.BaseActivity;
import com.jsonhu.people_news.utills.TimeCount;
import com.jsonhu.people_news.widget.InputView;
import com.jsonhu.people_news.widget.TopBarView;

/**
 * Created by Administrator on 2016/1/26.
 */
public class PhoneRegisterActivity extends BaseActivity implements InputView.TextIsNull {
    private Button btn_send, btn_register;
    private TextView emil_login, tv_agree;
    private TopBarView topBarView;
    private InputView input_phone, input_pwd, input_ver;
    private CheckBox checkBox;
    private boolean phoneIsNull = true;
    private boolean pwdIsNull = true;
    private boolean verIsNull = true;
    private TimeCount mTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_phone_register);
        initView();
    }

    public void initView() {
        topBarView = (TopBarView) findViewById(R.id.topBar);
        btn_register = (Button) findViewById(R.id.btn_register);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        tv_agree = (TextView) findViewById(R.id.tv_agree);
        topBarView.setActivity(this);
        topBarView.setTitle("手机注册");
        input_phone = (InputView) findViewById(R.id.input_phone);
        input_phone.setListener(0, this);
        input_phone.setEditHint("请输入11位手机号");
        input_phone.setIcon(R.mipmap.icon_phone);
        input_phone.isPassLayout(1);
        input_pwd = (InputView) findViewById(R.id.input_pwd);
        input_pwd.setListener(1, this);
        input_pwd.setEditHint("请输入6-16位数字或字母密码");
        input_pwd.setIcon(R.mipmap.icon_password);
        input_pwd.isPassLayout(0);
        input_ver = (InputView) findViewById(R.id.input_ver);
        input_ver.setListener(2, this);
        input_ver.setEditHint("请输入验证码");
        input_ver.setIcon(R.mipmap.icon_input);
        input_ver.isPassLayout(1);
        btn_send = (Button) findViewById(R.id.btn_send);
        emil_login = (TextView) findViewById(R.id.emil_login);
        btn_register.setOnClickListener(this);
        btn_send.setOnClickListener(this);
        emil_login.setOnClickListener(this);
        checkBox.setOnClickListener(this);
        tv_agree.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_register:
//              String phone=input_phone.editText.getText().toString();
//              String pwd=input_pwd.editText.getText().toString();
//              String ver=input_ver.editText.getText().toString();
                break;
            case R.id.btn_send:
                if (mTime == null) {
                    mTime = new TimeCount(btn_send);// 构造CountDownTimer对象
                }
                mTime.start();// 开始计时
                break;
            case R.id.emil_login:
                Intent intent = new Intent(this, MailRegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.checkBox:
                judgeState();
                break;
            case R.id.tv_agree:
                Intent intent_agr = new Intent(this, UserAgreementActivity.class);
                startActivity(intent_agr);
                break;
        }
    }

    //设置button 背景色
    @Override
    public void textIsNullListener(int tag, boolean isNull) {
        switch (tag) {
            case 0:
                phoneIsNull = isNull;
                break;
            case 1:
                pwdIsNull = isNull;
                break;
            case 2:
                verIsNull = isNull;
                break;
        }
        judgeState();
    }
    public void judgeState(){
        if (phoneIsNull == false && pwdIsNull == false && verIsNull == false&&checkBox.isChecked()) {
            setBtnBg(btn_register, 0);
        } else {
            setBtnBg(btn_register, 1);
        }
    }
}
