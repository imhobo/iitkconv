package com.aps.iitkconv.activities;

/**
 * Created by imhobo on 31/3/17.
 */

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
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
import com.aps.iitkconv.models.Table_Schedule;

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


public class CardViewActivity extends MainActivity
{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "CardViewActivity";
    private DBHandler_Grad db;

    //An integer representing which tab was clicked to reach this activity
    int value = -1;

    //Keeps track of which program and dept was clicked
    private ArrayList<DataObject> programs;
    private ArrayList<DataObject> depts;
    private int program = -1, dept = -1;

    //Keeps track of which award was clicked
    private ArrayList<DataObject> awards;
    private final int AWARDS_WITH_PICS = 5;
    private int awardNum = -1;

    private Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Clearing the existing UI
        frameLayout.removeAllViews();

        b = getIntent().getExtras();
        if(b != null)
        {
            value = b.getInt("key");
        }

        displayData();
    }


    @Override
    protected void onResume()
    {
        super.onResume();

    }


    //Possibly the worst way to implement the back button feature.
    //Recreates the view when back is pressed
    @Override
    public void onBackPressed()
    {
        Log.d("CDA", "onBackPressed Called");

        if(value==1 || value ==2 || value ==5 || value == 9 || value == 50)
        {
            finish();
        }

        else if(value==3 && awardNum == -1)
        {
            finish();
        }

        else if(value==3 && awardNum > -1)
        {
            awardNum = -1;
            displayData();
        }

        else if(value==4 && program == -1)
        {
            finish();
        }

        else if(value==4 && program > -1 && dept == -1)
        {
            program = -1;
            displayData();
        }

        else if(value==4 && program > -1 && dept > -1)
        {
            dept = -1;
            displayData();
        }

    }


    //---------------------------------------------------------------Methods to get data-------------------------------------------------------

    //Get all events
    private ArrayList<DataObject> getSchedule()
    {
        ArrayList<Table_Schedule> events = new ArrayList<Table_Schedule>();
        ArrayList<DataObject> results = new ArrayList<DataObject>();

        events = (ArrayList) db.getSchedule();

        int size = events.size();
        Log.d("Size of events : ", String.valueOf(size));

        for (int i = 0 ; i< size; i++)
        {
            Table_Schedule t = events.get(i);
            DataObject obj= new DataObject(t.getEvent(),t.getVenue(), t.getDate(), t.getTime());
            results.add(obj);
        }

        return results;
    }

    //Get all announcements
    private ArrayList<DataObject> getAnnouncements()
    {
        ArrayList results = new ArrayList<DataObject>();
        ArrayList<String> tempHolder = new ArrayList<String>();
        tempHolder = (ArrayList) db.getAnnouncements();

        for (String s : tempHolder)
        {
            results.add(new DataObject(s));
        }

        return results;
    }

    //Get all contacts
    private ArrayList<DataObject> getContacts()
    {
        ArrayList<Table_Contact> contacts = new ArrayList<Table_Contact>();
        ArrayList<DataObject> results = new ArrayList<DataObject>();

        contacts = (ArrayList<Table_Contact>) db.getContacts();

        int size = contacts.size();
        Log.d("Size of Contacts : ", String.valueOf(size));

        for (int i = 0 ; i< size; i++)
        {
            Table_Contact t = contacts.get(i);
            DataObject obj= new DataObject(t.getName(),t.getNumber(), t.getTransport());
            //Log.d("getStudents2",String.valueOf(t.getId())+t.getEvent()+t.getName()+award+t.getTime()+t.getDept()+t.getProgram()+t.getYear());
            results.add(obj);
        }
        return results;
    }

    //Get all Guests of a certain type
    private ArrayList<DataObject> getGuests(String type)
    {
        ArrayList<Table_Guest> g = new ArrayList<Table_Guest>();
        ArrayList<DataObject> results = new ArrayList<DataObject>();

        g = (ArrayList<Table_Guest>) db.getGuests(type);

        int size = g.size();

        for (int i = 0 ; i< size; i++)
        {
            Table_Guest t = g.get(i);

            Bitmap bmp = db.getImage(t.getPicture());
           /*
            if(bmp==null)
                Log.d("image from DB", "NULL");
            else
                Log.d("image from DB", "NOT NULL");
            */
            DataObject obj= new DataObject(bmp, t.getName(),t.getTitle(), t.getYear(), t.getPicture(), t.getDescription());
            //Log.d("getStudents2",String.valueOf(t.getId())+t.getEvent()+t.getName()+award+t.getTime()+t.getDept()+t.getProgram()+t.getYear());

            results.add(obj);
        }
        return results;
    }

    //Get all Awards
    private ArrayList<DataObject> getAwards()
    {
        ArrayList results = new ArrayList<DataObject>();
        ArrayList<String> tempHolder = new ArrayList<String>();
        tempHolder = (ArrayList) db.getAwards2();

        for (String i : tempHolder)
        {
            results.add(new DataObject(i));
        }

        return results;
    }


    //Get all Programs
    private ArrayList<DataObject> getPrograms()
    {
        ArrayList results = new ArrayList<DataObject>();
        ArrayList<String> tempHolder = new ArrayList<String>();
        tempHolder = (ArrayList) db.getProgram1();

        for (String i : tempHolder)
        {
            results.add(new DataObject(i));
        }

        return results;
    }

    //Get Departments for Graduating Students
    private ArrayList<DataObject> getDept1(String program)
    {
        ArrayList results = new ArrayList<DataObject>();
        ArrayList<String> tempHolder = new ArrayList<String>();
        tempHolder = (ArrayList) db.getDept1(program);

        for (String i : tempHolder)
        {
            results.add(new DataObject(i));
        }

        return results;
    }

    //Get Student for Graduating section
    private ArrayList<DataObject> getStudents1(String program, String dept)
    {
        ArrayList<Table_Grad_Students> students = new ArrayList<Table_Grad_Students>();
        ArrayList<DataObject> results = new ArrayList<DataObject>();

        String query = "SELECT * FROM Table_Grad_Students WHERE program = " + "'" + program + "'" + " AND dept = " + "'" + dept + "'";
        students = (ArrayList) db.runSelectQuery1(query);

        int size = students.size();
        Log.d("Size of students : ", String.valueOf(size));

        for (int i = 0 ; i< size; i++)
        {
            Table_Grad_Students t = students.get(i);
            DataObject obj= new DataObject(t.getName(),t.getRoll(), t.getAdvisers(), t.getDescription());
            results.add(obj);
        }

        return results;
    }

    //Get student for Awards section
    private ArrayList<DataObject> getStudents2(String award)
    {
        ArrayList<Table_Awards> students = new ArrayList<Table_Awards>();
        ArrayList<DataObject> results = new ArrayList<DataObject>();

        students = (ArrayList) db.runSelectQuery2(award);

        int size = students.size();
        Log.d("Size of students : ", String.valueOf(size));

        for (int i = 0 ; i< size; i++)
        {
            Table_Awards t = students.get(i);
            DataObject obj= new DataObject(t.getRoll(), t.getName(), award, t.getDescription(), t.getComment(), t.getProgram(), t.getYear());
            //Log.d("getStudents2",String.valueOf(t.getId())+t.getEvent()+t.getName()+award+t.getTime()+t.getDept()+t.getProgram()+t.getYear());
            results.add(obj);
        }
        return results;
    }

    //--------------------------------------------------------------------------------------------------------------------------------------------------------------

    protected void displayData()
    {

        b = getIntent().getExtras();
        if(b != null)
            value = b.getInt("key");


        //Clearing the existing UI
        frameLayout.removeAllViews();


        //Instance used to read data
        db = DBHandler_Grad.getInstance(this);

        //Set different card views here.
        if(value == 3 && awardNum > -1)
        {
            getLayoutInflater().inflate(R.layout.card_view_award, frameLayout);
        }
        else
        {
            getLayoutInflater().inflate(R.layout.card_view_generic, frameLayout);
        }


        //This should be done only after getLayoutInflater is called on frameLayout
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(CardViewActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);

//        Log.d("Value","val=" + value) ;
        // Schedule
        if(value==1)
            mAdapter = new MyRecyclerViewAdapter(getSchedule(),1);

        // Announcements
        else if(value==2)
            mAdapter = new MyRecyclerViewAdapter(getAnnouncements(),2);


        //List of Awards
        else if(value==3 && awardNum == -1)
        {
            awards = getAwards();
            mAdapter = new MyRecyclerViewAdapter(awards, 3);
        }

        //Some Award clicked
        else if(value==3 && awardNum > -1)
        {
            String curAward = awards.get(awardNum).getmText1();
            String curDesc = db.getDesc2(curAward);
            ArrayList<DataObject> students = getStudents2(curAward);

            TextView award = (TextView) findViewById(R.id.textViewA1);
            TextView desc = (TextView) findViewById(R.id.textViewA2);
            award.setText(curAward);
            //desc.setText(curDesc);


            if(awardNum<AWARDS_WITH_PICS)
                mAdapter = new MyRecyclerViewAdapter(students, 30);
            else
                mAdapter = new MyRecyclerViewAdapter(students, 31);
        }

        //Honourary Degrees and Chief Guests
        else if(value==5 || value == 50)
        {
            if(value == 5)
                mAdapter = new MyRecyclerViewAdapter(getGuests("H"), 5);
            else if(value == 50)
                mAdapter = new MyRecyclerViewAdapter(getGuests("C"), 50);
        }

        //Taxi Contacts
        else if(value==9)
        {
            ArrayList<Table_Contact> contacts = (ArrayList<Table_Contact>) db.getContacts();
            mAdapter = new MyRecyclerViewAdapter(getContacts(), 9);
        }



        //List of Programs for Graduating Students
        else if(value==4 && program == -1)
        {
            programs = getPrograms();
            mAdapter = new MyRecyclerViewAdapter(programs,4);
        }

        //List of Studetns for Graduating Students when Program already clicked
        else if(value == 4 && program > -1 && dept == -1)
        {
            String curDep = programs.get(program).getmText1();
            CardViewActivity.this.setTitle(curDep);
            depts = getDept1(curDep);
            mAdapter = new MyRecyclerViewAdapter(depts,40);
        }

        //List of Studetns for Graduating Students when Program and Dept already clicked
        else if(value == 4 && program > -1 && dept > -1)
        {
            String curDep = programs.get(program).getmText1();
            String curBr = depts.get(dept).getmText1();
            CardViewActivity.this.setTitle(curBr);
            ArrayList<DataObject> students = getStudents1(curDep, curBr);
            //Log.d("Branch", programs.get(program).getmText1() + ":"+ depts.get(dept).getmText1());
            if(!curDep.equals("Ph.D."))
                mAdapter = new MyRecyclerViewAdapter(students,400);
            else
                mAdapter = new MyRecyclerViewAdapter(students,401);

        }

        mRecyclerView.setAdapter(mAdapter);



        ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v)
            {
                Log.i(LOG_TAG, " Clicked on Item " + position );

                if(value == 3 && awardNum == -1)
                {
                    awardNum = position;
                    displayData();
                }

                else if(value == 3 && awardNum > -1)
                {

                    //Click on the student name
                }

                else if(value==4 && program == -1)
                {
                    program = position;
                    displayData();
                }

                else if(value==4 && program > -1 && dept == -1)
                {
                    dept = position;
                    displayData();
                }

                else if(value==4 && program > -1 && dept > -1)
                {
                    //Click on the student name
                }


            }
        });

    }


}
