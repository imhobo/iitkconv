package com.aps.iitkconv.activities;

/**
 * Created by imhobo on 31/3/17.
 */

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.aps.iitconv.R;
import com.aps.iitkconv.models.DBHandler_Grad;
import com.aps.iitkconv.models.DataObject;
import com.aps.iitkconv.models.MyRecyclerViewAdapter;
import com.aps.iitkconv.models.Table_Awards;
import com.aps.iitkconv.models.Table_Contact;
import com.aps.iitkconv.models.Table_Grad_Students;
import com.aps.iitkconv.models.Table_Guest;
import com.aps.iitkconv.models.Table_Prev_Rec;
import com.aps.iitkconv.models.Table_Schedule;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;


public class CardViewActivity extends MainActivity {

    private static String LOG_TAG = "CardViewActivity";
    //An integer representing which tab was clicked to reach this activity
    int value = -1;
    //Keep track whether Previous Recipients was clicked for Honourary,Chief guest or President Gold tab
    boolean prevHon = false;
    boolean prevChief = false;
    boolean prevPres = false;
    //Handling the search button
    boolean hasSearchedGrad = false;
    boolean hasSearchedAwards = false;
    String query = "";
    //Handling the back button
    int ch = -1;
    //Schedule page 1 or 2
    int schedule_page = 1;
    int chief_page = 1;
    int hon_page = 1;
    String date = "";
    String guestNameC = "";
    String guestNameH = "";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DBHandler_Grad db;
    //Keeps track of which program and dept was clicked
    private ArrayList<DataObject> programs;
    private ArrayList<DataObject> depts;
    private int program = -1, dept = -1;
    //Keeps track of which award was clicked
    private ArrayList<DataObject> awards;
    private int awardNum = -1;
    //Search
    private MenuItem searchMenuItem;
    private SearchView searchView;
    private Context mContext;
    private Bundle b;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Maintaining context
        mContext = this;

        //Clearing the existing UI
        frameLayout.removeAllViews();

//        Log.d("ch before init", String.valueOf(ch));

        b = getIntent().getExtras();
        if (b != null)
            value = b.getInt("key");

        //Handling back button
        ch = MainActivity.getChoice();
        MainActivity.setChoice(value);

//        Log.d("ch onCreate", String.valueOf(ch));
//        Log.d("value onCreate", String.valueOf(value));

        displayData();
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d("onResume", String.valueOf(value));

//        displayData();
        ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i(LOG_TAG, " Clicked on Item " + position);
                Log.i(LOG_TAG, " Value " + value);

                if (value == 1 && schedule_page == 1) {

                    if (position == 0)
                        date = "15 June";
                    else if (position == 1)
                        date = "16 June";
                    schedule_page = 2;
                    displayData();
                } else if (value == 3 && awardNum == -1) {
                    awardNum = position;
                    displayData();
                } else if (value == 3 && awardNum == 0 && position == (mAdapter.getItemCount() - 1)) {

                    Log.i(LOG_TAG, " Clicked on PrevPres " + position);
                    prevPres = true;
                    displayData();

                } else if (value == 4 && program == -1) {
                    program = position;
                    displayData();
                } else if (value == 4 && program > -1 && dept == -1) {
                    dept = position;
                    displayData();
                } else if (value == 4 && program > -1 && dept > -1) {
                    //Click on the student name
                } else if (value == 5 && position == (mAdapter.getItemCount() - 1) && hon_page == 1) {
//                    Log.i(LOG_TAG, " Clicked on HonPrevious " + position );
                    prevHon = true;
                    hon_page = 2;
                    displayData();

                } else if (value == 5 && hon_page == 1) {
//                    Log.i(LOG_TAG, " Clicked on HonPrevious " + position );

                    if (position == 0)
                        guestNameH = "Professor Ajay Kumar Sood";
                    else if (position == 1)
                        guestNameH = "Professor Mriganka Sur";
                    else if (position == 2)
                        guestNameH = "P.T Usha";
                    else if (position == 3)
                        guestNameH = "Dr. Monkombu Sambasivan Swaminathan";

                    hon_page = 2;
                    displayData();

                } else if (value == 50 && position == (mAdapter.getItemCount() - 1) && chief_page == 1) {
//                    Log.i(LOG_TAG, " Clicked on ChiefPrevious " + position );
                    prevChief = true;
                    chief_page = 2;
                    displayData();
                } else if (value == 50 && chief_page == 1) {
//                    Log.i(LOG_TAG, " Clicked on HonPrevious " + position );

                    if (position == 0)
                        guestNameC = "Mr. Natarajan Chandrasekaran ";
                    else if (position == 1)
                        guestNameC = "Dr. Clayton Daniel Mote, Jr.";
                    chief_page = 2;
                    displayData();

                }

                //Important Links
                else if (value == 10) {
                    String url = ((MyRecyclerViewAdapter) mAdapter).getDataSet().get(position).getmText2();
                    Log.d("url", url);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);

                } else if (value == 9) {
                    if (position == 0 || position == 12 || position == 15) return;

                    int tpos = -1;
                    if (position > 0 && position < 12)
                        tpos = position - 1;
                    else if (position == 13 || position == 14)
                        tpos = position - 2;
                    else
                        tpos = position - 3;
                    String phone = db.getContacts().get(tpos).getNumber();
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + phone));
                    mContext.startActivity(intent);
                }

            }
        });

    }


    //Possibly the worst way to implement the back button feature.
    //Recreates the view when back is pressed
    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        //Handling the back button


//        Log.d("ch onBack", String.valueOf(ch));
//        Log.d("value onBack", String.valueOf(value));

        if (value == 4 && hasSearchedGrad) {
            hasSearchedGrad = false;
            displayData();
            return;
        }

        if (value == 3 && hasSearchedAwards) {
            hasSearchedAwards = false;
            displayData();
            return;
        }

        if ((value == 1 && schedule_page == 1) || value == 2 || (value == 5 && !prevHon && hon_page == 1) || value == 9 || value == 10 || (value == 50 && !prevChief && chief_page == 1)) {
            MainActivity.setChoice(ch);
            finish();
            return;

        } else if (value == 50 && chief_page == 2) {
            chief_page = 1;
            prevChief = false;
            displayData();
        } else if (value == 5 && hon_page == 2) {
            hon_page = 1;
            prevHon = false;
            displayData();
        } else if (value == 1 && schedule_page == 2) {
            schedule_page = 1;
            displayData();
        } else if (value == 5 && prevHon) {
            prevHon = false;
            displayData();
        } else if (value == 50 && prevChief) {
            prevChief = false;
            displayData();
        } else if (value == 3 && awardNum == -1) {
            MainActivity.setChoice(ch);
            finish();
            return;
        } else if (value == 3 && awardNum > -1 && !prevPres) {
            awardNum = -1;
            displayData();
        } else if (value == 3 && awardNum > -1 && prevPres) {
            prevPres = false;
            displayData();
        } else if (value == 4 && program == -1) {
            MainActivity.setChoice(ch);
            finish();
            return;
        } else if (value == 4 && program > -1 && dept == -1) {
            program = -1;
            displayData();
        } else if (value == 4 && program > -1 && dept > -1) {
            dept = -1;
            displayData();
        }


    }


    //---------------------------------------------------------------Methods to get data-------------------------------------------------------

    //Get all events
    private ArrayList<DataObject> getSchedule(String date) {
        ArrayList<Table_Schedule> events = new ArrayList<Table_Schedule>();
        ArrayList<DataObject> results = new ArrayList<DataObject>();

        events = (ArrayList) db.getSchedule(date);

        int size = events.size();
        Log.d("Size of events : ", String.valueOf(size));

        for (int i = 0; i < size; i++) {
            Table_Schedule t = events.get(i);
            DataObject obj = new DataObject(t.getEvent(), t.getVenue(), t.getDate(), t.getTime());
            results.add(obj);
        }

        return results;
    }

    //Create first page of schedule
    private ArrayList<DataObject> schedule_page1() {
        ArrayList<DataObject> results = new ArrayList<DataObject>();

        DataObject obj = new DataObject("Session - 1", "Auditorium, IIT Kanpur", "15 June, 2017", "09:00 am to 13:35 pm");
        results.add(obj);
        obj = new DataObject("Session - 2", "Auditorium, IIT Kanpur", "16 June, 2017", "09:00 am to 13:35 pm");
        results.add(obj);

        return results;
    }

    //Create first page of chief_guests
    private ArrayList<DataObject> chief_page1() {
        ArrayList<DataObject> results = new ArrayList<DataObject>();

        DataObject obj = new DataObject("", "Mr. Natarajan Chandrasekaran", "", "", "Chairman, TATA SONS", "15 June, 2017\nSession - 1\nAuditorium, IIT Kanpur");

        int i = mContext.getResources().getIdentifier("img1", "raw", mContext.getPackageName());
        InputStream input = mContext.getResources().openRawResource(i);
        Bitmap myBitmap = BitmapFactory.decodeStream(input);
        obj.setmImg(myBitmap);
        results.add(obj);

        obj = new DataObject("", "Professor C. D. Mote, Jr.", "", "", "President, National Academy of Engineering, USA", "16 June, 2017\nSession - 2\nAuditorium, IIT Kanpur");
        i = mContext.getResources().getIdentifier("img2", "raw", mContext.getPackageName());
        input = mContext.getResources().openRawResource(i);
        myBitmap = BitmapFactory.decodeStream(input);
        obj.setmImg(myBitmap);
        results.add(obj);

        obj = new DataObject("Previous Guests");
        results.add(obj);

        return results;
    }

    //Create first page of hon_guests
    private ArrayList<DataObject> hon_page1() {
        ArrayList<DataObject> results = new ArrayList<DataObject>();

        DataObject obj = new DataObject("", "Professor Ajay Kumar Sood", "", "", "HONORARY DEGREE (HONORIS CAUSA)\nFiftieth Convocation, 2017");

        int i = mContext.getResources().getIdentifier("hon1", "raw", mContext.getPackageName());
        InputStream input = mContext.getResources().openRawResource(i);
        Bitmap myBitmap = BitmapFactory.decodeStream(input);
        obj.setmImg(myBitmap);
        results.add(obj);

        obj = new DataObject("", "Professor Mriganka Sur", "", "", "HONORARY DEGREE (HONORIS CAUSA)\nFiftieth Convocation, 2017");
        i = mContext.getResources().getIdentifier("hon2", "raw", mContext.getPackageName());
        input = mContext.getResources().openRawResource(i);
        myBitmap = BitmapFactory.decodeStream(input);
        obj.setmImg(myBitmap);
        results.add(obj);

        obj = new DataObject("", "Ms P.T Usha", "", "", "HONORARY DEGREE (HONORIS CAUSA)\nFiftieth Convocation, 2017");
        i = mContext.getResources().getIdentifier("hon3", "raw", mContext.getPackageName());
        input = mContext.getResources().openRawResource(i);
        myBitmap = BitmapFactory.decodeStream(input);
        obj.setmImg(myBitmap);
        results.add(obj);

        obj = new DataObject("", "Dr. M S Swaminathan ", "", "", "HONORARY DEGREE (HONORIS CAUSA)\nFiftieth Convocation, 2017");
        i = mContext.getResources().getIdentifier("hon4", "raw", mContext.getPackageName());
        input = mContext.getResources().openRawResource(i);
        myBitmap = BitmapFactory.decodeStream(input);
        obj.setmImg(myBitmap);
        results.add(obj);

        obj = new DataObject("Previous Recipients");
        results.add(obj);

        return results;
    }

    //Get all links
    private ArrayList<DataObject> getLinks() {
        ArrayList<Pair<String, String>> links = new ArrayList<Pair<String, String>>();
        ArrayList<DataObject> results = new ArrayList<DataObject>();

        links = (ArrayList) db.getLinks();

        int size = links.size();

        for (int i = 0; i < size; i++) {
            Pair<String, String> t = links.get(i);
            DataObject obj = new DataObject(t.first, t.second);
            results.add(obj);
        }
        return results;
    }

    //Get all announcements
    private ArrayList<DataObject> getAnnouncements() {
        ArrayList results = new ArrayList<DataObject>();
        ArrayList<String> tempHolder = new ArrayList<String>();
        tempHolder = (ArrayList) db.getAnnouncements();

        for (String s : tempHolder) {
            results.add(new DataObject(s));
        }
        if (tempHolder.size() == 0) {

            TextView txt1 = new TextView(CardViewActivity.this);
            txt1.setText("No announcements yet.");
            txt1.setGravity(Gravity.CENTER_HORIZONTAL);
            frameLayout.addView(txt1);
        }

        return results;
    }

    //Get all contacts
    private ArrayList<DataObject> getContacts() {
        ArrayList<Table_Contact> contacts = new ArrayList<Table_Contact>();
        ArrayList<DataObject> results = new ArrayList<DataObject>();

        contacts = (ArrayList<Table_Contact>) db.getContacts();

        int size = contacts.size();
        Log.d("Size of Contacts : ", String.valueOf(size));

        for (int i = 0; i < size; i++) {
            Table_Contact t = contacts.get(i);
            DataObject obj = new DataObject(t.getName(), t.getNumber(), t.getTransport());
            //Log.d("getStudents2",String.valueOf(t.getId())+t.getEvent()+t.getName()+award+t.getTime()+t.getDept()+t.getProgram()+t.getYear());
            results.add(obj);
        }

        DataObject obj = new DataObject("Volunteers", "");
        results.add(0, obj);
        obj = new DataObject("Health Center & Security", "");
        results.add(12, obj);
        obj = new DataObject("Taxi", "");
        results.add(15, obj);


        return results;
    }

    //Get all Guests of a certain type
    private ArrayList<DataObject> getGuests(String type, String guestName) {
        ArrayList<Table_Guest> g = new ArrayList<Table_Guest>();
        ArrayList<DataObject> results = new ArrayList<DataObject>();

        g = (ArrayList<Table_Guest>) db.getGuests(type);

        int size = g.size();

        for (int i = 0; i < size; i++) {
            Table_Guest t = g.get(i);

            Bitmap bmp = db.getImage(t.getPicture());
           /*
            if(bmp==null)
                Log.d("image from DB", "NULL");
            else
                Log.d("image from DB", "NOT NULL");
            */
            DataObject obj = new DataObject(bmp, t.getName(), t.getTitle(), t.getYear(), t.getPicture(), t.getDescription());
            //Log.d("getStudents2",String.valueOf(t.getId())+t.getEvent()+t.getName()+award+t.getTime()+t.getDept()+t.getProgram()+t.getYear());

            if (t.getName().equals(guestName))
                results.add(obj);
        }


        return results;
    }

    //Get all Awards
    private ArrayList<DataObject> getAwards() {
        ArrayList results = new ArrayList<DataObject>();
        ArrayList<String> tempHolder = new ArrayList<String>();
        tempHolder = (ArrayList) db.getAwards2();

        for (String i : tempHolder) {
            int num = db.getStudentCountInAward(i);
            if (!i.equals(""))
                results.add(new DataObject(i, String.valueOf(num)));
        }

        return results;
    }


    //Get all Programs
    private ArrayList<DataObject> getPrograms() {
        ArrayList results = new ArrayList<DataObject>();
        ArrayList<String> tempHolder = new ArrayList<String>();
        tempHolder = (ArrayList) db.getProgram1();

        int k = 0;
        for (String i : tempHolder) {
            int res = db.getStudentCountInProgram(i);
            if (!i.equals(""))
                results.add(new DataObject(i, String.valueOf(res)));
//            Log.d("Programs : ", i + " : " + k);
            k++;
        }
//        Log.d("Size of k : ", String.valueOf(k));
//        Log.d("Size of program1 : ", String.valueOf(results.size()));

        return results;
    }

    //Get Departments for Graduating Students
    private ArrayList<DataObject> getDept1(String program) {
        ArrayList results = new ArrayList<DataObject>();
        ArrayList<String> tempHolder = new ArrayList<String>();
        tempHolder = (ArrayList) db.getDept1(program);

        for (String i : tempHolder) {
            int res = db.getStudentCountInDept(program, i);
            results.add(new DataObject(i, String.valueOf(res)));
        }

        return results;
    }

    //Get Student for Graduating section
    private ArrayList<DataObject> getStudents1(String program, String dept) {
        ArrayList<Table_Grad_Students> students = new ArrayList<Table_Grad_Students>();
        ArrayList<DataObject> results = new ArrayList<DataObject>();

        String query = "SELECT * FROM Table_Grad_Students WHERE program = " + "'" + program + "'" + " AND dept = " + "'" + dept + "'";
        students = (ArrayList) db.runSelectQuery1(query);

        int size = students.size();
        Log.d("Size of students : ", String.valueOf(size));

        for (int i = 0; i < size; i++) {
            Table_Grad_Students t = students.get(i);
            DataObject obj = new DataObject(t.getName(), t.getRoll(), t.getAdvisers(), t.getDescription());
            results.add(obj);
        }

        return results;
    }

    //Get student for Awards section
    private ArrayList<DataObject> getStudents2(String award) {
        ArrayList<Table_Awards> students = new ArrayList<Table_Awards>();
        ArrayList<DataObject> results = new ArrayList<DataObject>();

        students = (ArrayList) db.runSelectQuery2(award);

        int size = students.size();
        Log.d("Size of students : ", String.valueOf(size));

        for (int i = 0; i < size; i++) {
            Table_Awards t = students.get(i);

            Bitmap bmp = db.getImage(t.getPicture());
           /*
            if(bmp==null)
                Log.d("image from DB", "NULL");
            else
                Log.d("image from DB", "NOT NULL");
            */

            DataObject obj = new DataObject(bmp, t.getRoll(), t.getName(), award, t.getDescription(), t.getComment(), t.getProgram(), t.getYear());

            Log.d("Checking values : ", "Roll-" + t.getRoll() + ";" + "Name-" + t.getName() + ";" + "Award-" + award + ";" + "Desc-" + t.getDescription() +
                    ";" + "Comment-" + t.getComment() + ";" + "Program-" + t.getProgram() + ";" + "Dept-" + t.getDept() + ";" + "Year-" + t.getYear());
            results.add(obj);
        }

//        Previous Recipients for award
        if (awardNum == 0 && !hasSearchedAwards) {
            DataObject obj = new DataObject("Previous Recipients");
            results.add(obj);
        }

        return results;
    }
    //-------------------------------------------------------------------Get searched data-------------------------------------------------------------------------

    protected ArrayList<DataObject> getSearchedGrad(String q) {

        ArrayList<Table_Grad_Students> students = new ArrayList<Table_Grad_Students>();
        ArrayList<DataObject> results = new ArrayList<DataObject>();

        String query = "SELECT * FROM Table_Grad_Students WHERE name like " + "'" + "%" + q + "%" + "'";
        students = (ArrayList) db.runSelectQuery1(query);

        int size = students.size();
        Log.d("Size of students : ", String.valueOf(size));

        for (int i = 0; i < size; i++) {
            Table_Grad_Students t = students.get(i);
            DataObject obj = new DataObject(t.getName(), t.getRoll(), t.getAdvisers(), t.getDescription(), t.getProgram(), t.getDept());

            Log.d("Program : ", t.getProgram());
            Log.d("Program : ", t.getName());
            Log.d("Program : ", t.getAdvisers());
            Log.d("Program : ", t.getDescription());
            Log.d("Program : ", t.getProgram());
            Log.d("Program : ", t.getDept());

            results.add(obj);
        }

        return results;


    }

    protected ArrayList<DataObject> getSearchedAwards(String q) {

        ArrayList<Table_Awards> students = new ArrayList<Table_Awards>();
        ArrayList<DataObject> results = new ArrayList<DataObject>();

        students = (ArrayList) db.getStudentsbyName(q);

        int size = students.size();
        Log.d("Size of students : ", String.valueOf(size));

        for (int i = 0; i < size; i++) {
            Table_Awards t = students.get(i);

            Bitmap bmp = db.getImage(t.getPicture());
            DataObject obj = new DataObject(bmp, t.getRoll(), t.getName(), t.getAward(), t.getDescription(), t.getComment(), t.getProgram(), t.getDept(), t.getYear(), t.getPicture());

            Log.d("Name : ", t.getName());
            Log.d("Award : ", t.getAward());
            results.add(obj);
        }

        return results;


    }


    //--------------------------------------------------------------------------------------------------------------------------------------------------------------

    protected void displayData() {

//        Log.d("displayData init", String.valueOf(value));
//        b = getIntent().getExtras();
//        if(b != null)
//            value = b.getInt("key");

//        Log.d("displayData after init", String.valueOf(value));

        //Clearing the existing UI
        frameLayout.removeAllViews();


        //Instance used to read data
        db = DBHandler_Grad.getInstance(this);

        //Set different card views here.
        if ((value == 3 && awardNum > -1 && !prevPres && !hasSearchedAwards) || (chief_page == 1 && value == 50) || (hon_page == 1 && value == 5)) {
            getLayoutInflater().inflate(R.layout.card_view_award, frameLayout);
        } else {
            getLayoutInflater().inflate(R.layout.card_view_generic, frameLayout);
        }


        //This should be done only after getLayoutInflater is called on frameLayout
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(CardViewActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);

//        Log.d("Value","val=" + value) ;
        // Schedule
        if (value == 1) {
            this.setTitle("Schedule");
            if (schedule_page == 1) {
                mAdapter = new MyRecyclerViewAdapter(schedule_page1(), 1);
            } else if (schedule_page == 2)
                mAdapter = new MyRecyclerViewAdapter(getSchedule(date), 1);

        }


        //Searched in awards
        else if (value == 3 && hasSearchedAwards) {
            this.setTitle(Html.fromHtml(getString(R.string.app_title)));
            mAdapter = new MyRecyclerViewAdapter(getSearchedAwards(query), 1000);
        }

        //Searched for grad students
        else if (value == 4 && hasSearchedGrad) {
            this.setTitle(Html.fromHtml(getString(R.string.app_title)));
            mAdapter = new MyRecyclerViewAdapter(getSearchedGrad(query), 999);
        }

        // Announcements
        else if (value == 2) {
            mAdapter = new MyRecyclerViewAdapter(getAnnouncements(), 2);
        }

        //List of Awards
        else if (value == 3 && awardNum == -1) {
            this.setTitle("Medals");
            awards = getAwards();
            mAdapter = new MyRecyclerViewAdapter(awards, 3);
        }

        //Some Award clicked
        else if (value == 3 && awardNum > -1 && !prevPres) {
            String curAward = awards.get(awardNum).getmText1();
            String curDesc = db.getDesc2(curAward);
            ArrayList<DataObject> students = getStudents2(curAward);

            TextView award = (TextView) findViewById(R.id.textViewA1);
            TextView desc = (TextView) findViewById(R.id.textViewA2);
            award.setText(curAward);
            //desc.setText(curDesc);

            //If a student with a certain award has a picture associated, then we assume that everyone in that category has a picture
            String imgName = db.getImageName(students.get(0).getmText2(), curAward);

            if (!imgName.equals(""))
                mAdapter = new MyRecyclerViewAdapter(students, 30);
            else
                mAdapter = new MyRecyclerViewAdapter(students, 31);
        }

        //Prev Recipient in Pres Gold Medal
        else if (value == 3 && awardNum == 0 && prevPres) {
            this.setTitle(Html.fromHtml(getString(R.string.app_title)));
            mAdapter = new MyRecyclerViewAdapter(getPrevPresExcel(), 300);

        }

        //Honourary Degrees and Chief Guests
        else if ((value == 5 && !prevHon) || (value == 50 && !prevChief)) {
            if (value == 5) {
                this.setTitle("Honorary");
                if (hon_page == 1) {
                    TextView award = (TextView) findViewById(R.id.textViewA1);
                    award.setText("Honorary Degrees");
                    mAdapter = new MyRecyclerViewAdapter(hon_page1(), 519);
                } else
                    mAdapter = new MyRecyclerViewAdapter(getGuests("H", guestNameH), 5);
            } else if (value == 50) {
                this.setTitle(Html.fromHtml(getString(R.string.app_title)));
                if (chief_page == 1) {
                    TextView award = (TextView) findViewById(R.id.textViewA1);
                    award.setText("Chief Guests");
                    mAdapter = new MyRecyclerViewAdapter(chief_page1(), 509);
                } else
                    mAdapter = new MyRecyclerViewAdapter(getGuests("C", guestNameC), 50);

            }
        }
        //Honourary Degrees and Chief Guests with Prev Recipients
        else if ((value == 5 && prevHon) || (value == 50 && prevChief)) {
            this.setTitle(Html.fromHtml(getString(R.string.app_title)));
            if (value == 5)
                mAdapter = new MyRecyclerViewAdapter(getPrevHonExcel(), 51);
            else if (value == 50)
                mAdapter = new MyRecyclerViewAdapter(getPrevChiefExcel(), 501);
        }

        //Taxi Contacts
        else if (value == 9) {
            this.setTitle("Contacts");
            mAdapter = new MyRecyclerViewAdapter(getContacts(), 9);
        }


        //List of Programs for Graduating Students with the number of students in each of them.
        else if (value == 4 && program == -1) {

            CardViewActivity.this.setTitle("Degrees");
            programs = getPrograms();
            Log.d("Size of Programs : ", String.valueOf(programs.size()));
            mAdapter = new MyRecyclerViewAdapter(programs, 4);

        }

        //List of Students for Graduating Students when Program already clicked with the number of students in each of them.
        else if (value == 4 && program > -1 && dept == -1) {
            String curDep = programs.get(program).getmText1();
            CardViewActivity.this.setTitle(curDep);
            depts = getDept1(curDep);
            mAdapter = new MyRecyclerViewAdapter(depts, 40);
        }

        //List of Students for Graduating Students when Program and Dept already clicked
        else if (value == 4 && program > -1 && dept > -1) {
            String curDep = programs.get(program).getmText1();
            String curBr = depts.get(dept).getmText1();
            CardViewActivity.this.setTitle(curDep + " -> " + curBr);
            ArrayList<DataObject> students = getStudents1(curDep, curBr);
            //Log.d("Branch", programs.get(program).getmText1() + ":"+ depts.get(dept).getmText1());
            if (!curDep.equals("PhD"))
                mAdapter = new MyRecyclerViewAdapter(students, 400);
            else
                mAdapter = new MyRecyclerViewAdapter(students, 401);

        }

        //List of Useful links
        else if (value == 10) {
            this.setTitle("Other Links");
            mAdapter = new MyRecyclerViewAdapter(getLinks(), 10);
        }


        mRecyclerView.setAdapter(mAdapter);

    }

    //-----------------------------------------------------------------------------Previous Year Data Parsing Functions---------------------------------------------------

    private ArrayList<DataObject> getPrevHonExcel() {

        ArrayList<DataObject> result = new ArrayList<DataObject>();
        ArrayList<Table_Prev_Rec> prevList = new ArrayList<Table_Prev_Rec>();

        prevList = (ArrayList<Table_Prev_Rec>) db.getPrevRec("H");
        //Also reads the first row of the excel file. i.e Name,Roll number etc
        for (Iterator<Table_Prev_Rec> rit = prevList.iterator(); rit.hasNext(); ) {
            Table_Prev_Rec p = rit.next();
            // Log.d("ExcelData", row.getCell(0, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            DataObject g = new DataObject(p.getName(), p.getConvo_num(), p.getDesignation(), p.getComment());
            result.add(g);
        }
        return result;
    }

    private ArrayList<DataObject> getPrevChiefExcel() {

        ArrayList<DataObject> result = new ArrayList<DataObject>();
        ArrayList<Table_Prev_Rec> prevList = new ArrayList<Table_Prev_Rec>();

        prevList = (ArrayList<Table_Prev_Rec>) db.getPrevRec("C");
        //Also reads the first row of the excel file. i.e Name,Roll number etc
        for (Iterator<Table_Prev_Rec> rit = prevList.iterator(); rit.hasNext(); ) {
            Table_Prev_Rec p = rit.next();
            // Log.d("ExcelData", row.getCell(0, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            DataObject g = new DataObject(p.getName(), p.getConvo_num(), p.getComment(), p.getDesignation());
            result.add(g);
        }
        return result;
    }

    private ArrayList<DataObject> getPrevPresExcel() {

        ArrayList<DataObject> result = new ArrayList<DataObject>();
        ArrayList<Table_Prev_Rec> prevList = new ArrayList<Table_Prev_Rec>();

        prevList = (ArrayList<Table_Prev_Rec>) db.getPrevRec("S");
        //Also reads the first row of the excel file. i.e Name,Roll number etc
        for (Iterator<Table_Prev_Rec> rit = prevList.iterator(); rit.hasNext(); ) {
            Table_Prev_Rec p = rit.next();
            // Log.d("ExcelData", row.getCell(0, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            DataObject g = new DataObject(p.getName(), p.getDesignation());
            result.add(g);
        }
        return result;
    }


    //-------------------------------------------------------------------------------------Creating and handling the search bar---------------------------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (value != 3 && value != 4) return false;

        this.menu = menu;
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Enter student name");

        //Expanding the search view to take complete width
        searchView.setMaxWidth(Integer.MAX_VALUE);
        MenuItemCompat.expandActionView(searchItem);


        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.d("New Intent in CardView", "Reached Here");

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String q = intent.getStringExtra(SearchManager.QUERY);

            Log.d("Search query", q);

            if (value == 3)
                hasSearchedAwards = true;
            else if (value == 4)
                hasSearchedGrad = true;

            query = q;
            displayData();
        } else {
            Log.d("New Intent in CardView", "Inside Else");
            b = intent.getExtras();
            if (b != null)
                value = b.getInt("key");
            Log.d("Value : ", String.valueOf(value));
            displayData();
        }
    }

}
