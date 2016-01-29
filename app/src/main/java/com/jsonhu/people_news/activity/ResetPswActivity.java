package com.jsonhu.people_news.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jsonhu.people_news.R;
import com.jsonhu.people_news.base.BaseActivity;
import com.jsonhu.people_news.fragment.MinePager;
import com.jsonhu.people_news.widget.InputView;
import com.jsonhu.people_news.widget.TopBarView;

/**
 * Created by Administrator on 2016/1/25.
 */
public class ResetPswActivity extends BaseActivity implements InputView.TextIsNull {
    private TopBarView topBarView;
    private Button btn_complete;
    private InputView input_pwd_one,input_pwd_two;
    private boolean pwdOneIsNull=true;
    private boolean pwdTwoIsNull=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_reset_pwd);
        initView();
    }

    private void initView() {
        topBarView = (TopBarView) findViewById(R.id.topBar);
        btn_complete = (Button) findViewById(R.id.btn_complete);

        input_pwd_one=(InputView)findViewById(R.id.input_pwd_one);
        input_pwd_one.setListener(0, this);
        input_pwd_one.setEditHint("请输入6-16位数字或字母密码");
        input_pwd_one.setIcon(R.mipmap.icon_input);
        input_pwd_one.isPassLayout(1);

        input_pwd_two=(InputView)findViewById(R.id.input_pwd_two);
        input_pwd_two.setListener(1, this);
        input_pwd_two.setEditHint("确认密码");
        input_pwd_two.setIcon(R.mipmap.icon_password);
        input_pwd_two.isPassLayout(1);


        btn_complete.setOnClickListener(this);

        topBarView.setActivity(this);
        topBarView.setTitle("密码重置");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        String pwdOne=input_pwd_one.editText.getText().toString();
        String pwdTwo=input_pwd_two.editText.getText().toString();
        if ("".equals(pwdOne)||"".equals(pwdTwo)){
            showToast("请输入密码");
        }else if (!pwdOne.equals(pwdTwo)){
            showToast("两次输入不一致");
        }else{
            showToast("设置成功");
           finish();
        }
    }

    @Override
    public void textIsNullListener(int tag, boolean isNull) {
        switch (tag){
            case 0:
                pwdOneIsNull=isNull;
                break;
            case 1:
                pwdTwoIsNull=isNull;
                break;
        }
        if (pwdOneIsNull==false&&pwdTwoIsNull==false){
            setBtnBg(btn_complete,0);
        }else{
            setBtnBg(btn_complete,1);
        }
    }
}
