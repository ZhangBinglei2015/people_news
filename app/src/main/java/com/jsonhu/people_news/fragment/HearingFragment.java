package com.jsonhu.people_news.fragment;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.LoadingMoreFooter;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jsonhu.people_news.R;
import com.jsonhu.people_news.activity.NewsDetailActivity;
import com.jsonhu.people_news.adapter.MyAdapter;
import com.jsonhu.people_news.model.RefreshModel;
import com.jsonhu.people_news.utills.ToastUtil;
import com.jsonhu.people_news.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lugang on 2016/1/20.
 */
public class HearingFragment extends BaseFragment {

    private XRecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private int refreshTime = 0;
    private int times = 0;
    public static String FRAGMENT_TAG = HearingFragment.class.getSimpleName();

    private static int[] imageIds = new int[]{R.drawable.image01, R.drawable.image02, R.drawable.image03, R.drawable.image04};
    private static List<ImageView> imageViewList = new ArrayList<ImageView>();

    private static final String[] viewpagerStrings = new String[]{"人民日报批“做空中国”：只会做空自己","上赢联，免费看超级纬度大戏","别让2075万留守儿童成家庭之痛社会之殇","300年后星际人类的科技与生活"};

    // 标志位，标志已经初始化完成。
    private boolean isPrepared = false;
    private boolean mHasLoadedOnce = false;
    private Activity activity;

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || mHasLoadedOnce) {
            return;
        }

        mHasLoadedOnce = true;
        Toast.makeText(getActivity(), "标志位，标志已经初始化完成。", Toast.LENGTH_SHORT).show();
    }


    @Override
    public View initView(LayoutInflater inflater) {


        activity = getActivity();
        /*if (getActivity() instanceof NewsDetailActivity){
        }else {
            ToastUtil.show("bushi");
        }*/
        rootView = inflater.inflate(R.layout.fragment_hearing_layout, null);
        isPrepared = true;
        lazyLoad();
        createView(inflater);

        return rootView;
    }

    private void createView(LayoutInflater inflater) {
        mRecyclerView = (XRecyclerView) rootView.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setPullRefreshEnabled(true);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
//        mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

        mRecyclerView.setArrowImageView(R.mipmap.default_ptr_flip);

        mRecyclerView.setLoadingMoreEnabled(true);

        View header = LayoutInflater.from(getActivity()).inflate(R.layout.recycler_head_view, (ViewGroup) activity.findViewById(android.R.id.content), false);
        initHeaderView(header);
        mRecyclerView.addHeaderView(header);

        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                refreshTime++;
                times = 0;
                new Handler().postDelayed(new Runnable() {
                    public void run() {

                        mAdapter.notifyDataSetChanged();
                        mRecyclerView.refreshComplete();
                    }

                }, 1000);            //refresh data here
            }

            @Override
            public void onLoadMore() {
                if (times < 2) {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            mRecyclerView.loadMoreComplete();
                            mAdapter.notifyDataSetChanged();
                            mRecyclerView.refreshComplete();
                        }
                    }, 1000);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {

                            mAdapter.notifyDataSetChanged();
                            mRecyclerView.loadMoreComplete();
                        }
                    }, 1000);
                }
                times++;
            }
        });

        setData();

    }

    private void setData() {

        List<RefreshModel> refreshModels = new ArrayList<RefreshModel>();

        for (int i = 0; i < 6; i++) {
            RefreshModel model = new RefreshModel();
            model.setType(1);

            refreshModels.add(model);
        }

        for (int i = 0; i < 2; i++) {
            RefreshModel model = new RefreshModel();
            model.setType(2);

            refreshModels.add(model);
        }
        for (int i = 0; i < 6; i++) {
            RefreshModel model = new RefreshModel();
            model.setType(1);

            refreshModels.add(model);
        }

        for (int i = 0; i < 1; i++) {
            RefreshModel model = new RefreshModel();
            model.setType(3);

            refreshModels.add(model);
        }

        for (int i = 0; i < 4; i++) {
            RefreshModel model = new RefreshModel();
            model.setType(1);

            refreshModels.add(model);
        }

        for (int i = 0; i < 1; i++) {
            RefreshModel model = new RefreshModel();
            model.setType(4);

            refreshModels.add(model);
        }

        mAdapter = new MyAdapter(activity, refreshModels);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));


    }

    @Override
    public void loadData() {

    }

    /**
     * 初始化头部的布局
     *
     * @param header
     */
    private void initHeaderView(View header) {

        ImageView imageView = null;

        for (int id : imageIds) {
            imageView = new ImageView(activity);
            imageView.setImageResource(id);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            imageViewList.add(imageView);
        }

        final TextView tv_viewpager_text = (TextView) header.findViewById(R.id.tv_viewpager_text);
        final TextView tv_viewpager_img_number = (TextView) header.findViewById(R.id.tv_viewpager_img_number);

        ViewPager viewPager = (ViewPager) header.findViewById(R.id.head_viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tv_viewpager_text.setText(viewpagerStrings[position % viewpagerStrings.length]);

                int index = position % viewpagerStrings.length +1;

                tv_viewpager_img_number.setText(index+"/4");

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAdapter(new ViewPagerAdapter());

        tv_viewpager_text.setText(viewpagerStrings[0]);
        tv_viewpager_img_number.setText("1/4");

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
            container.removeView(imageViewList.get(position % imageViewList.size()));
        }
    }

}
