package com.aps.iitkconv.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
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
import static android.support.design.R.id.start;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    protected FrameLayout frameLayout;
    private Context mContext;
    private static int curTab = -1;


    ArrayList<Button> but;
    ArrayList<String> colors;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
//        frameLayout.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.exhort, null));
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

        //Handling button UI
        but = new ArrayList<Button>();
        but.add((Button) findViewById(R.id.button1));
        but.add((Button) findViewById(R.id.button2));
        but.add((Button) findViewById(R.id.button3));
        but.add((Button) findViewById(R.id.button4));
        but.add((Button) findViewById(R.id.button5));
        but.add((Button) findViewById(R.id.button6));
        but.add((Button) findViewById(R.id.button7));
        but.add((Button) findViewById(R.id.button8));
        but.add((Button) findViewById(R.id.button9));

        colors = new ArrayList<>();
        colors.add("#FDD017");
        colors.add("#8D38C9");
        colors.add("#4CC552");
        colors.add("#F88017");
        colors.add("#342D7E");
        colors.add("#3090C7");
        colors.add("#E56717");
        colors.add("#483C32");
        colors.add("#3b5998");

        for(int i=0;i<9;i++)
        {
            GradientDrawable rippleDrawable = (GradientDrawable)but.get(i).getBackground(); // assumes bg is a RippleDrawable
            rippleDrawable.setColor(Color.parseColor(colors.get(i)));
            but.get(i).setTransformationMethod(null);

        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();


        for(int i=0;i<9;i++)
        {
            final int finalI = i;
            but.get(i).setOnTouchListener(new View.OnTouchListener()
            {
                @Override
                public boolean onTouch(View v, MotionEvent event)
                {

                    GradientDrawable ri = (GradientDrawable)but.get(finalI).getBackground(); // assumes bg is a RippleDrawable
                    ri.setColorFilter(null);

                    if(event.getAction() == MotionEvent.ACTION_DOWN)
                    {
                        GradientDrawable rippleDrawable = (GradientDrawable)but.get(finalI).getBackground(); // assumes bg is a RippleDrawable
                        rippleDrawable.setColorFilter(new LightingColorFilter(0xFF000000, 0xFFAA0000));
                    }

                    if(event.getAction() == MotionEvent.ACTION_MOVE){


                        GradientDrawable rippleDrawable = (GradientDrawable)but.get(finalI).getBackground(); // assumes bg is a RippleDrawable
                        rippleDrawable.setColorFilter(null);

                    }
                    if (event.getAction() == MotionEvent.ACTION_UP)
                    {
                        GradientDrawable rippleDrawable = (GradientDrawable)but.get(finalI).getBackground(); // assumes bg is a RippleDrawable
                        rippleDrawable.setColorFilter(null);

                        if (finalI == 0)
                            startDrawerActivity(CardViewActivity.class, 50);
                        else if (finalI == 1)
                            startDrawerActivity(CardViewActivity.class, 1);
                        else if (finalI == 2)
                            startDrawerActivity(CardViewActivity.class, 4);
                        else if (finalI == 3)
                            startDrawerActivity(CardViewActivity.class, 3);
                        else if (finalI == 4)
                            startDrawerActivity(CardViewActivity.class, 5);
                        else if (finalI == 5)
                            startDrawerActivity(GalleryActivity.class, 8);
                        else if (finalI == 6)
                            startDrawerActivity(CardViewActivity.class, 9);
                        else if (finalI == 7)
                            startDrawerActivity(MapsActivity.class, 6);
                        else if (finalI == 8)
                        {
                            //Url for facebook group
                            String url = "https://www.facebook.com/Convocation-IIT-Kanpur-1350357441738753/";
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);
                        }
                    }
                    return true;
                }

                });


            }


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


    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_schedule)
        {
            startDrawerActivity(CardViewActivity.class,1);
        }

        else if (id == R.id.nav_home && curTab !=-1)
        {
            //startDrawerActivity(CardViewActivity.class,1);
//            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(intent);

//            finish();
            startDrawerActivity(MainActivity.class,2);
            curTab = -1;
        }
/*
        else if (id == R.id.nav_announce && curTab !=2)
        {
            startDrawerActivity(CardViewActivity.class,2);
        }
*/
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

        else if (id == R.id.nav_chief)
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

        else if (id == R.id.nav_useful)
        {
            startDrawerActivity(CardViewActivity.class,9);

        }

        else if (id == R.id.nav_links)
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

//                Intent intent = new Intent(MainActivity.this, activity);
                Intent intent = new Intent(getApplicationContext(), activity);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
                Bundle b = new Bundle();
                b.putInt("key", val);
                intent.putExtras(b);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }, 200);
    }


}
