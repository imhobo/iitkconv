package com.aps.iitconv;

import android.os.Bundle;

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
    }

}
