package com.aps.iitconv;

/**
 * Created by imhobo on 31/3/17.
 */

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.List;


public class CardViewActivity extends MainActivity
{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "CardViewActivity";
    private DBHandler_Grad db;
    int value = -1;

    private ArrayList<DataObject> programs;
    private ArrayList<DataObject> depts;
    private int program = -1, dept = -1;

    private ArrayList<DataObject> awards;
    private final int AWARDS_WITH_PICS = 5;
    private int awardNum = -1;

    private Bundle b;

//    private static final String ip = "http://192.168.0.101/";
    private static final String ip = "http://34.204.142.107/media/documents/";

//    private static final String ip = "http://192.168.0.102/media/documents/";
    private static final String u1 = "format.xlsx";
    private static final String u2 = "schedule.xlsx";
    private static final String u3 = "taxi.xlsx";
    private static final String u4 = "webcast.txt";
    private static final String u5 = "format_epoch.txt";
    private static final String u6 = "schedule_epoch.txt";
    private static final String u7 = "taxi_epoch.txt";
    private static final String u8 = "hon_degree.xlsx";
    private static final String u9 = "hon_degree_epoch.txt";


    private static final String TYPE_H = "H";
    private static final String TYPE_C = "C";



    //Copied table names and columns from DBHANDLER_GRAD
    private static final String TABLE_GRAD = "Table_Grad_Students";
    private static final String TABLE_AWARDS = "Table_Awards";

    private static final String T1_KEY_ID = "id";
    private static final String T1_KEY_ROLL = "roll";
    private static final String T1_KEY_NAME = "name";
    private static final String T1_KEY_ADVISERS = "advisers";
    private static final String T1_KEY_DESCRIPTION = "desc";
    private static final String T1_KEY_DEPT = "dept";
    private static final String T1_KEY_PROGRAM = "program";

    private ProgressDialog dialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //When this class is called from MainActivity or some other activity
        if(value==-1)
        {
            super.onCreate(savedInstanceState);
            dialog = new ProgressDialog(CardViewActivity.this);
        }

        //Clearing the existing UI
        frameLayout.removeAllViews();


        //Table created
        db = DBHandler_Grad.getInstance(this);



        b = getIntent().getExtras();
        if(b != null)
        {
            value = b.getInt("key");
        }


        checkForUpdates();

    }




    protected void checkForUpdates()
    {
        Log.v("LOG_TAG", "Checking for updates");
        new ExecuteURL().execute();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

    }


    //Possibly the worst way to implement the back button feature.
    @Override
    public void onBackPressed()
    {
        Log.d("CDA", "onBackPressed Called");

        if(value==1 || value ==2 || value ==5 || value == 9)
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
            reset();
        }

        else if(value==4 && program == -1)
        {
            finish();
        }

        else if(value==4 && program > -1 && dept == -1)
        {
            program = -1;
            reset();
        }

        else if(value==4 && program > -1 && dept > -1)
        {
            dept = -1;
            reset();
        }

    }

    //Temp Data Set to test UI
    private ArrayList<DataObject> getDataSet()
    {
        ArrayList results = new ArrayList<DataObject>();
        for (int index = 0; index < 20; index++) {
            DataObject obj = new DataObject("Some Primary Text " + index,
                    "Secondary " + index);
            results.add(index, obj);
        }
        return results;
    }

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

        for (String i : tempHolder)
        {
            results.add(new DataObject(i));
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
            DataObject obj= new DataObject(t.getName(),t.getTitle(), t.getYear(), t.getPicture(), t.getDescription());
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


    //-------------------------------------------------------------------------------------


    private class ExecuteURL extends AsyncTask<String, Void, String>
    {
        private static final int REGISTRATION_TIMEOUT = 3 * 1000;
        private static final int WAIT_TIMEOUT = 30 * 1000;
        private String content = null;
        private Exception exception = null;

        protected void onPreExecute()
        {
            dialog.setMessage("Fetching data. Please wait...");

            if(!dialog.isShowing())
                dialog.show();
        }

        protected InputStream getContent(String u)
        {
            InputStream in = null;
            URL url = null;
            try
            {
                url = new URL(u);
            } catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            HttpURLConnection urlConnection = null;
            try
            {
                urlConnection = (HttpURLConnection) url.openConnection();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            try
            {

                in = new BufferedInputStream(urlConnection.getInputStream());
                return in;


            }catch (Exception e)
            {
                exception = e;
                Log.d("ConnectionError","getContent : " + e.getMessage());
                reset();
            }

                    finally {
                //urlConnection.disconnect();
            }

            return in;
        }

        protected String streamToString(InputStream is)
        {
            BufferedReader br = null;
            StringBuilder sb = new StringBuilder();

            String line;
            try {

                br = new BufferedReader(new InputStreamReader(is));
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return sb.toString();

        }


        protected String doInBackground(String... urls)
        {

            try
            {

                //Checking if new data is available at server
                InputStream i1 = getContent(ip+u5);
                String r1 = streamToString(i1);
                i1.close();
                InputStream i2 = getContent(ip+u6);
                String r2 = streamToString(i2);
                i2.close();
                InputStream i3 = getContent(ip+u7);
                String r3 = streamToString(i3);
                i3.close();
                InputStream i4 = getContent(ip+u4);
                String r4 = streamToString(i4);
                i4.close();
                InputStream i5 = getContent(ip+u9);
                String r5 = streamToString(i5);
                i5.close();


                if(exception!=null)
                {
                    return content;
                }


                SharedPreferences mPrefs = getApplicationContext().getSharedPreferences("lastDataFetch", MODE_PRIVATE);

                String m1 = mPrefs.getString("FetchFormat", String.valueOf(1));
                String m2 = mPrefs.getString("FetchSchedule", String.valueOf(1));
                String m3 = mPrefs.getString("FetchTaxi", String.valueOf(1));
                String m4 = mPrefs.getString("FetchWebcast", String.valueOf(1));
                String m5 = mPrefs.getString("FetchHon", String.valueOf(1));


                //Fetching new data from the servers and updating the variables to reflect last fetched time
                if(m1.equals("1") || (!r1.equals(m1)))
                {
                    //Delete existing data
                    if(!r1.equals(m1))
                    {
                        db.deleteAwardsAndStudents();
                    }
                    parseGraduating(getContent(ip+u1));
                    SharedPreferences.Editor mEditor = mPrefs.edit();
                    mEditor.putString("FetchFormat", r1).commit();
                }


                if(m2.equals("1") || (!r2.equals(m2)))
                {
                    //Delete existing data
                    if(!r2.equals(m2))
                    {
                        db.deleteSchedule();
                    }
                    parseSchedule(getContent(ip+u2));
                    SharedPreferences.Editor mEditor = mPrefs.edit();
                    mEditor.putString("FetchSchedule", r2).commit();

                }


                if(m3.equals("1") || (!r3.equals(m3)))
                {
                    //Delete existing data
                    if(!r3.equals(m3))
                    {
                        db.deleteContacts();
                    }
                    parseContacts(getContent(ip+u3));
                    SharedPreferences.Editor mEditor = mPrefs.edit();
                    mEditor.putString("FetchTaxi", r3).commit();

                }


                if(m4.equals("1") || (!r4.equals(m4)))
                {
                    SharedPreferences.Editor mEditor = mPrefs.edit();
                    mEditor.putString("FetchWebcast", r4).commit();
                }

                if(m5.equals("1") || (!r5.equals(m5)))
                {
                    //Delete existing data

                    //Type 'H' is Honourary degree and 'C' is Chief Guest
                    if(!r5.equals(m5))
                    {
                        db.deleteGuests(TYPE_H);
                    }
                    parseHonDegrees(getContent(ip+u8));
                    SharedPreferences.Editor mEditor = mPrefs.edit();
                    mEditor.putString("FetchHon", r5).commit();
                }





            } catch (Exception e)
            {

//                Log.d("ConError ExecuteURL:", exception.getMessage());
                content = exception.getMessage();
                if(e!=null && exception == null)
                    reset();

                cancel(true);

            }
            return content;
        }

        protected void onCancelled()
        {

            dialog.dismiss();
            Toast toast = Toast.makeText(CardViewActivity.this,
                    "Error connecting to Server", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 25, 400);
            toast.show();

        }

        protected void onPostExecute(String content)
        {

            dialog.dismiss();
            reset();
        }

        private void parseGraduating(InputStream inStream)
        {


            AssetManager am = getAssets();
            XSSFWorkbook wb = null;
            try
            {
                wb = new XSSFWorkbook(inStream);
                inStream.close();
            } catch (IOException e)
            {
                Log.d("Error", e.getMessage().toString());
                e.printStackTrace();
            }

            Sheet sheet1 = wb.getSheetAt(0);

            Sheet sheet2 = wb.getSheetAt(1);
            if (sheet1 == null)
            {
                return;
            }
            if (sheet2 == null)
            {
                return;
            }

            insertGraduatingExcelToSqlite(sheet1);
            insertAwardsExcelToSqlite(sheet2);

        }

        private void parseSchedule(InputStream inStream)
        {

            AssetManager am = getAssets();
            XSSFWorkbook wb = null;
            try
            {
                wb = new XSSFWorkbook(inStream);
                inStream.close();
            } catch (IOException e)
            {
                Log.d("Error", e.getMessage().toString());
                e.printStackTrace();
            }

            Sheet sheet1 = wb.getSheetAt(0);

            if (sheet1 == null)
            {
                return;
            }

            insertScheduleExcelToSqlite(sheet1);
        }

        private void parseContacts(InputStream inStream)
        {

            AssetManager am = getAssets();
            XSSFWorkbook wb = null;
            try
            {
                wb = new XSSFWorkbook(inStream);
                inStream.close();
            } catch (IOException e)
            {
                Log.d("Error", e.getMessage().toString());
                e.printStackTrace();
            }

            Sheet sheet1 = wb.getSheetAt(0);

            if (sheet1 == null)
            {
                return;
            }

            insertContactExcelToSqlite(sheet1);
        }

        private void parseHonDegrees(InputStream inStream)
        {

            AssetManager am = getAssets();
            XSSFWorkbook wb = null;
            try
            {
                wb = new XSSFWorkbook(inStream);
                inStream.close();
            } catch (IOException e)
            {
                Log.d("Error", e.getMessage().toString());
                e.printStackTrace();
            }

            Sheet sheet1 = wb.getSheetAt(0);

            if (sheet1 == null)
            {
                return;
            }

            insertHonDegreeExcelToSqlite(sheet1);
        }

    }
    //----------------------------------------------------------------------------------------------------------------------------------------



    public void insertGraduatingExcelToSqlite(Sheet sheet)
    {

        int count = 0;

        //Also reads the first row of the excel file. i.e Name,Roll number etc
        for (Iterator<Row> rit = sheet.rowIterator(); rit.hasNext(); )
        {
            Row row = rit.next();

            if(count == 0)row = rit.next();

            row.getCell(0, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(1, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(2, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(3, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(4, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(5, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);

            Table_Grad_Students g = new Table_Grad_Students();

            g.setRoll(row.getCell(0, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            g.setName(row.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            g.setProgram(row.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            g.setDept(row.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            g.setAdvisers(row.getCell(4, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            g.setDescription(row.getCell(5, Row.CREATE_NULL_AS_BLANK).getStringCellValue());


            //Log.d("NAME OF THE STUDENT : ", g.getName());

            DBHandler_Grad.getInstance(this).addStudent1(db.sqldb, g);

            count++;
        }
    }


    public void insertAwardsExcelToSqlite(Sheet sheet)
    {
        int count = 0;

        for (Iterator<Row> rit = sheet.rowIterator(); rit.hasNext(); )
        {
            Row row = rit.next();

            if(count == 0)row = rit.next();

            row.getCell(0, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(1, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(2, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(3, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(4, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(5, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(6, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(7, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);

            Table_Awards g = new Table_Awards();

            g.setRoll(row.getCell(0, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            g.setName(row.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            g.setAward(row.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            g.setDescription(row.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            g.setProgram(row.getCell(4, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            g.setDept(row.getCell(5, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            g.setYear(row.getCell(6, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            g.setComment(row.getCell(7, Row.CREATE_NULL_AS_BLANK).getStringCellValue());


            //Log.d("NAME OF THE STUDENT : ", g.getName());

            DBHandler_Grad.getInstance(this).addStudent2(db.sqldb, g);

            count++;
        }
    }


    public void insertScheduleExcelToSqlite(Sheet sheet)
    {

        int count = 0;

        //Also reads the first row of the excel file. i.e Name,Roll number etc
        for (Iterator<Row> rit = sheet.rowIterator(); rit.hasNext(); )
        {
            Row row = rit.next();

            if(count == 0)row = rit.next();

            row.getCell(0, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(1, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(2, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(3, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);


            String title = row.getCell(0, Row.CREATE_NULL_AS_BLANK).getStringCellValue();
            String venue = row.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue();
            String date = row.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue();
            String time = row.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue();

            DBHandler_Grad.getInstance(this).addEvent(db.sqldb, title,venue,date,time);
            count++;
        }
    }

    public void insertContactExcelToSqlite(Sheet sheet)
    {
        int count = 0;

        for (Iterator<Row> rit = sheet.rowIterator(); rit.hasNext(); )
        {
            Row row = rit.next();

            if(count == 0)row = rit.next();

            row.getCell(0, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(1, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(2, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);

            Table_Contact g = new Table_Contact();


            g.setName(row.getCell(0, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            g.setNumber(row.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            g.setTransport(row.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());


            //Log.d("NAME OF THE STUDENT : ", g.getName());

            DBHandler_Grad.getInstance(this).addContact(db.sqldb, g);

            count++;
        }
    }

    public void insertHonDegreeExcelToSqlite(Sheet sheet)
    {

        int count = 0;

        //Also reads the first row of the excel file. i.e Name,Roll number etc
        for (Iterator<Row> rit = sheet.rowIterator(); rit.hasNext(); )
        {
            Row row = rit.next();

            if(count == 0)row = rit.next();

            row.getCell(0, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(1, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(2, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(3, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(4, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);


            Table_Guest g = new Table_Guest();
            g.setName(row.getCell(0, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            g.setTitle(row.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            g.setYear(row.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            g.setPicture(row.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            g.setDescription(row.getCell(4, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            g.setType("H");

            DBHandler_Grad.getInstance(this).addGuest(db.sqldb, g);
            count++;
        }
    }





    //Bad way to reset everything.
    protected void reset()
    {

        b = getIntent().getExtras();
        if(b != null)
            value = b.getInt("key");


        //Clearing the existing UI
        frameLayout.removeAllViews();


        //Table created
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

        // Schedule
        else if(value==2)
            mAdapter = new MyRecyclerViewAdapter(getAnnouncements(),2);


        else if(value==3 && awardNum == -1)
        {
            awards = getAwards();
            mAdapter = new MyRecyclerViewAdapter(awards, 3);
        }

        else if(value==5)
        {
            mAdapter = new MyRecyclerViewAdapter(getGuests(TYPE_H), 5);
        }

        else if(value==9)
        {
            ArrayList<Table_Contact> contacts = (ArrayList<Table_Contact>) db.getContacts();
            mAdapter = new MyRecyclerViewAdapter(getContacts(), 9);
        }

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

        else if(value==4 && program == -1)
        {
            programs = getPrograms();
            mAdapter = new MyRecyclerViewAdapter(programs,4);
        }

        else if(value == 4 && program > -1 && dept == -1)
        {
            String curDep = programs.get(program).getmText1();
            CardViewActivity.this.setTitle(curDep);
            depts = getDept1(curDep);
            mAdapter = new MyRecyclerViewAdapter(depts,40);
        }

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
                    reset();
                }

                else if(value == 3 && awardNum > -1)
                {

                    //Click on the student name
                }

                else if(value==4 && program == -1)
                {
                    program = position;
                    reset();
                }

                else if(value==4 && program > -1 && dept == -1)
                {
                    dept = position;
                    reset();
                }

                else if(value==4 && program > -1 && dept > -1)
                {
                    //Click on the student name
                }


            }
        });

    }


}
