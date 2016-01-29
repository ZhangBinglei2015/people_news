package com.jsonhu.people_news.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.jsonhu.people_news.R;
import com.jsonhu.people_news.utills.Utils;

/**
 * Created by admin on 2016/1/21.
 */
public class ChannelWindow extends Dialog implements View.OnClickListener{
    private Activity context;
    public ChannelWindow(Activity context){
        super(context,R.style.Anim);
        this.context = context;
        View v = View.inflate(context, R.layout.act_channel,null);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics displayMetrics = Utils.getDisplaySize(context);
        lp.height = displayMetrics.heightPixels;
        lp.width = displayMetrics.widthPixels;
        dialogWindow.setAttributes(lp);
        this.setContentView(v);
        initView(v);
    }
    private void initView(View v) {
        ImageView icon_add = (ImageView) v.findViewById(R.id.act_channel_icon_add);
        icon_add.setOnClickListener(this);
        rotate135(icon_add);
    }
    private void rotate135(final ImageView iv){

        Animation operatingAnim = AnimationUtils.loadAnimation(context,R.anim.iv_rotate);
        operatingAnim.setInterpolator(new LinearInterpolator());
        operatingAnim.setFillAfter(true);
        iv.startAnimation(operatingAnim);

    }

    @Override
    public void onClick(View v) {
        this.dismiss();
    }
}
