package com.jsonhu.people_news.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jsonhu.people_news.R;
import com.jsonhu.people_news.activity.WebViewDetailActivity;
import com.jsonhu.people_news.application.NewsApplication;
import com.jsonhu.people_news.holder.VedioPlayer;
import com.jsonhu.people_news.model.RefreshModel;
import com.jsonhu.people_news.utills.LogUtil;
import com.jsonhu.people_news.utills.ToastUtil;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lugang on 2016/1/21.
 */
public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static List<RefreshModel> datas = null;
    public static Context context;
    private static VedioPlayer vedioPlayer;

    private static final String TAG = "MyAdapter";

    public MyAdapter(Context ctx,List<RefreshModel> datas) {
        this.context = ctx;
        this.datas = datas;

        vedioPlayer = new VedioPlayer(ctx);
    }

    public static final int VIEW_TYPE_FIRST = 1001;
    public static final int VIEW_TYPE_SECOND = 1002;
    public static final int VIEW_TYPE_THIRD = 1003;
    public static final int VIEW_TYPE_FORTH = 1004;

    private static MediaPlayer mediaPlayer;
    private static Surface surface;

    private boolean isPlaying = false;



    //创建新View，被LayoutManager所调用
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == VIEW_TYPE_FIRST) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
            FisrtTypeViewHolder vh1 = new FisrtTypeViewHolder(view);
            return vh1;
        } else if (viewType == VIEW_TYPE_SECOND) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_second, viewGroup, false);
            SecondTypeViewHolder vh2 = new SecondTypeViewHolder(view);
            return vh2;
        } else if(viewType == VIEW_TYPE_THIRD){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_third, viewGroup, false);
            return new ThirdTypeViewHolder(view);
        }else {

            View view  = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_forth,viewGroup,false);

            return new ForthTypeViewHolder(view);
        }

    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        // viewHolder.mTextView.setText(datas.get(position));
    }


    @Override
    public int getItemViewType(int position) {
        if (datas.get(position).getType() == 1) {
            return VIEW_TYPE_FIRST;
        } else if (datas.get(position).getType() == 2) {
            return VIEW_TYPE_SECOND;
        } else if (datas.get(position).getType() == 3){
            return VIEW_TYPE_THIRD;
        }else {
            return VIEW_TYPE_FORTH;
        }
//        return super.getItemViewType(position);
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return datas.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class FisrtTypeViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public FisrtTypeViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.text);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.show("当前的位置" + getAdapterPosition() + ";" + datas.get(getAdapterPosition() - 2));

                    context.startActivity(new Intent(context, WebViewDetailActivity.class));
                }
            });
        }
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class SecondTypeViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public SecondTypeViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.text);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //ToastUtil.show("当前的位置" + getAdapterPosition() + ";" + datas.get(getAdapterPosition() - 2));
                }
            });
        }
    }

    public static class ForthTypeViewHolder extends RecyclerView.ViewHolder{
        public ForthTypeViewHolder(View view){
            super(view);


        }
    }


    /**
     * 这个Runnable是用来控制SeekBar的
     */
    private static class VedioSeekBarRunnable implements Runnable{

        AppCompatSeekBar seekBar;

        public VedioSeekBarRunnable(AppCompatSeekBar sb){
            this.seekBar = sb;
        }

        @Override
        public void run() {
            if (seekBar!=null){
                long position  = vedioPlayer.getCurrentPosition();
                seekBar.setProgress((int) position);
            }

            if (vedioPlayer.isplaying() || vedioPlayer.getBuffering()<= 100){
                seekBar.postDelayed(this,100);
            }

        }
    }

    /**
     * 下面的这个ViewHolder是用来管理列表中视频播放的
     */
    public  static class ThirdTypeViewHolder extends RecyclerView.ViewHolder {

        TextureView textureView;
        ImageButton playbutton;
        ImageView iv_list_vedio_default;
        ProgressBar spinner;
        FrameLayout fl_video_playbutton_layout;
        AppCompatSeekBar seekBar;
        VedioSeekBarRunnable seekBarRunnable;
        ImageView iv_video_list_play;
        ImageView iv_video_list_fullscreen;
        ThirdHolderClickListener clickListener;
        ImageView iv_vedio_list_share,iv_vedio_list_collect;

        public ThirdTypeViewHolder(View view) {
            super(view);

            clickListener = new ThirdHolderClickListener(this);

            textureView = (TextureView) view.findViewById(R.id.texture_view_list);
            playbutton = (ImageButton) view.findViewById(R.id.iv_button_list_vedioplay);

            iv_video_list_play = (ImageView) view.findViewById(R.id.iv_video_play_and_pause);
            iv_video_list_fullscreen = (ImageView) view.findViewById(R.id.iv_video_full_screen);
            iv_list_vedio_default = (ImageView) view.findViewById(R.id.iv_list_vedio_default);
            iv_vedio_list_share = (ImageView) view.findViewById(R.id.iv_vedio_list_share);
            iv_vedio_list_collect = (ImageView) view.findViewById(R.id.iv_vedio_list_collect);

            spinner = (ProgressBar) view.findViewById(R.id.progressBar);
            fl_video_playbutton_layout = (FrameLayout) view.findViewById(R.id.fl_video_playbutton_layout);
            seekBar = (AppCompatSeekBar) view.findViewById(R.id.seekbar_view_list);
            seekBarRunnable = new VedioSeekBarRunnable(seekBar);
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        vedioPlayer.seekTo((long) progress);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            //在此处需要对vedioplayer进行设置

            vedioPlayer.setTextureView(textureView);
            vedioPlayer.setVedioPlayListener(new VedioPlayer.onVedioPlayListener() {
                @Override
                public void onVedioAvailable() {
                    LogUtil.i(TAG, "onVedioAvailable");
                }

                @Override
                public void onVedioDestroyed() {
                    LogUtil.i(TAG, "onVedioDestroyed");
                    fl_video_playbutton_layout.setVisibility(View.VISIBLE);
                    spinner.setVisibility(View.GONE);
                    seekBar.removeCallbacks(seekBarRunnable);
                    vedioPlayer.setInitialize(false);
                }

                @Override
                public void onRemoteVedioPrepared() {
                    LogUtil.i(TAG, "onRemoteVedioPrepared");
                    spinner.setVisibility(View.GONE);
                    vedioPlayer.play();

                    startSeekRunnable();
                }

                @Override
                public void onVedioPlayCompleted() {
                    LogUtil.i(TAG, "onVedioPlayCompleted");
                }

                @Override
                public void onVedioPlayError() {

                    LogUtil.i(TAG, "onVedioPlayError");

                }

                @Override
                public void onTextureViewSizeChanged(SurfaceTexture surface, int width, int height) {
                    LogUtil.i(TAG, "onTextureViewSizeChanged : width = " + width + "  height = " + height);
                }

                @Override
                public void onMediaBufferUpdate(long percent) {
//                    seekBar.setSecondaryProgress((int) percent);
                }
            });


            iv_video_list_fullscreen.setOnClickListener(clickListener);
            iv_vedio_list_collect.setOnClickListener(clickListener);
            iv_vedio_list_share.setOnClickListener(clickListener);
            iv_video_list_play.setOnClickListener(clickListener);
            playbutton.setOnClickListener(clickListener);
        }

        public void startSeekRunnable(){
            if (seekBar!=null){
                seekBar.setMax((int) vedioPlayer.getDuration());
                seekBar.postDelayed(seekBarRunnable, 100);
            }
        }

    }



    /**
     * 设置视频播放器holder的clickListener
     */
    private static class ThirdHolderClickListener implements View.OnClickListener{

        private ThirdTypeViewHolder holder;

        public ThirdHolderClickListener(ThirdTypeViewHolder typeViewHolder){

            this.holder = typeViewHolder;

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){

                case R.id.iv_button_list_vedioplay:

                    holder.fl_video_playbutton_layout.setVisibility(View.GONE);
                    holder.spinner.setVisibility(View.VISIBLE);

                    boolean initialize = vedioPlayer.isPlayerInitialized();
                    if (!initialize){
                        vedioPlayer.setVedioPath(getVedioPath());
                        vedioPlayer.initPlayer();
                    }else {
                        vedioPlayer.play();
                    }


                    break;

                case R.id.iv_video_play_and_pause:

                    if (vedioPlayer.isplaying()){
                        holder.iv_video_list_play.setImageResource(R.mipmap.video_list_pause);
                        vedioPlayer.pause();
                    }else {
                        holder.iv_video_list_play.setImageResource(R.mipmap.video_list_play);
                        vedioPlayer.play();
                    }

                    holder.startSeekRunnable();

                    break;

                case R.id.iv_vedio_list_share:
                    ToastUtil.show("分享");
                    break;

                case R.id.iv_vedio_list_collect:
                    ToastUtil.show("收藏");
                    break;

                case R.id.iv_video_full_screen:

                    ToastUtil.show("全屏");

                    holder.textureView.setRotation(90.0f);

                    break;

                default:
                    break;
            }
        }
    }

    private static String getVedioPath(){
//        return "android.resource://" + NewsApplication.mInstantce.getPackageName() + "/" + R.raw.video;

        return "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
    }



}
