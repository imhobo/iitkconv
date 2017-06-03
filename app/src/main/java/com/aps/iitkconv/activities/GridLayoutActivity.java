package com.aps.iitkconv.activities;

/**
 * Created by ankitku on 02/06/17.
 */

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.aps.iitconv.R;
import com.aps.iitkconv.models.ImageAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class GridLayoutActivity extends MainActivity {

    Context ctx;
    final String TAG = "GridLayoutActivity";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        frameLayout.removeAllViews();
        frameLayout.setBackground(null);
        ctx = this.getApplicationContext();

        getLayoutInflater().inflate(R.layout.grid_layout, frameLayout);


        GridView gridView = (GridView) findViewById(R.id.grid_view);

        // Instance of ImageAdapter Class
        final String category = getIntent().getStringExtra(EXTRA_MESSAGE);
        gridView.setAdapter(new ImageAdapter(this, category));

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

        if ("MEM".equals(category)) {
            TextView tv1 = (TextView) findViewById(R.id.tv1);
            TextView tv2 = (TextView) findViewById(R.id.tv2);
            TextView tv3 = (TextView) findViewById(R.id.tv3);
            TextView tv4 = (TextView) findViewById(R.id.tv4);
            TextView tv5 = (TextView) findViewById(R.id.tv5);
            tv1.setVisibility(View.VISIBLE);
            tv2.setVisibility(View.VISIBLE);
            tv3.setVisibility(View.VISIBLE);
        //    tv4.setVisibility(View.VISIBLE);
            tv5.setVisibility(View.VISIBLE);


            CopyAssetsbrochure("mem_back_in_1960.pdf");
            CopyAssetsbrochure("mem_extracts_from_the_convocation_address_kelkar.pdf");
            CopyAssetsbrochure("mem_iitk_formative_years.pdf");
        //    CopyAssetsbrochure("mem_kelkar.pdf");
            CopyAssetsbrochure("mem_pk_kelkar.pdf");

            tv1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /** PDF reader code */
                    File file = new File(Environment.getExternalStorageDirectory() + "/" + "mem_extracts_from_the_convocation_address_kelkar.pdf");

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    try {
                        getApplicationContext().startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(ctx, "No Docx Viewer", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            tv2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /** PDF reader code */
                    File file = new File(Environment.getExternalStorageDirectory() + "/" + "mem_back_in_1960.pdf");

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    try {
                        getApplicationContext().startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(ctx, "No Docx Viewer", Toast.LENGTH_SHORT).show();
                    }
                }
            });


            tv3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /** PDF reader code */
                    File file = new File(Environment.getExternalStorageDirectory() + "/" + "mem_iitk_formative_years.pdf");

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    try {
                        getApplicationContext().startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(ctx, "No Pdf Viewer", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            tv5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /** PDF reader code */
                    File file = new File(Environment.getExternalStorageDirectory() + "/" + "mem_pk_kelkar.pdf");

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    try {
                        getApplicationContext().startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(ctx, "No Pdf Viewer", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }

    }

    //method to write the PDFs file to sd card
    private void CopyAssetsbrochure(String filename) {
        AssetManager assetManager = getAssets();
        String[] files = null;
        try {
            files = assetManager.list("");
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }
        for (int i = 0; i < files.length; i++) {
            String fStr = files[i];
            if (fStr.equalsIgnoreCase(filename)) {
                InputStream in = null;
                OutputStream out = null;
                try {
                    in = assetManager.open(files[i]);
                    out = new FileOutputStream(Environment.getExternalStorageDirectory() + "/" + files[i]);
                    copyFile(in, out);
                    in.close();
                    in = null;
                    out.flush();
                    out.close();
                    out = null;
                    break;
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

}