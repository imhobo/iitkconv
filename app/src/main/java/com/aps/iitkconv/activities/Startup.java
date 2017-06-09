package com.aps.iitkconv.activities;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.aps.iitconv.R;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

/**
 * Created by imhobo on 2/5/17.
 */

public class Startup extends AppCompatActivity
{

    private Context mContext;

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;

    public Startup()
    {
        // this method fires only once per application start.
        // getApplicationContext returns null here

        Log.i("main", "Constructor fired");
    }

    @Override
    public void onCreate(Bundle saved)
    {
        super.onCreate(saved);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_startup);

        mContext = Startup.this;

        Log.i("main", "onCreate fired");
        askForUpdates();

    }


    protected void askForUpdates()
    {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);

//        builder.setTitle("Update");
//        builder.setMessage("Would you like to check for new data?");
//        builder.setCancelable(false);

//        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//
//            public void onClick(DialogInterface dialog, int which) {
//
//
//                dialog.dismiss();
//
//                //Check for all the new data
//                Sync s = new Sync(mContext);
//                s.checkForUpdates();
//
//            }
//        });

//        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                // Do nothing
//                dialog.dismiss();
//                addDelay();
//
//
//            }
//        });

//        AlertDialog alert = builder.create();
//        alert.show();
        //Check for all the new data

        addDelay();

    }

    protected void addDelay()
    {
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                Sync s = new Sync(mContext);
                s.checkForUpdates();
//                Intent i = new Intent(Startup.this, MainActivity.class);
//                startActivity(i);
                // close this activity

//                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}

