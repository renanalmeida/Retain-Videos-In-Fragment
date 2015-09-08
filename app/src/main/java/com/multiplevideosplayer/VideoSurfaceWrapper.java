package com.multiplevideosplayer;

import android.app.Activity;
import android.media.MediaPlayer;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by Cliente on 07/09/2015.
 */
public class VideoSurfaceWrapper implements SurfaceHolder.Callback {

    private SurfaceHolder surfaceHolder;
    private SurfaceView surfaceView;
    private VideoSettings videoSettings;
    private RelativeLayout.LayoutParams mLayoutParams;
    private Activity rootActivity;
    private MediaPlayer mediaPlayer;

    public VideoSurfaceWrapper(Activity rootActivity, MediaPlayer mediaPlayer, int surfaceViewId, RelativeLayout.LayoutParams mLayoutParams, VideoSettings videoSettings) {
        this.mLayoutParams = mLayoutParams;
        this.videoSettings = videoSettings;
        this.rootActivity = rootActivity;
        this.mediaPlayer = mediaPlayer;
        surfaceView = (SurfaceView) rootActivity.findViewById(surfaceViewId);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
    }

    public void setVideoSize() {
        android.view.ViewGroup.LayoutParams lp = surfaceView.getLayoutParams();
        lp.width = videoSettings.getWidth();
        lp.height = videoSettings.getHeight();
        surfaceView.setLayoutParams(lp);
        mLayoutParams.setMargins(videoSettings.getLeftMargin(), videoSettings.getTopMargin(), 0, 0);
    }

    public void showVideoFullscreen() {
        DisplayMetrics dm = new DisplayMetrics();
        rootActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        double widescreenAspectRatio = 0.5625;
        int height = (int) (width * widescreenAspectRatio);

        mLayoutParams.leftMargin = 0;
        mLayoutParams.topMargin = 0;
        mLayoutParams.width = width;
        mLayoutParams.height = height;
        ViewGroup.LayoutParams lp = surfaceView.getLayoutParams();
        lp.width = width;
        lp.height = height;
        surfaceView.setLayoutParams(lp);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mediaPlayer.setDisplay(surfaceHolder);
        setVideoSize();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        setVideoSize();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    }
}
