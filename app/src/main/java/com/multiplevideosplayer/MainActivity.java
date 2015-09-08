package com.multiplevideosplayer;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

    private static final String FRAGMENT_TAG = "main_fragment";
    private static String packageName;
    private static String vidAddress = "rtsp://184.72.239.149/vod/mp4:BigBuckBunny_115k.mov";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getFragmentManager();
        Fragment f = fm.findFragmentByTag(FRAGMENT_TAG);
        packageName = getPackageName();
        if (f == null) {
            fm.beginTransaction().add(new MainFragment(), FRAGMENT_TAG).commit();
        }
    }

    public static class MainFragment extends Fragment implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {

        private MediaPlayer mediaPlayer1;
        private MediaPlayer mediaPlayer2;
        private MediaPlayer mediaPlayer3;
        private MediaPlayer mediaPlayer4;

        private VideoSurfaceWrapper videoSulfaceWrapper1;
        private VideoSurfaceWrapper videoSulfaceWrapper2;
        private VideoSurfaceWrapper videoSulfaceWrapper3;
        private VideoSurfaceWrapper videoSulfaceWrapper4;

        private VideoSettings videoSettings1;
        private VideoSettings videoSettings2;
        private VideoSettings videoSettings3;
        private VideoSettings videoSettings4;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            Log.wtf(getTag(), "onCreate !!!");
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
            Uri videoUri = Uri.parse("android.resource://" + packageName + "/" + R.raw.big_buck_bunny);
            videoSettings1 = new VideoSettings(getActivity(), VideoSettings.POSITION_TOP_LEFT, videoUri);
            videoSettings2 = new VideoSettings(getActivity(), VideoSettings.POSITION_TOP_RIGHT, videoUri);
            videoSettings3 = new VideoSettings(getActivity(), VideoSettings.POSITION_BOTTOM_LEFT, videoUri);
            videoSettings4 = new VideoSettings(getActivity(), VideoSettings.POSITION_BOTTOM_RIGHT, videoUri);

            mediaPlayer1 = createMediaPlayer(videoSettings1);
            mediaPlayer2 = createMediaPlayer(videoSettings2);
            mediaPlayer3 = createMediaPlayer(videoSettings3);
            mediaPlayer4 = createMediaPlayer(videoSettings4);
        }


        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            RelativeLayout.LayoutParams mLayoutParams1 = (RelativeLayout.LayoutParams) ((View) getActivity().findViewById(R.id.child_view1)).getLayoutParams();
            RelativeLayout.LayoutParams mLayoutParams2 = (RelativeLayout.LayoutParams) ((View) getActivity().findViewById(R.id.child_view2)).getLayoutParams();
            RelativeLayout.LayoutParams mLayoutParams3 = (RelativeLayout.LayoutParams) ((View) getActivity().findViewById(R.id.child_view3)).getLayoutParams();
            RelativeLayout.LayoutParams mLayoutParams4 = (RelativeLayout.LayoutParams) ((View) getActivity().findViewById(R.id.child_view4)).getLayoutParams();

            videoSulfaceWrapper1 = new VideoSurfaceWrapper(getActivity(), mediaPlayer1, R.id.surface_view1, mLayoutParams1, videoSettings1);
            videoSulfaceWrapper2 = new VideoSurfaceWrapper(getActivity(), mediaPlayer2, R.id.surface_view2, mLayoutParams2, videoSettings2);
            videoSulfaceWrapper3 = new VideoSurfaceWrapper(getActivity(), mediaPlayer3, R.id.surface_view3, mLayoutParams3, videoSettings3);
            videoSulfaceWrapper4 = new VideoSurfaceWrapper(getActivity(), mediaPlayer4, R.id.surface_view4, mLayoutParams4, videoSettings4);

            Button button = (Button) getActivity().findViewById(R.id.one_video_bt);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    videoSulfaceWrapper1.showVideoFullscreen();
                    videoSulfaceWrapper2.showVideoFullscreen();
                    videoSulfaceWrapper3.showVideoFullscreen();
                    videoSulfaceWrapper4.showVideoFullscreen();

                }
            });

            Button button4 = (Button) getActivity().findViewById(R.id.four_video_bt);
            button4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    videoSulfaceWrapper1.setVideoSize();
                    videoSulfaceWrapper2.setVideoSize();
                    videoSulfaceWrapper3.setVideoSize();
                    videoSulfaceWrapper4.setVideoSize();
                }
            });
        }

        private MediaPlayer createMediaPlayer(final VideoSettings videoSettings) {
            MediaPlayer mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(getActivity(), videoSettings.getVideoUri());
                mediaPlayer.prepare();
                mediaPlayer.setOnErrorListener(this);
                mediaPlayer.setOnPreparedListener(this);
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ;
            return mediaPlayer;
        }


        @Override
        public void onPrepared(MediaPlayer mediaPlayer) {
            mediaPlayer.start();
        }

        @Override
        public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
            Log.wtf("Main", "" + i);
            return false;
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            if (mediaPlayer1 != null) {
                mediaPlayer1.release();
                mediaPlayer1 = null;
            }
            if (mediaPlayer2 != null) {
                mediaPlayer2.release();
                mediaPlayer2 = null;
            }
            if (mediaPlayer3 != null) {
                mediaPlayer3.release();
                mediaPlayer3 = null;
            }
            if (mediaPlayer4 != null) {
                mediaPlayer4.release();
                mediaPlayer4 = null;
            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}