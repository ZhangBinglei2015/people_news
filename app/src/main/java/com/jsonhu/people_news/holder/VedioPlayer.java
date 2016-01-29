package com.jsonhu.people_news.holder;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;

import com.jsonhu.people_news.utills.LogUtil;

import java.io.IOException;

/**
 * Created by lugang on 2016/1/22.
 */
public class VedioPlayer implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnBufferingUpdateListener {
    private MediaPlayer mediaPlayer;
    private TextureView mTextureView;
    private onVedioPlayListener vedioPlayListener;
    private String vedioPath;
    private final Context context;
    private Surface surface;
    private static final String TAG = "VedioPlayer";
    private boolean isInitialized = false;

    private int buffering = -1;

    public VedioPlayer(final Context ctx) {

        this.context = ctx;
    }

    /**
     *  设置一个Textureview
     * @param textureView
     */
    public void setTextureView(TextureView textureView){
        mTextureView = textureView;
        mTextureView.setSurfaceTextureListener(new VedioTextureListener());
    }

    public boolean isPlayerInitialized(){
        return isInitialized;
    }

    /**
     * 设置媒体播放路劲
     * @param path
     */
    public void setVedioPath(String path) {
        this.vedioPath = path;
    }


    public void initPlayer() {

        if (vedioPath == null) {
            throw new NullPointerException("VedioPath cannot be Null,you must set vedio path");
        }

        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnErrorListener(this);
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setOnBufferingUpdateListener(this);
            mediaPlayer.reset();
            if (vedioPath.startsWith("content://")) {
                mediaPlayer.setDataSource(context, Uri.parse(vedioPath));
            } else if (vedioPath.startsWith("android.resource://")) {
                mediaPlayer.setDataSource(context, Uri.parse(vedioPath));
            } else {
                mediaPlayer.setDataSource(vedioPath);
            }
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setSurface(surface);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
//                    spinner.setVisibility(View.GONE);
//                    mediaPlayer.start();

//                    play();

                    vedioPlayListener.onRemoteVedioPrepared();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();

            isInitialized = false;

        }catch (IllegalArgumentException todo){

            todo.printStackTrace();
            isInitialized =false;
        }

        isInitialized = true;

    }

    /**
     * 开始播放
     */
    public void play(){
            mediaPlayer.start();
    }

    public void release() {
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void setInitialize(boolean isInitialized){
        this.isInitialized = isInitialized;
    }

    /**
     * 停止播放器
     */
    public void stop(){
            mediaPlayer.stop();
            mediaPlayer.reset();

            isInitialized = false;
    }

    /**
     * 暂停播放器
     */
    public void pause(){
          mediaPlayer.pause();
    }


    /**
     * 设置监听回调
     * @param listener
     */
    public void setVedioPlayListener(onVedioPlayListener listener) {
        this.vedioPlayListener = listener;
    }

    /**
     * 返回媒体播放器当前的播放状态
     * @return
     */
    public boolean isplaying(){
        return mediaPlayer.isPlaying();
    }


    /**
     * 获取当前媒体资源播放的位置
     * @return
     */
    public long getCurrentPosition(){
        return mediaPlayer.getCurrentPosition();
    }

    /**
     * 获取媒体的总长度
     * @return
     */

    public long getDuration(){
        return mediaPlayer.getDuration();
    }

    /**
     * 将媒体进度滑动到指定的位置
     * @param position
     */
    public void seekTo(long position){
        mediaPlayer.seekTo((int) position);
    }

    public int getBuffering(){
        return buffering;
    }

    /**
     * 这个回调是当音频或者视频播放完成之后的回调。
     *
     * @param mp
     */
    @Override
    public void onCompletion(MediaPlayer mp) {
        vedioPlayListener.onVedioPlayCompleted();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {

        isInitialized = false;

//        mediaPlayer.release();
//        mediaPlayer = new MediaPlayer();
//        vedioPlayListener.onVedioPlayError();
//        return true;

        return false;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        LogUtil.i(TAG,"percent = "+percent);
        //vedioPlayListener.onMediaBufferUpdate((long)percent);

        buffering = percent;
    }


    /**
     * 此处需要从外部的Adapter中传递一个Textureview，用来处理当Textureview从屏幕中移除的时候需要做什么
     */
    private class VedioTextureListener implements TextureView.SurfaceTextureListener {

        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
            surface = new Surface(surfaceTexture);
            vedioPlayListener.onVedioAvailable();

        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
            vedioPlayListener.onTextureViewSizeChanged(surface,width,height);
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {

            //此处当用户按下Home键的时候最好是判断一下当前的视频是否在播放，如果播放就先暂停，下次进来的时候跟着上次的位置继续播放

            surface = null;
            release();
            vedioPlayListener.onVedioDestroyed();

            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }
    }


    /**
     * 此处是用于给Adapter中的Vedio播放的时候回调
     */
    public interface onVedioPlayListener {
        void onVedioAvailable();

        void onVedioDestroyed();

        void onRemoteVedioPrepared();

        void onVedioPlayCompleted();

        void onVedioPlayError();

        void onTextureViewSizeChanged(SurfaceTexture surface,int width,int height);

        void onMediaBufferUpdate(long percent);
    }


}
