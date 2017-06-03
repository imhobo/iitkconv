package com.aps.iitkconv.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.aps.iitconv.R;
import com.aps.iitkconv.models.DataObject;
import com.aps.iitkconv.models.MyRecyclerViewAdapter;

import java.util.ArrayList;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

/**
 * Created by ankitkumar on 29/04/17.
 */

public class NostalgiaActivity extends MainActivity {

    int ch = -1;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "NostalgiaActivity";
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        frameLayout.removeAllViews();
        frameLayout.setBackground(null);
        getLayoutInflater().inflate(R.layout.card_view_award, frameLayout);

        //Handling back button
        ch = MainActivity.getChoice();
        MainActivity.setChoice(getIntent().getExtras().getInt("key"));


        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mLayoutManager = new LinearLayoutManager(NostalgiaActivity.this);
        ctx = this.getApplicationContext();

        populateCategories();
    }

    private void populateCategories() {

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);


        mAdapter = new MyRecyclerViewAdapter(getCats(), 69);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onBackPressed() {
        MainActivity.setChoice(ch);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.d(LOG_TAG + ">>>>>>>", "" + position);

                Intent intent = new Intent(ctx, GridLayoutActivity.class);
                switch (position) {
                    case 0:
                        intent.putExtra(EXTRA_MESSAGE, "INSTI");
                        break;
                    case 1:
                        intent.putExtra(EXTRA_MESSAGE, "KIAP");
                        break;
                    case 2:
                        intent.putExtra(EXTRA_MESSAGE, "STUD");
                        break;
                    case 3:
                        intent.putExtra(EXTRA_MESSAGE, "MEM");
                        break;
                    case 4:
                        intent.putExtra(EXTRA_MESSAGE, "OLD");
                        break;
                }

                startActivity(intent);
            }
        });
    }

    public ArrayList<DataObject> getCats() {
        ArrayList<DataObject> result = new ArrayList<DataObject>();
        DataObject g = new DataObject("Institute");
        Bitmap bitmp = ((BitmapDrawable) getResources().getDrawable(R.drawable.insti_teaser, null)).getBitmap();
        g.setmImg(bitmp);
        result.add(g);
        g = new DataObject("Kanpur Indo-American Program");
        bitmp = ((BitmapDrawable) getResources().getDrawable(R.drawable.kiap_teaser, null)).getBitmap();
        g.setmImg(bitmp);
        result.add(g);
        g = new DataObject("Student activities");
        bitmp = ((BitmapDrawable) getResources().getDrawable(R.drawable.stud_teaser, null)).getBitmap();
        g.setmImg(bitmp);
        result.add(g);
        g = new DataObject("Memorium");
        bitmp = ((BitmapDrawable) getResources().getDrawable(R.drawable.mem_teaser, null)).getBitmap();
        g.setmImg(bitmp);
        result.add(g);
        g = new DataObject("Past Convocations");
        bitmp = ((BitmapDrawable) getResources().getDrawable(R.drawable.old_teaser, null)).getBitmap();
        g.setmImg(bitmp);
        result.add(g);
        return result;
    }
}
