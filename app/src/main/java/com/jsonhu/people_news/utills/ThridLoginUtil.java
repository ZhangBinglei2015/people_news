package com.jsonhu.people_news.utills;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * Created by Administrator on 2016/1/22.
 */
public class ThridLoginUtil {
    private UMShareAPI mShareAPI;
    private Activity mContext;
    private UMAuthListener listener;
    public ThridLoginUtil(Activity context,UMShareAPI mShareAPI,UMAuthListener listener){
        this.mShareAPI=mShareAPI;
        this.mContext=context;
        this.listener=listener;
    }
    private void thirdLogin(SHARE_MEDIA platform){
        mShareAPI.doOauthVerify(mContext, platform, listener);

        UMAuthListener umAuthListener = new UMAuthListener() {
            @Override
            public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
                Toast.makeText(mContext, "Authorize succeed", Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onError(SHARE_MEDIA platform, int action, Throwable t) {
                Toast.makeText(mContext, "Authorize fail", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(SHARE_MEDIA platform, int action) {
                Toast.makeText(mContext, "Authorize cancel", Toast.LENGTH_SHORT).show();
            }
        };
    }
}
