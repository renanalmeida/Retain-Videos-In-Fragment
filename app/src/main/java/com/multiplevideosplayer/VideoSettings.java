package com.multiplevideosplayer;

import android.app.Activity;
import android.graphics.Point;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;

/**
 * Created by renan on 04/09/15.
 */
public class VideoSettings {

    public static final int POSITION_TOP_LEFT = 0;
    public static final int POSITION_TOP_RIGHT = 1;
    public static final int POSITION_BOTTOM_LEFT = 2;
    public static final int POSITION_BOTTOM_RIGHT = 3;

    private Uri videoUri;
    private Activity rootActivity;
    private int  position;
    public VideoSettings(Activity rootActivity, int position, Uri videoUri) {
        this.rootActivity = rootActivity;
        this.videoUri = videoUri;
        this.position = position;
    }

    public int getLeftMargin() {
        switch (position){
            case POSITION_TOP_LEFT:
                return 0;
            case POSITION_TOP_RIGHT:
                return getWidth();
            case POSITION_BOTTOM_LEFT:
                return 0;
            case POSITION_BOTTOM_RIGHT:
                return getWidth();
        }
        return 0;
    }

    public int getTopMargin() {
        switch (position){
            case POSITION_TOP_LEFT:
                return 0;
            case POSITION_TOP_RIGHT:
                return 0;
            case POSITION_BOTTOM_LEFT:
                return getHeight();
            case POSITION_BOTTOM_RIGHT:
                return getHeight();
        }
        return 0;
    }

    public int getWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        rootActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width =dm.widthPixels / 2;
        return width;
    }

    public int getHeight() {
        DisplayMetrics dm = new DisplayMetrics();
        rootActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width =  dm.widthPixels / 2;
        double widescreenAspectRatio = 0.5625;
        Log.wtf("VideoSettings", (width * widescreenAspectRatio) + "");
        int height = (int) (width * widescreenAspectRatio);
        return height;
    }

    public Uri getVideoUri() {
        return videoUri;
    }
}
