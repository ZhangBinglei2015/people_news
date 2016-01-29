package com.jsonhu.people_news.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsonhu.people_news.R;
import com.jsonhu.people_news.base.BaseActivity;
import com.jsonhu.people_news.utills.TimeCount;
import com.jsonhu.people_news.widget.InputView;
import com.jsonhu.people_news.widget.TopBarView;

/**
 * Created by Administrator on 2016/1/25.
 */
public class FindPwNextActivity extends BaseActivity implements InputView.TextIsNull {
    private TextView userName;
    private Button btn_commit,btn_send;
    private TopBarView topBarView;
    private TimeCount mTime;
    private InputView input_ver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_find_pwd_next);
        initView();
    }
    private void initView(){
        topBarView = (TopBarView) findViewById(R.id.topBar);
        userName=(TextView)findViewById(R.id.userName);
        btn_commit=(Button)findViewById(R.id.btn_commit);
        btn_send=(Button)findViewById(R.id.btn_send);

        input_ver=(InputView)findViewById(R.id.input_ver);
        input_ver.setListener(0, this);
        input_ver.setEditHint("请输入验证码");
        input_ver.setIcon(R.mipmap.icon_input);
        input_ver.isPassLayout(1);

        btn_commit.setOnClickListener(this);
        btn_send.setOnClickListener(this);
        topBarView.setActivity(this);
        topBarView.setTitle("密码找回");
        userName.setText(getIntent().getStringExtra("user"));
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        String verifica=input_ver.editText.getText().toString();
        switch (v.getId()){
            case R.id.btn_commit:
                if (!"".equals(verifica)){
                    Intent intent = new Intent(this, ResetPswActivity.class);
                    startActivity(intent);
                    finish();
                }
//                verification.setText("");
                break;
            case R.id.btn_send:
                if (mTime == null) {
                    mTime = new TimeCount(btn_send);// 构造CountDownTimer对象
                }
                mTime.start();// 开始计时
                break;
        }
    }

    @Override
    public void textIsNullListener(int tag, boolean isNull) {
        if (isNull==false){
            setBtnBg(btn_commit,0);
        }else{
            setBtnBg(btn_commit,1);
        }
    }
}
