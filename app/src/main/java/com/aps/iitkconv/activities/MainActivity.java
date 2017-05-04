package com.aps.iitkconv.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aps.iitconv.R;
import com.aps.iitkconv.models.DBHandler_Grad;
import com.aps.iitkconv.models.DataObject;
import com.aps.iitkconv.models.MyRecyclerViewAdapter;
import com.aps.iitkconv.models.Table_Awards;
import com.aps.iitkconv.models.Table_Contact;
import com.aps.iitkconv.models.Table_Grad_Students;
import com.aps.iitkconv.models.Table_Guest;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import static android.support.design.R.id.dialog;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    protected FrameLayout frameLayout;
    private Context mContext;
    private static int curTab = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setting application context so that it can be accessed from everywhere
        mContext = MainActivity.this;

        //Changing the action bar title
        this.setTitle(Html.fromHtml(getString(R.string.app_title)));

        frameLayout = (FrameLayout)findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.content_main, frameLayout);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Receive messages from GCM Firebase
        Bundle b = getIntent().getExtras();
        if(b != null)
        {
            String message = null;
            message = b.getString("message");
            if(message != null)
            {
                Log.d("GCM Message",message);
                DBHandler_Grad db = DBHandler_Grad.getInstance(this);
                db.addAnnouncement(db.sqldb, message);
                startDrawerActivity(CardViewActivity.class,2);
            }

        }
        //---------------------------------------

    }


    public static void clearChoice()
    {
        curTab = -1;
    }
    public static int getChoice()
    {
        return curTab;
    }
    public static void setChoice(int val)
    {
        curTab = val;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_schedule && curTab !=1)
        {
            startDrawerActivity(CardViewActivity.class,1);
        }

        else if (id == R.id.nav_announce && curTab !=2)
        {
            startDrawerActivity(CardViewActivity.class,2);
        }

        else if (id == R.id.nav_awards && curTab !=3)
        {
            startDrawerActivity(CardViewActivity.class,3);

        }

        else if (id == R.id.nav_grad && curTab !=4)
        {
            startDrawerActivity(CardViewActivity.class,4);

        }

        else if (id == R.id.nav_honorary && curTab !=5)
        {
            startDrawerActivity(CardViewActivity.class,5);

        }

        else if (id == R.id.nav_chief && curTab !=50)
        {
            startDrawerActivity(CardViewActivity.class,50);

        }

        else if (id == R.id.nav_map && curTab !=6)
        {
            startDrawerActivity(MapsActivity.class,6);

        }

        else if (id == R.id.nav_webcast && curTab !=7)
        {
            startDrawerActivity(WebcastActivity.class,7);

        }

        else if (id == R.id.nav_nostalgia && curTab !=8)
        {
            startDrawerActivity(GalleryActivity.class,8);

        }

        else if (id == R.id.nav_useful && curTab !=9)
        {
            startDrawerActivity(CardViewActivity.class,9);

        }

        else if (id == R.id.nav_links && curTab !=10)
        {
            startDrawerActivity(CardViewActivity.class,10);

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void startDrawerActivity(final Class activity, final int val)
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(MainActivity.this, activity);
                Bundle b = new Bundle();
                b.putInt("key", val);
                intent.putExtras(b);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }, 200);
    }

}
