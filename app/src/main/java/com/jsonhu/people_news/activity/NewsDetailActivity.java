package com.jsonhu.people_news.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jsonhu.people_news.R;
import com.jsonhu.people_news.adapter.MyAdapter;
import com.jsonhu.people_news.base.BaseActivity;
import com.jsonhu.people_news.factory.FragmentFactory;
import com.jsonhu.people_news.model.RefreshModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lugang on 2016/1/21.
 */
public class NewsDetailActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FrameLayout content;
    private XRecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private ArrayList<String> listData;
    private int refreshTime = 0;
    private int times = 0;
    private static List<ImageView> imageViewList = new ArrayList<ImageView>();
    private static int[] imageIds = new int[]{R.drawable.image01, R.drawable.image02, R.drawable.image03, R.drawable.image04};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        methodtwo();

//        initView();

    }

    private void methodtwo() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content, FragmentFactory.create(FragmentFactory.HAERING_FRAGMENT)).commit();
    }

//    private void initView() {
//
//        mRecyclerView = (XRecyclerView) this.findViewById(R.id.recyclerview);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
//        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
//        mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
//
//        View header = LayoutInflater.from(this).inflate(R.layout.recycler_head_view, (ViewGroup) findViewById(android.R.id.content), false);
//        initHeaderView(header);
//        mRecyclerView.addHeaderView(header);
//
//        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
//            @Override
//            public void onRefresh() {
//                refreshTime++;
//                times = 0;
//                new Handler().postDelayed(new Runnable() {
//                    public void run() {
//
//                        listData.clear();
//                        for (int i = 0; i < 15; i++) {
//                            listData.add("item" + i + "after " + refreshTime + " times of refresh");
//                        }
//                        mAdapter.notifyDataSetChanged();
//                        mRecyclerView.refreshComplete();
//                    }
//
//                }, 1000);            //refresh data here
//            }
//
//            @Override
//            public void onLoadMore() {
//                if (times < 2) {
//                    new Handler().postDelayed(new Runnable() {
//                        public void run() {
//                            mRecyclerView.loadMoreComplete();
//                            for (int i = 0; i < 15; i++) {
//                                listData.add("item" + (i + listData.size()));
//                            }
//                            mAdapter.notifyDataSetChanged();
//                            mRecyclerView.refreshComplete();
//                        }
//                    }, 1000);
//                } else {
//                    new Handler().postDelayed(new Runnable() {
//                        public void run() {
//
//                            mAdapter.notifyDataSetChanged();
//                            mRecyclerView.loadMoreComplete();
//                        }
//                    }, 1000);
//                }
//                times++;
//            }
//        });
//
//        listData = new ArrayList<String>();
//        for (int i = 0; i < 15; i++) {
//            listData.add("item" + i);
//        }
//
//        List<RefreshModel> refreshModels = new ArrayList<RefreshModel>();
//
//        for (int i = 0; i < 6; i++) {
//            RefreshModel model = new RefreshModel();
//            model.setType(1);
//
//            refreshModels.add(model);
//        }
//
//        for (int i = 0; i < 2; i++) {
//            RefreshModel model = new RefreshModel();
//            model.setType(2);
//
//            refreshModels.add(model);
//        }
//        for (int i = 0; i < 6; i++) {
//            RefreshModel model = new RefreshModel();
//            model.setType(1);
//
//            refreshModels.add(model);
//        }
//
//        for (int i = 0; i < 1; i++) {
//            RefreshModel model = new RefreshModel();
//            model.setType(3);
//
//            refreshModels.add(model);
//        }
//
//        mAdapter = new MyAdapter(refreshModels);
//
//        mRecyclerView.setAdapter(mAdapter);
//    }


    /**
     * 初始化头部的布局
     *
     * @param header
     */
    private void initHeaderView(View header) {

        ImageView imageView = null;

        for (int id : imageIds) {
            imageView = new ImageView(this);
            imageView.setImageResource(id);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            imageViewList.add(imageView);
        }

        ViewPager viewPager = (ViewPager) header.findViewById(R.id.head_viewPager);
        viewPager.setAdapter(new ViewPagerAdapter());

    }


    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            View view = imageViewList.get(position % imageViewList.size());

            container.addView(view);
            return imageViewList.get(position % imageViewList.size());
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imageViewList.get(position%imageViewList.size()));
        }
    }

}
