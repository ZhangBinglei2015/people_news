package com.jsonhu.people_news.application;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.jsonhu.people_news.R;
import com.jsonhu.people_news.constants.Constants;
import com.jsonhu.people_news.db.SQLHelper;
import com.jsonhu.people_news.engine.Engine;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.umeng.socialize.PlatformConfig;

import java.io.File;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by lugang on 2016/1/20.
 */
public class NewsApplication extends Application {

    public static NewsApplication mInstantce;
    private Engine mEngine;
    private SQLHelper sqlHelper;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstantce = this;
        mEngine = new Retrofit.Builder()
                .baseUrl("http://7xk9dj.com1.z0.glb.clouddn.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(Engine.class);
        initThridLogin();
        initImageLoader(this);
    }
    /** 获取Application */
    public static NewsApplication getApp() {
        return mInstantce;
    }

    /** 获取数据库Helper */
    public SQLHelper getSQLHelper() {
        if (sqlHelper == null)
            sqlHelper = new SQLHelper(mInstantce);
        return sqlHelper;
    }

    /** 摧毁应用进程时候调用 */
    public void onTerminate() {
        if (sqlHelper != null)
            sqlHelper.close();
        super.onTerminate();
    }

    public void clearAppCache() {
    }
    //初始化第三方登录
    private void initThridLogin() {
        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        //微信 appid appsecret
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad");
        //新浪微博 appkey appsecret
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        // QQ和Qzone appid appkey
    }

    private void initImageLoader(Context applicationContext) {

        FadeInBitmapDisplayer fadeInBitmapDisplayer = new FadeInBitmapDisplayer(1500, true, true, true);
        RoundedBitmapDisplayer roundedBitmapDisplayer = new RoundedBitmapDisplayer(3, 2);
        File file = Constants.getImageCacheFilePath();
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher).cacheInMemory(true)
                .displayer(fadeInBitmapDisplayer)
                .displayer(roundedBitmapDisplayer)
                .cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                applicationContext).denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))

                        // .diskCache(new UnlimitedDiscCache(file))
                .defaultDisplayImageOptions(options).build();

        ImageLoader.getInstance().init(config);

    }

    public static NewsApplication getInstance() {
        return mInstantce;
    }

    public Engine getEngine() {
        return mEngine;
    }
}
