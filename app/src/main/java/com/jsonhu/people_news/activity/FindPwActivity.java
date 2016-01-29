package com.jsonhu.people_news.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.jsonhu.people_news.R;
import com.jsonhu.people_news.base.BaseActivity;
import com.jsonhu.people_news.widget.InputView;
import com.jsonhu.people_news.widget.TopBarView;

/**
 * Created by Administrator on 2016/1/25.
 */
public class FindPwActivity extends BaseActivity implements InputView.TextIsNull {
    private TopBarView topBarView;
    private Button btn_next;
    private InputView input_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_find_pw);
        initView();
    }

    public void initView() {
        topBarView = (TopBarView) findViewById(R.id.topBar);
        btn_next = (Button) findViewById(R.id.btn_next);

        input_user=(InputView)findViewById(R.id.input_user);
        input_user.setListener(0, this);
        input_user.setEditHint("请输入登录手机号/邮箱");
        input_user.setIcon(R.mipmap.icon_mail);
        input_user.isPassLayout(1);

        btn_next.setOnClickListener(this);
        topBarView.setActivity(this);
        topBarView.setTitle("密码找回");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        String userName=input_user.editText.getText().toString();
        switch (v.getId()){
            case R.id.btn_next:
                if (!"".equals(userName)){
                    Intent intent = new Intent(this, FindPwNextActivity.class);
                    intent.putExtra("user",userName);
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }

    @Override
    public void textIsNullListener(int tag, boolean isNull) {

        if (isNull==false){
            setBtnBg(btn_next,0);
        }else{
            setBtnBg(btn_next,1);
        }
    }
}
