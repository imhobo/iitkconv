package com.aps.iitkconv.models;

/**
 * Created by ankitku on 02/06/17.
 */


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.aps.iitconv.R;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    // Keep all Images in array

    public Integer[] insti = {
            R.drawable.insti_1, R.drawable.insti_4, R.drawable.insti_teaser, R.drawable.insti_10,
            R.drawable.insti_5, R.drawable.insti_11, R.drawable.insti_6, R.drawable.insti_12,
            R.drawable.insti_7, R.drawable.insti_2, R.drawable.insti_8, R.drawable.insti_3, R.drawable.insti_9};
    public Integer[] kiap = {R.drawable.kiap_6, R.drawable.kiap_1, R.drawable.kiap_teaser, R.drawable.kiap_2,
            R.drawable.kiap_3, R.drawable.kiap_4, R.drawable.kiap_5};
    public Integer[] stud = {R.drawable.stud_11, R.drawable.stud_17, R.drawable.stud_6, R.drawable.stud_12,
            R.drawable.stud_18, R.drawable.stud_7, R.drawable.stud_13, R.drawable.stud_2, R.drawable.stud_8,
            R.drawable.stud_14, R.drawable.stud_3, R.drawable.stud_9, R.drawable.stud_1, R.drawable.stud_15,
            R.drawable.stud_4, R.drawable.stud_teaser, R.drawable.stud_10, R.drawable.stud_16, R.drawable.stud_5};
    public Integer[] old = {R.drawable.old_10, R.drawable.old_2, R.drawable.old_8, R.drawable.old_11,
            R.drawable.old_3, R.drawable.old_9, R.drawable.old_12, R.drawable.old_4,
            R.drawable.old_teaser, R.drawable.old_13, R.drawable.old_5, R.drawable.old_14,
            R.drawable.old_6, R.drawable.old_1, R.drawable.old_15, R.drawable.old_7};
    public Integer[] mem = {R.drawable.mem_1, R.drawable.mem_2, R.drawable.mem_teaser};

    public Integer[] use;


    // Constructor
    public ImageAdapter(Context c, String select) {
        mContext = c;
        switch (select) {
            case "INSTI":
                use = insti;
                break;
            case "KIAP":
                use = kiap;
                break;
            case "STUD":
                use = stud;
                break;
            case "OLD":
                use = old;
                break;
            case "MEM":
                use = mem;
                break;
        }
    }

    @Override
    public int getCount() {
        return use.length;
    }

    @Override
    public Object getItem(int position) {
        return use[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(use[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }

}