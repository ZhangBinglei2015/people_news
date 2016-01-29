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
public class MailRegisterActivity extends BaseActivity implements InputView.TextIsNull {
    private Button btn_register;
    private TextView phone_register, tv_agree;
    private TopBarView topBarView;
    private InputView input_phone, input_pwd;
    private CheckBox checkBox;
    private boolean phoneIsNull = true;
    private boolean pwdIsNull = true;
    private TimeCount mTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_mail_register);
        initView();
    }

    public void initView() {
        topBarView = (TopBarView) findViewById(R.id.topBar);
        btn_register = (Button) findViewById(R.id.btn_register);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        tv_agree = (TextView) findViewById(R.id.tv_agree);
        topBarView.setActivity(this);
        topBarView.setTitle("邮箱注册");
        input_phone = (InputView) findViewById(R.id.input_phone);
        input_phone.setListener(0, this);
        input_phone.setEditHint("请输入邮箱");
        input_phone.setIcon(R.mipmap.icon_phone);
        input_phone.isPassLayout(1);
        input_pwd = (InputView) findViewById(R.id.input_pwd);
        input_pwd.setListener(1, this);
        input_pwd.setEditHint("请输入6-16位数字或字母密码");
        input_pwd.setIcon(R.mipmap.icon_password);
        input_pwd.isPassLayout(0);
        phone_register = (TextView) findViewById(R.id.phone_register);
        btn_register.setOnClickListener(this);
        phone_register.setOnClickListener(this);
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
            case R.id.phone_register:
                Intent intent = new Intent(this, PhoneRegisterActivity.class);
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
        }
        judgeState();
    }

    public void judgeState() {
        if (phoneIsNull == false && pwdIsNull == false && checkBox.isChecked()) {
            setBtnBg(btn_register, 0);
        } else {
            setBtnBg(btn_register, 1);
        }
    }
}
