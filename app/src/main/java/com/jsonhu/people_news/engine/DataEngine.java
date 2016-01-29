package com.jsonhu.people_news.engine;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jsonhu.people_news.R;
import com.jsonhu.people_news.application.NewsApplication;
import com.jsonhu.people_news.model.BannerModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/26 上午1:03
 * 描述:
 */
public class DataEngine {

//    public static View getCustomHeaderView(final Context context) {
//        View headerView = View.inflate(context, R.layout.view_custom_header, null);
//        final BGABanner banner = (BGABanner) headerView.findViewById(R.id.banner);
//        final List<View> views = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            views.add(View.inflate(context, R.layout.view_image, null));
//        }
//        banner.setViews(views);
//        NewsApplication.getInstance().getEngine().getBannerModel().enqueue(new Callback<BannerModel>() {
//            @Override
//            public void onResponse(Response<BannerModel> response) {
//                BannerModel bannerModel = response.body();
//                for (int i = 0; i < views.size(); i++) {
//                    Glide.with(context).load(bannerModel.imgs.get(i)).placeholder(R.mipmap.holder).error(R.mipmap.holder).dontAnimate().thumbnail(0.1f).into((ImageView) views.get(i));
//                }
//                banner.setTips(bannerModel.tips);
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//            }
//        });
//        return headerView;
//    }

}