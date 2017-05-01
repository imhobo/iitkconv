package com.aps.iitkconv.activities;

import android.os.Bundle;

import com.aps.iitconv.R;

/**
 * Created by ankitkumar on 29/04/17.
 */

public class GalleryActivity extends MainActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        getLayoutInflater().inflate(R.layout.activity_gallery, frameLayout);

        //setContentView(R.layout.activity_gallery);

    }

}
