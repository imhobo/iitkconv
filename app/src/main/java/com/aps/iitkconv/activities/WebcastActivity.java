package com.aps.iitkconv.activities;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.aps.iitconv.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

public class WebcastActivity extends MainActivity implements YouTubePlayer.OnInitializedListener {

    int ch = -1;
    String url = "";
    private YouTubePlayerSupportFragment playerFragment;
    private YouTubePlayer mPlayer;
    private String YouTubeKey = "AIzaSyAcRBR_oZDQmCl6AqaAe_9JXHjl--LbNCU";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        frameLayout.removeAllViews();
        frameLayout.setBackground(null);
        getLayoutInflater().inflate(R.layout.activity_webcast, frameLayout);

        //Handling back button
        ch = MainActivity.getChoice();
        MainActivity.setChoice(getIntent().getExtras().getInt("key"));


        SharedPreferences mPrefs = getApplicationContext().getSharedPreferences("lastDataFetch", MODE_PRIVATE);
        url = mPrefs.getString("FetchWebcast", String.valueOf(1));
        playerFragment =
                (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.youtube_player_fragment);


        playerFragment.initialize(YouTubeKey, this);

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                        boolean wasRestored) {
        mPlayer = player;

        //Enables automatic control of orientation
        mPlayer.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION);

        //Show full screen in landscape mode always
        mPlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE);

        //System controls will appear automatically
        mPlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI);


        if (!wasRestored) {
            mPlayer.cueVideo(url);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult errorReason) {
        mPlayer = null;
    }

    protected String parseLink(String url) {
        String result;
        //32 characters before youtube links reach "v="
        result = url.substring(32);
        return result;
    }

    @Override
    public void onBackPressed() {
        MainActivity.setChoice(ch);
        finish();
    }

}
