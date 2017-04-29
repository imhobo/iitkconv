package com.aps.iitconv;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.aps.iitkconv.models.DBHandler_Grad;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    protected FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Changing the action bar title
        this.setTitle(R.string.app_title);

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
        getMenuInflater().inflate(R.menu.main, menu);
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
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_schedule)
        {
            startDrawerActivity(CardViewActivity.class,1);
        }

        else if (id == R.id.nav_announce)
        {
            startDrawerActivity(CardViewActivity.class,2);

        }

        else if (id == R.id.nav_awards)
        {
            startDrawerActivity(CardViewActivity.class,3);

        }

        else if (id == R.id.nav_grad)
        {
            startDrawerActivity(CardViewActivity.class,4);

        }

        else if (id == R.id.nav_honorary)
        {
            startDrawerActivity(CardViewActivity.class,5);
        }


        // For Ankit
        else if (id == R.id.nav_map)
        {
            startDrawerActivity(MapsActivity.class,6);
        }

        //For Ankit
        else if (id == R.id.nav_webcast)
        {
            startDrawerActivity(WebcastActivity.class,1);
        }

        //For Pulkit
        else if (id == R.id.nav_nostalgia)
        {
            startDrawerActivity(GalleryActivity.class,1);
        }

        else if (id == R.id.nav_useful)
        {
            startDrawerActivity(CardViewActivity.class,9);
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
