package com.jsonhu.people_news.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jsonhu.people_news.R;
import com.jsonhu.people_news.adapter.CommentListAdapter;
import com.jsonhu.people_news.base.BaseActivity;

/**
 * Created by lugang on 2016/1/29.
 */
public class CommentDetailActivity extends BaseActivity {

    private XRecyclerView xRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_detail);

        initView();
    }

    private void initView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        xRecyclerView = (XRecyclerView) findViewById(R.id.recyclerview_comment_detail);
        xRecyclerView.setLoadingMoreEnabled(false);
        xRecyclerView.setPullRefreshEnabled(false);
        xRecyclerView.setLayoutManager(layoutManager);
        xRecyclerView.setAdapter(new CommentListAdapter(this));

        findViewById(R.id.iv_comment_detail_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
