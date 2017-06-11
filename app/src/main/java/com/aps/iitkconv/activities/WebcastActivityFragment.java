package com.aps.iitkconv.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aps.iitconv.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class WebcastActivityFragment extends Fragment {

    public WebcastActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_webcast, container, false);
    }
}
