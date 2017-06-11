package com.aps.iitkconv.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.aps.iitconv.R;
import com.aps.iitkconv.models.ExtendedViewPager;
import com.aps.iitkconv.models.ImageAdapter;
import com.aps.iitkconv.models.TouchImageView;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

/**
 * Created by ankitku on 3/6/17.
 */

public class FullImageActivity extends MainActivity {

    static int pos;
    static String category;
    static ImageAdapter imageAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        frameLayout.removeAllViews();
        frameLayout.setBackground(null);
        getLayoutInflater().inflate(R.layout.full_image, frameLayout);

    }

    @Override
    protected void onResume() {
        super.onResume();

        // get intent data
        Intent i = getIntent();

        // Selected image id
        pos = i.getExtras().getInt("id");
        category = i.getStringExtra(EXTRA_MESSAGE);
        imageAdapter = new ImageAdapter(this, category);

        ExtendedViewPager mViewPager = (ExtendedViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(new TouchImageAdapter(imageAdapter.use));

    }

    static class TouchImageAdapter extends PagerAdapter {

        private static Integer[] images;

        TouchImageAdapter(Integer[] ims) {
            images = ims;
        }

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            if (pos > position) {
                position = pos;
                pos = -1;
            }
            TouchImageView img = new TouchImageView(container.getContext());
            img.setImageResource(images[position]);
            container.addView(img, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            return img;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }

}
