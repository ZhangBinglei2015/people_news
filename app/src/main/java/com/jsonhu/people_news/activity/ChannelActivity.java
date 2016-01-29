package com.jsonhu.people_news.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

import com.jsonhu.people_news.R;
import com.jsonhu.people_news.adapter.CateGoryAdapter;
import com.jsonhu.people_news.adapter.ChannelAdapter;
import com.jsonhu.people_news.adapter.ChannelLocalAdapter;
import com.jsonhu.people_news.application.NewsApplication;
import com.jsonhu.people_news.base.BaseActivity;
import com.jsonhu.people_news.model.ChannelItem;
import com.jsonhu.people_news.dao.ChannelManage;
import com.jsonhu.people_news.view.draggridview.DragGrid;
import com.jsonhu.people_news.view.draggridview.OtherGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/1/21.
 * 频道设置
 */
public class ChannelActivity extends BaseActivity implements AdapterView.OnItemClickListener{

    private Button act_channel_local,act_channel_cat;


    /** 用户栏目的GRIDVIEW */
    private DragGrid userGridView;
    /** 其它栏目的GRIDVIEW */
    private OtherGridView otherGridView;

    private OtherGridView CateGoryGridView;

    private CateGoryAdapter cateGoryAdapter;
    /** 用户栏目对应的适配器，可以拖动 */
    ChannelAdapter userAdapter;
    /** 其它栏目对应的适配器 */
    ChannelLocalAdapter otherAdapter;
    /** 其它栏目列表 */
    ArrayList<ChannelItem> otherChannelList = new ArrayList<ChannelItem>();
    /** 用户栏目列表 */
    ArrayList<ChannelItem> userChannelList = new ArrayList<ChannelItem>();
    ArrayList<ChannelItem> cateGoryList = new ArrayList<ChannelItem>();

    /** 是否在移动，由于这边是动画结束后才进行的数据更替，设置这个限制为了避免操作太频繁造成的数据错乱。 */
    boolean isMove = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0,0);
        setContentView(R.layout.act_channel);
        initView();
        initData();
    }

    private void initView() {
        ImageView icon_add = (ImageView) findViewById(R.id.act_channel_icon_add);
        act_channel_local = (Button) findViewById(R.id.act_channel_local);
        act_channel_cat = (Button) findViewById(R.id.act_channel_cat);


        userGridView = (DragGrid) findViewById(R.id.act_channel_customGridView);
        otherGridView = (OtherGridView) findViewById(R.id.act_channel_local_grid);
        CateGoryGridView = (OtherGridView) findViewById(R.id.act_channel_local_grid2);
        userGridView.setTag(0);
        otherGridView.setTag(1);
        CateGoryGridView.setTag(2);
        act_channel_local.setSelected(true);
        icon_add.setOnClickListener(this);
        act_channel_local.setOnClickListener(this);
        act_channel_cat.setOnClickListener(this);
        rotate135(icon_add);

    }

    /** 初始化数据*/
    private void initData() {
//        ChannelManage.getManage(NewsApplication.getApp().getSQLHelper()).initDefaultChannel();
        userChannelList = ((ArrayList<ChannelItem>) ChannelManage.getManage(NewsApplication.getApp().getSQLHelper()).getUserChannel());
        otherChannelList = ((ArrayList<ChannelItem>)ChannelManage.getManage(NewsApplication.getApp().getSQLHelper()).getOtherChannel());
        cateGoryList = ((ArrayList<ChannelItem>)ChannelManage.getManage(NewsApplication.getApp().getSQLHelper()).getCateGoryChannel());
        userAdapter = new ChannelAdapter(this, userChannelList);
        userGridView.setAdapter(userAdapter);
        setOtherAdapter();
        cateGoryAdapter = new CateGoryAdapter(this,cateGoryList);
        CateGoryGridView.setAdapter(cateGoryAdapter);
        //设置GRIDVIEW的ITEM的点击监听
        otherGridView.setOnItemClickListener(this);
        userGridView.setOnItemClickListener(this);
        CateGoryGridView.setOnItemClickListener(this);
    }

    private void rotate135(final ImageView iv){

        Animation operatingAnim = AnimationUtils.loadAnimation(this,R.anim.iv_rotate);
        operatingAnim.setInterpolator(new LinearInterpolator());
        operatingAnim.setFillAfter(true);
        iv.startAnimation(operatingAnim);

    }
    private void setOtherAdapter(){
        otherAdapter = new ChannelLocalAdapter(this, otherChannelList);
        otherGridView.setAdapter(this.otherAdapter);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.act_channel_icon_add:
                saveChannel();

                finish();
                break;
            case R.id.act_channel_local:
                CateGoryGridView.setVisibility(View.GONE);
                otherGridView.setVisibility(View.VISIBLE);
                switchTab(0);
                break;
            case R.id.act_channel_cat:
                CateGoryGridView.setVisibility(View.VISIBLE);
                otherGridView.setVisibility(View.GONE);
                switchTab(1);
                break;
        }
    }
    private void switchTab(int tab){
        switch (tab){
            case 0:
                act_channel_local.setTextColor(getResources().getColor(R.color.white));
                act_channel_cat.setTextColor(getResources().getColor(R.color.bg_black));
                act_channel_local.setSelected(true);
                act_channel_cat.setSelected(false);
            break;
            case 1:
                act_channel_local.setTextColor(getResources().getColor(R.color.bg_black));
                act_channel_cat.setTextColor(getResources().getColor(R.color.white));
                act_channel_local.setSelected(false);
                act_channel_cat.setSelected(true);

            break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        //如果点击的时候，之前动画还没结束，那么就让点击事件无效
        if(isMove){
            return;
        }
        switch (parent.getId()) {
            case R.id.act_channel_customGridView:
                //position为 0，1 的不可以进行任何操作
                if (position != 0 && position != 1) {
                    final ImageView moveImageView = getView(view);
                    if (moveImageView != null) {
                        TextView newTextView = (TextView) view.findViewById(R.id.navigate_content_right);
                        final int[] startLocation = new int[2];
                        newTextView.getLocationInWindow(startLocation);
                        final ChannelItem channel = ((ChannelAdapter) parent.getAdapter()).getItem(position);//获取点击的频道内容
                        final GridView gv;
                        if (channel.getType() == 1){
                            gv = otherGridView;

                            otherAdapter.setVisible(false);
                            //添加到最后一个
                            otherAdapter.addItem(channel);
                        }else {
                            gv = CateGoryGridView;

                            cateGoryAdapter.setVisible(false);
                            //添加到最后一个
                            cateGoryAdapter.addItem(channel);
                        }
                        otherAdapter.setVisible(false);
                        //添加到最后一个
                        otherAdapter.addItem(channel);
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                try {
                                    int[] endLocation = new int[2];
                                    //获取终点的坐标
                                    gv.getChildAt(gv.getLastVisiblePosition()).getLocationInWindow(endLocation);
                                    MoveAnim(moveImageView, startLocation , endLocation, channel,userGridView);
                                    userAdapter.setRemove(position);
                                } catch (Exception localException) {
                                }
                            }
                        }, 50L);
                    }
                }
                break;
            case R.id.act_channel_local_grid:
                final ImageView moveImageView = getView(view);
                if (moveImageView != null){
                    TextView newTextView = (TextView) view.findViewById(R.id.text_item);
                    final int[] startLocation = new int[2];
                    newTextView.getLocationInWindow(startLocation);
                    final ChannelItem channel = ((ChannelLocalAdapter) parent.getAdapter()).getItem(position);
                    userAdapter.setVisible(false);
                    //添加到最后一个
                    userAdapter.addItem(channel);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            try {
                                int[] endLocation = new int[2];
                                //获取终点的坐标
                                userGridView.getChildAt(userGridView.getLastVisiblePosition()).getLocationInWindow(endLocation);
                                MoveAnim(moveImageView, startLocation , endLocation, channel,otherGridView);
                                otherAdapter.setRemove(position);
                            } catch (Exception localException) {
                            }
                        }
                    }, 50L);
                }
                break;
            case R.id.act_channel_local_grid2:
                final ImageView moveImageView2 = getView(view);
                if (moveImageView2 != null){
                    TextView newTextView = (TextView) view.findViewById(R.id.text_item);
                    final int[] startLocation = new int[2];
                    newTextView.getLocationInWindow(startLocation);
                    final ChannelItem channel = ((CateGoryAdapter) parent.getAdapter()).getItem(position);
                    userAdapter.setVisible(false);
                    //添加到最后一个
                    userAdapter.addItem(channel);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            try {
                                int[] endLocation = new int[2];
                                //获取终点的坐标
                                userGridView.getChildAt(userGridView.getLastVisiblePosition()).getLocationInWindow(endLocation);
                                MoveAnim(moveImageView2, startLocation , endLocation, channel,CateGoryGridView);
                                cateGoryAdapter.setRemove(position);
                            } catch (Exception localException) {
                            }
                        }
                    }, 50L);
                }
                break;
            default:
                break;
        }
    }
    /**
     * 点击ITEM移动动画
     * @param moveView
     * @param startLocation
     * @param endLocation
     * @param moveChannel
     * @param clickGridView
     */
    private void MoveAnim(View moveView, int[] startLocation,int[] endLocation, final ChannelItem moveChannel,
                          final GridView clickGridView) {
        int[] initLocation = new int[2];
        //获取传递过来的VIEW的坐标
        moveView.getLocationInWindow(initLocation);
        //得到要移动的VIEW,并放入对应的容器中
        final ViewGroup moveViewGroup = getMoveViewGroup();
        final View mMoveView = getMoveView(moveViewGroup, moveView, initLocation);
        //创建移动动画
        TranslateAnimation moveAnimation = new TranslateAnimation(
                startLocation[0], endLocation[0], startLocation[1],
                endLocation[1]);
        moveAnimation.setDuration(300L);//动画时间
        //动画配置
        AnimationSet moveAnimationSet = new AnimationSet(true);
        moveAnimationSet.setFillAfter(false);//动画效果执行完毕后，View对象不保留在终止的位置
        moveAnimationSet.addAnimation(moveAnimation);
        mMoveView.startAnimation(moveAnimationSet);
        moveAnimationSet.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                isMove = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
//                moveViewGroup.removeView(mMoveView);
//                // instanceof 方法判断2边实例是不是一样，判断点击的是DragGrid还是OtherGridView
//                if (clickGridView.getTag() ==0) {
//                    otherAdapter.setVisible(true);
//                    otherAdapter.notifyDataSetChanged();
//                    userAdapter.remove();
//                }else if (clickGridView.getTag() == 1){
//                    userAdapter.setVisible(true);
//                    userAdapter.notifyDataSetChanged();
//                    otherAdapter.remove();
//                }else{
//                    userAdapter.setVisible(true);
//                    userAdapter.notifyDataSetChanged();
//                    cateGoryAdapter.remove();
//                }
//                isMove = false;
            }
        });
    }

    /**
     * 获取移动的VIEW，放入对应ViewGroup布局容器
     * @param viewGroup
     * @param view
     * @param initLocation
     * @return
     */
    private View getMoveView(ViewGroup viewGroup, View view, int[] initLocation) {
        int x = initLocation[0];
        int y = initLocation[1];
        viewGroup.addView(view);
        LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mLayoutParams.leftMargin = x;
        mLayoutParams.topMargin = y;
        view.setLayoutParams(mLayoutParams);
        return view;
    }

    /**
     * 创建移动的ITEM对应的ViewGroup布局容器
     */
    private ViewGroup getMoveViewGroup() {
        ViewGroup moveViewGroup = (ViewGroup) getWindow().getDecorView();
        LinearLayout moveLinearLayout = new LinearLayout(this);
        moveLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        moveViewGroup.addView(moveLinearLayout);
        return moveLinearLayout;
    }

    /**
     * 获取点击的Item的对应View，
     * @param view
     * @return
     */
    private ImageView getView(View view) {
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(true);
        Bitmap cache = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        ImageView iv = new ImageView(this);
        iv.setImageBitmap(cache);
        return iv;
    }

    /** 退出时候保存选择后数据库的设置  */
    private void saveChannel() {
        ChannelManage.getManage(NewsApplication.getApp().getSQLHelper()).deleteAllChannel();
        ChannelManage.getManage(NewsApplication.getApp().getSQLHelper()).saveUserChannel(userAdapter.getChannnelLst());
        ChannelManage.getManage(NewsApplication.getApp().getSQLHelper()).saveOtherChannel(otherAdapter.getChannnelLst());
        ChannelManage.getManage(NewsApplication.getApp().getSQLHelper()).saveCateGoryChannel(cateGoryAdapter.getChannnelLst());
    }



    @Override
    public void onBackPressed() {
        saveChannel();
        super.onBackPressed();
    }
}
