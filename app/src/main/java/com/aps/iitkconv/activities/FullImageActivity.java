package com.aps.iitkconv.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.aps.iitconv.R;
import com.aps.iitkconv.models.ImageAdapter;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

/**
 * Created by ankitku on 3/6/17.
 */

public class FullImageActivity extends MainActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        frameLayout.removeAllViews();
        frameLayout.setBackground(null);
        getLayoutInflater().inflate(R.layout.full_image, frameLayout);

        // get intent data
        Intent i = getIntent();

        // Selected image id
        int position = i.getExtras().getInt("id");
        String category = i.getStringExtra(EXTRA_MESSAGE);
        ImageAdapter imageAdapter = new ImageAdapter(this,category);

        ImageView imageView = (ImageView) findViewById(R.id.full_image_view);
        imageView.setImageResource(imageAdapter.use[position]);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

    }

}
