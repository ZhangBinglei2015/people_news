package com.jsonhu.people_news.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jsonhu.people_news.R;
import com.jsonhu.people_news.base.BaseActivity;
import com.jsonhu.people_news.widget.TopBarView;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2016/1/27.
 */
public class UserAgreementActivity extends BaseActivity {
    private TopBarView topBarView;
    private TextView tv_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_user_agreement);
        initView();
        readFile();
    }
    public void readFile(){
        try {
            // Return an AssetManager instance for your application's package
            InputStream is=this.getClass().getClassLoader().getResourceAsStream("assets/"+"agreement.txt");
            int size = is.available();

            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            // Convert the buffer into a string.
            String text = new String(buffer, "UTF_8");

            // Finally stick the string into the text view.
            tv_content.setText(text);
        } catch (IOException e) {
            // Should never happen!
            throw new RuntimeException(e);
        }
    }
    public void initView(){
        tv_content=(TextView)findViewById(R.id.content);
        topBarView = (TopBarView) findViewById(R.id.topBar);
        topBarView.setActivity(this);
        topBarView.setTitle("用户协议");

    }
}
