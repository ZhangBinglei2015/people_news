package com.jsonhu.people_news.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.jsonhu.people_news.R;
import com.jsonhu.people_news.interfaces.ShareClickListener;
import com.jsonhu.people_news.utills.Utils;


/**
 * Created by Administrator on 2015/8/11.
 */
public class ShareDialog extends Dialog implements View.OnClickListener {

    private View mView;
    private WindowManager.LayoutParams lp;
    private ShareClickListener mShareListener;
    private ImageView iv_message,iv_timeline;

    public ShareDialog(Activity context) {
        super(context, R.style.AnimBottom);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mView = inflater.inflate(R.layout.share_dialog_layout,null);
        this.setCanceledOnTouchOutside(true);
        this.setContentView(mView);
        lp = getWindow().getAttributes();
        lp.gravity = Gravity.BOTTOM;
        lp.width = Utils.getDisplaySize(context).widthPixels;
        lp.height = Utils.getDisplaySize(context).heightPixels * 1/3;
        getWindow().setAttributes(lp);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        initView();
    }

    private void initView() {
        iv_message = (ImageView) mView.findViewById(R.id.iv_message);
        iv_timeline = (ImageView) mView.findViewById(R.id.iv_timeline);

        iv_message.setOnClickListener(this);
        iv_timeline.setOnClickListener(this);
    }

    public void setOnShareListener(ShareClickListener clickListener){
        this.mShareListener = clickListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_message:

                mShareListener.onMessageClick();

                break;

            case R.id.iv_timeline:

                mShareListener.onTimeLineClick();

                break;
        }
    }
}
