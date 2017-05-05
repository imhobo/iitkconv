package com.aps.iitkconv.activities;

import android.os.Bundle;

import com.aps.iitconv.R;

/**
 * Created by ankitkumar on 29/04/17.
 */

public class GalleryActivity extends MainActivity
{

    int ch = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        frameLayout.removeAllViews();
        frameLayout.setBackground(null);
        getLayoutInflater().inflate(R.layout.activity_gallery, frameLayout);

        //Handling back button
        ch = MainActivity.getChoice();
        MainActivity.setChoice(getIntent().getExtras().getInt("key"));

        //setContentView(R.layout.activity_gallery);

    }

    @Override
    public void onBackPressed()
    {
        MainActivity.setChoice(ch);
        finish();
    }

}
