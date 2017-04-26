package com.aps.iitconv;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

public class WebcastActivity extends AppCompatActivity implements YouTubePlayer.OnInitializedListener{

    private YouTubePlayerSupportFragment  playerFragment;
    private YouTubePlayer mPlayer;
    private String YouTubeKey = "AIzaSyAcRBR_oZDQmCl6AqaAe_9JXHjl--LbNCU";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webcast);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        SharedPreferences mPrefs = getApplicationContext().getSharedPreferences("lastDataFetch", MODE_PRIVATE);
        String url = mPrefs.getString("FetchWebcast", String.valueOf(1));

        if (!wasRestored)
        {
            if(url.equals("1"))
                player.cueVideo("9rLZYyMbJic");
            else
                mPlayer.loadVideo(parseLink(url));
        }
        else
        {
            mPlayer.play();
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult errorReason) {
        mPlayer = null;
    }

    protected String parseLink(String url)
    {
        String result;
        //32 characters before youtube links reach "v="
        result = url.substring(32);
        return result;
    }

}
