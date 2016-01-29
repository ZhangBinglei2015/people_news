package com.jsonhu.people_news.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;
import com.jsonhu.people_news.R;
import com.jsonhu.people_news.interfaces.ShareClickListener;
import com.jsonhu.people_news.utills.LogUtil;
import com.jsonhu.people_news.view.ShareDialog;
import com.jsonhu.people_news.view.StepsviewDialog;
import cn.pedant.SweetAlert.SweetAlertDialog;
/**
 * Created by lugang on 2016/1/20.
 */
public class BaseActivity extends FragmentActivity implements View.OnClickListener {

    public static Context context;
    private SweetAlertDialog mLoadingDialog;
    private ShareDialog shareDialog;
    private StepsviewDialog stepsviewDialog;
    private WebSettings settings;

    /**
     * 控制WebView字体
     */
    public static final int TEXTSIZE_SMALLEST = 0;
    public static final int TEXTSIZE_SMALLER = 25;
    public static final int TEXTSIZE_NORMAL = 50;
    public static final int TEXTSIZE_LARGER = 75;
    public static final int TEXTSIZE_LARGEST = 100;

    /**
     * 用来控制WebView中字体的大小
     */
    private int fontSize = 1;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        context = this;
    }

    /**
     * 查找View
     *
     * @param id   控件的id
     * @param <VT> View类型
     * @return
     */
    protected <VT extends View> VT getViewById(@IdRes int id) {
        return (VT) findViewById(id);
    }

    @Override
    public void onClick(View v) {

    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setBtnBg(Button button, int state) {
        if (state == 0) {
            button.setBackground(getDrawable(R.drawable.btn_normal));
            button.setTextColor(getResources().getColor(R.color.white));
        } else {
            button.setBackground(getDrawable(R.drawable.btn_disable));
            button.setTextColor(getResources().getColor(R.color.gray));
        }
    }

    public void initWebViewArgs(WebView webView) {
        webView.setWebChromeClient(new MyWebChromeClient());

        settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(false);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
    }

    /**
     * 设置WebView的字体大小
     * @param size
     */
    public void changeWebViewFontSize(int size){
        switch (size){

            case TEXTSIZE_SMALLEST:
                settings.setTextSize(WebSettings.TextSize.SMALLEST);
                break;

            case TEXTSIZE_SMALLER:
                settings.setTextSize(WebSettings.TextSize.SMALLER);
//                settings.setTextZoom(75);

                break;

            case TEXTSIZE_NORMAL:
                settings.setTextSize(WebSettings.TextSize.NORMAL);
                break;

            case TEXTSIZE_LARGER:
                settings.setTextSize(WebSettings.TextSize.LARGER);
                break;

            case TEXTSIZE_LARGEST:
                settings.setTextSize(WebSettings.TextSize.LARGEST);
                break;

            default:
                break;
        }
    }



    /**
     * 为了捕捉js的 alert在webview不能够直接的显示 需要一个webchromeclient容器
     *
     * @author Administrator
     */
    public class MyWebChromeClient extends WebChromeClient {

        @Override
        public boolean onJsAlert(WebView view, String url, String message,
                                 final JsResult result) {
            new AlertDialog.Builder(BaseActivity.this)
                    .setTitle("提醒")
                    .setMessage(message)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            result.confirm();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            result.cancel();
                        }
                    }).create().show();

            return true;
        }
    }


    /**
     * 显示加载的dialog
     */
    public void showLoadingDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            mLoadingDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
            mLoadingDialog.setCancelable(false);
            mLoadingDialog.setTitleText("数据加载中...");
        }
        mLoadingDialog.show();
    }

    /**
     * 隐藏加载的dialog
     */
    public void dismissLoadingDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

    /**
     * 展开分享的dialog
     */
    public void showShareDialog() {
        shareDialog = new ShareDialog(this);
        shareDialog.setOnShareListener(new ShareClickListener() {
            @Override
            public void onMessageClick() {
            }

            @Override
            public void onTimeLineClick() {
            }
        });

        shareDialog.show();
    }


    public void popStepViewDialog(){
        stepsviewDialog = new StepsviewDialog(this);
        stepsviewDialog.setSeekBarChanged(new StepsviewDialog.SeekBarChangedListener() {
            @Override
            public void onProgressChanged(int progress) {
                changeWebViewFontSize(progress);

                LogUtil.i("Base",progress+"");
            }
        });
        stepsviewDialog.show();
    }

}
