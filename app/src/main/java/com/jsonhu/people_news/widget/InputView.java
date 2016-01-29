package com.jsonhu.people_news.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jsonhu.people_news.R;

/**
 * Created by Administrator on 2016/1/26.
 */
public class InputView extends RelativeLayout {
    public EditText editText;
    private ImageView iv_input, iv_del;
    private TextView is_vis;
    private boolean is_visbble = false;
    private boolean isPassLayout = false;
    private RelativeLayout rel_vis;
    private TextIsNull listener;
    private int tag;
    private int layoutType = 0;

    public InputView(Context context) {
        super(context);
    }

    public InputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.input_layout, this, true);
        editText = (EditText) this.findViewById(R.id.edit);
        iv_input = (ImageView) this.findViewById(R.id.iv_input);
        rel_vis = (RelativeLayout) this.findViewById(R.id.rel_vis);
        iv_del = (ImageView) this.findViewById(R.id.iv_del);
        is_vis = (TextView) this.findViewById(R.id.is_vis);
        iv_del.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
            }
        });
        is_vis.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (is_visbble == true) {
                    is_vis.setText("隐藏");
                    is_visbble = false;
                    editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    is_vis.setText("显示");
                    is_visbble = true;
                    editText.setTransformationMethod(PasswordTransformationMethod.getInstance()); //设置为密码输
                }
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String edt = editText.getText().toString();
                if ("".equals(edt)) {
                    listener.textIsNullListener(tag, true);
                } else {
                    listener.textIsNullListener(tag, false);
                }
                if (charSequence.length() > 0 && layoutType !=0) {
                    iv_del.setVisibility(View.VISIBLE);
                } else {
                    iv_del.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void setEditHint(String hint) {
        editText.setHint(hint);
    }

    public void setIcon(int id) {
        iv_input.setBackground(getResources().getDrawable(id));
    }

    public void isPassLayout(int layoutType) {
        this.layoutType = layoutType;
        if (layoutType==0){//可隐藏显示的密码输入
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance()); //设置为密码输
            iv_del.setVisibility(View.GONE);
            rel_vis.setVisibility(View.VISIBLE);
        }else if(layoutType==1){//普通显示
            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            rel_vis.setVisibility(View.GONE);
        }else{//不可隐藏显示的密码输入
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance()); //设置为密码输
            rel_vis.setVisibility(View.GONE);
        }
//        if (isPassLayout == true) {
//            editText.setTransformationMethod(PasswordTransformationMethod.getInstance()); //设置为密码输
//            iv_del.setVisibility(View.GONE);
//            rel_vis.setVisibility(View.VISIBLE);
//        } else {
//            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//            rel_vis.setVisibility(View.GONE);
//        }
    }

    public void setListener(int tag, TextIsNull listener) {
        this.listener = listener;
        this.tag = tag;
    }

    public interface TextIsNull {
        public void textIsNullListener(int tag, boolean isNull);
    }
}
