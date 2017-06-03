package com.aps.iitkconv.activities;

/**
 * Created by ankitku on 02/06/17.
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.aps.iitconv.R;
import com.aps.iitkconv.models.ImageAdapter;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class GridLayoutActivity extends MainActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        frameLayout.removeAllViews();
        frameLayout.setBackground(null);

        getLayoutInflater().inflate(R.layout.grid_layout, frameLayout);


        GridView gridView = (GridView) findViewById(R.id.grid_view);

        // Instance of ImageAdapter Class
        final String category = getIntent().getStringExtra(EXTRA_MESSAGE);
        gridView.setAdapter(new ImageAdapter(this,category));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                // Sending image id to FullScreenActivity
                Intent i = new Intent(getApplicationContext(), FullImageActivity.class);
                // passing array index
                i.putExtra("id", position);
                i.putExtra(EXTRA_MESSAGE, category);
                startActivity(i);
            }
        });

    }

}