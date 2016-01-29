package com.jsonhu.people_news.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jsonhu.people_news.R;
import com.jsonhu.people_news.adapter.CommentListAdapter;
import com.jsonhu.people_news.base.BaseActivity;
import com.jsonhu.people_news.utills.LogUtil;

/**
 * Created by lugang on 2016/1/26.
 */
public class WebViewDetailActivity extends BaseActivity {



    private XRecyclerView xRecyclerView;
    private static final String TAG = "WebViewDetailActivity";
    WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_detail);

        initView();
    }

    private void initView() {

        ImageView iv_back = (ImageView) findViewById(R.id.iv_webview_news_detail_back);
        iv_back.setOnClickListener(this);

        ImageView iv_share = (ImageView) findViewById(R.id.iv_webview_share);
        iv_share.setOnClickListener(this);

        ImageView iv_webview_font_size = (ImageView) findViewById(R.id.iv_webview_font_size);
        iv_webview_font_size.setOnClickListener(this);

        TextView tv_webview_comment = (TextView) findViewById(R.id.tv_webview_comment);
        tv_webview_comment.setOnClickListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        xRecyclerView = (XRecyclerView) findViewById(R.id.webview_detail_recyclerview);
        xRecyclerView.setLoadingMoreEnabled(false);
        xRecyclerView.setPullRefreshEnabled(false);
        xRecyclerView.setLayoutManager(layoutManager);

        View headView = View.inflate(this, R.layout.public_webview_detail_recyclerview_head, null);
        initWeb(headView);
        xRecyclerView.addHeaderView(headView);

        xRecyclerView.setAdapter(new CommentListAdapter(this));

    }

    private void initWeb(View headView) {

        mWebView = (WebView) headView.findViewById(R.id.webView_detail);
        initWebViewArgs(mWebView);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                dismissLoadingDialog();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                showLoadingDialog();
            }
        });

        mWebView.loadUrl("http://www.baidu.com");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {

            case R.id.iv_webview_weibo:
                showShareDialog();
                break;

            case R.id.iv_webview_wx:
                showShareDialog();
                break;

            case R.id.iv_webview_timeline:
                showShareDialog();
                break;

            case R.id.iv_webview_font_size:
                popStepViewDialog();
                break;

            case R.id.iv_webview_news_detail_back:
                finish();
                break;

            case R.id.iv_webview_share:
                showShareDialog();
                break;

            case R.id.tv_webview_comment:

                startActivity(new Intent(getApplicationContext(),CommentDetailActivity.class));

                break;

            default:
                break;
        }
    }

}
