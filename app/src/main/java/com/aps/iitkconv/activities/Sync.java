package com.aps.iitkconv.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.aps.iitconv.R;
import com.aps.iitkconv.models.DBHandler_Grad;
import com.aps.iitkconv.models.Table_Awards;
import com.aps.iitkconv.models.Table_Contact;
import com.aps.iitkconv.models.Table_Grad_Students;
import com.aps.iitkconv.models.Table_Guest;
import com.aps.iitkconv.models.Table_Prev_Rec;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by imhobo on 2/5/17.
 */


    class Sync extends AsyncTask<String, Void, String>
    {

        DBHandler_Grad db;
        ProgressDialog dialog;
        Context mContext;

        private static final int REGISTRATION_TIMEOUT = 3 * 1000;
        private static final int WAIT_TIMEOUT = 30 * 1000;
        private String content = null;
        private Exception exception = null;


        public Sync(Context mContext)
        {
            this.mContext = mContext;
            dialog = new ProgressDialog(mContext);

        }

        protected void checkForUpdates()
        {
            Log.v("LOG_TAG", "Checking for updates");
            new Sync(mContext).execute();
        }

        protected void onPreExecute()
        {
            super.onPreExecute();
            dialog.setCancelable(false);
            dialog.setMessage("Fetching data. Please wait...");
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
                //   reset();
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


//            Log.d("Inside:", "doInback1");
            try
            {


                if(exception!=null)
                {
                    return content;
                }

                db = DBHandler_Grad.getInstance(mContext);

//                Log.d("Inside:", "doInback2");
                SharedPreferences mPrefs = mContext.getSharedPreferences("lastDataFetch", MODE_PRIVATE);

                String m1 = mPrefs.getString("FetchFormat", String.valueOf(1));
                String m2 = mPrefs.getString("FetchSchedule", String.valueOf(1));
                String m3 = mPrefs.getString("FetchContact", String.valueOf(1));
                String m4 = mPrefs.getString("FetchWebcast", String.valueOf(1));
                String m5 = mPrefs.getString("FetchHon", String.valueOf(1));
                String m6 = mPrefs.getString("FetchChief", String.valueOf(1));
                String m7 = mPrefs.getString("ParsePrev", String.valueOf(1));
                String m8 = mPrefs.getString("FetchLinks", String.valueOf(1));
                String m9 = mPrefs.getString("FetchStudents", String.valueOf(1));
                String m10 = mPrefs.getString("FetchAwards", String.valueOf(1));




                Log.d("Inside:", "doInback3");

                if(m2.equals("1"))
                {
                    parseSchedule();
                    Log.d("Inside:", "doInback4");
                    SharedPreferences.Editor mEditor = mPrefs.edit();
                    mEditor.putString("FetchSchedule", "0").commit();
                    Log.d("Done:", "Schedule");

                }


                if(m3.equals("1"))
                {
                    db.deleteContacts();
                    parseContacts();
                    SharedPreferences.Editor mEditor = mPrefs.edit();
                    mEditor.putString("FetchContact", "0").commit();
                    Log.d("Done:", "Contacts");
                }


                if(m5.equals("1"))
                {
                    //Delete existing data

                    //Type 'H' is Honourary degree and 'C' is Chief Guest

                    parseHonDegrees();
                    ArrayList<String> imgList = (ArrayList<String>) db.getImageList(mContext.getString(R.string.TYPE_H));
                    insertImageList(imgList, mContext.getString(R.string.TYPE_H));

                    SharedPreferences.Editor mEditor = mPrefs.edit();
                    mEditor.putString("FetchHon", "0").commit();
                    Log.d("Done:", "Honorary");
                }


                if(m6.equals("1"))
                {
                    //Delete existing data

                    //Type 'H' is Honourary degree and 'C' is Chief Guest

                    parseChiefGuests();
                    ArrayList<String> imgList = (ArrayList<String>) db.getImageList(mContext.getString(R.string.TYPE_C));
                    insertImageList(imgList, mContext.getString(R.string.TYPE_C));

                    SharedPreferences.Editor mEditor = mPrefs.edit();
                    mEditor.putString("FetchChief", "0").commit();
                    Log.d("Done:", "Chief Guests");
                }



                //Parsing Previous recipient data
                if(m7.equals("1"))
                {
                    parsePrevRec("H");//Honourary
                    parsePrevRec("C");//Chief Guests
                    parsePrevRec("S");//President Gold Medal

                    SharedPreferences.Editor mEditor = mPrefs.edit();
                    mEditor.putString("ParsePrev", "0").commit();
                    Log.d("Done:", "Prev_Recp");
                }




                //Checking if new data is available at server
                InputStream i1 = getContent(mContext.getString(R.string.ip) + mContext.getString(R.string.u16));
                String r1 = streamToString(i1);
                i1.close();

                InputStream i2 = getContent(mContext.getString(R.string.ip) + mContext.getString(R.string.u17));
                String r2 = streamToString(i2);
                i2.close();

//                InputStream i2 = getContent(mContext.getString(R.string.ip) + mContext.getString(R.string.u6));
//                String r2 = streamToString(i2);
//                i2.close();


//                InputStream i3 = getContent(mContext.getString(R.string.ip) + mContext.getString(R.string.u7));
//                String r3 = streamToString(i3);
//                i3.close();

                InputStream i4 = getContent(mContext.getString(R.string.ip) + mContext.getString(R.string.u4));
                String r4 = streamToString(i4);
                i4.close();

                /*
                InputStream i5 = getContent(mContext.getString(R.string.ip) + mContext.getString(R.string.u9));
                String r5 = streamToString(i5);
                i5.close();
                */
                /*
                InputStream i6 = getContent(mContext.getString(R.string.ip) + mContext.getString(R.string.u11));
                String r6 = streamToString(i6);
                i6.close();
                */
                InputStream i7 = getContent(mContext.getString(R.string.ip) + mContext.getString(R.string.u13));
                String r7 = streamToString(i7);
                i7.close();


                //Fetching new data from the servers and updating the variables to reflect last fetched time
                if(m9.equals("1") || (!r1.equals(m9)))
                {
                    //Delete existing data
                    if(!r1.equals(m9))
                    {
                        if(db == null)
                            Log.d("CheckDBInstance", "NULL");
                        else
                            Log.d("CheckDBInstance", "NOT NULL");
                        db.deleteStudents();
                    }
                    parseGraduating(getContent(mContext.getString(R.string.ip) + mContext.getString(R.string.u14)));
                    SharedPreferences.Editor mEditor = mPrefs.edit();
                    mEditor.putString("FetchStudents", r1).commit();
                    Log.d("Done:", "Graduating Students");
                }

                if(m10.equals("1") || (!r2.equals(m10)))
                {
                    //Delete existing data
                    if(!r2.equals(m10))
                    {
                        if(db == null)
                            Log.d("CheckDBInstance", "NULL");
                        else
                            Log.d("CheckDBInstance", "NOT NULL");
                        db.deleteAwards();
                    }

                    parseAwards(getContent(mContext.getString(R.string.ip) + mContext.getString(R.string.u15)));
                    ArrayList<String> imgList = (ArrayList<String>) db.getImageList(mContext.getString(R.string.TYPE_S));
                    Log.d("Number of AwardImg:", String.valueOf(imgList.size()));
                    insertImageList(imgList, mContext.getString(R.string.TYPE_S));

                    SharedPreferences.Editor mEditor = mPrefs.edit();
                    mEditor.putString("FetchAwards", r2).commit();
                    Log.d("Done:", "Awards");
                }


                if(m4.equals("1") || (!r4.equals(m4)))
                {
                    SharedPreferences.Editor mEditor = mPrefs.edit();
                    mEditor.putString("FetchWebcast", r4).commit();
                    Log.d("Done:", "Webcast");
                }

                if(m8.equals("1") || (!r7.equals(m8)))
                {
                    //Delete existing data
                    if(!r7.equals(m8))
                    {
                        db.deleteLinks();
                    }
                    parseLinks(getContent(mContext.getString(R.string.ip) + mContext.getString(R.string.u12)));
                    SharedPreferences.Editor mEditor = mPrefs.edit();
                    mEditor.putString("FetchLinks", r7).commit();
                    Log.d("Done:", "Links");

                }



            } catch (Exception e)
            {

                if(e!=null)
                {
                    Log.d("ConError SyncURL:", "doInBackground Exception");
                    startMain();
                }
                    cancel(true);

            }
            return content;

        }

        protected void startMain()
        {
            Intent i = new Intent(mContext, MainActivity.class);
            mContext.startActivity(i);
            ((Activity)mContext).finish();
        }

        protected void onCancelled()
        {

            dialog.dismiss();
            Toast toast = Toast.makeText(mContext,
                    "Error connecting to Server", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 25, 400);
            toast.show();

        }

        protected void onPostExecute(String content)
        {

            dialog.dismiss();
            startMain();
        }


        //----------------------------------------------Get json-------------------------------------------------
        private JSONObject getJson(InputStream inputStream)
        {
            BufferedReader bR = new BufferedReader(  new InputStreamReader(inputStream));
            String line = "";

            StringBuilder responseStrBuilder = new StringBuilder();
            try
            {
                while((line =  bR.readLine()) != null){

                    responseStrBuilder.append(line);
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            JSONObject result= null;
            try
            {
                result = new JSONObject(responseStrBuilder.toString());
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
            return result;
        }

        //----------------------------------------------Parsing functions-----------------------------------------

        private void parsePrevRec(String type)
        {
            int r = -1;
            // getResources().getIdentifier("image_name","res_folder_name", package_name);


            if(type.equals("H"))
                r = mContext.getResources().getIdentifier("prev_hon","raw", mContext.getPackageName());
            else if(type.equals("C"))
                r = mContext.getResources().getIdentifier("prev_chief","raw", mContext.getPackageName());
            else if(type.equals("S"))
                r = mContext.getResources().getIdentifier("prev_pres","raw", mContext.getPackageName());


            InputStream inStream = mContext.getResources().openRawResource(r);
            JSONObject obj = getJson(inStream);
            JSONArray sheet = null;
            try
            {
                sheet = obj.getJSONArray("Sheet1");
            } catch (JSONException e)
            {
                e.printStackTrace();
            }

            for (int i = 0; i < sheet.length(); i++)
            {
//                Log.d("Siez :" , String.valueOf(sheet.length()));
                JSONObject c = null;
                try
                {
                    c = sheet.getJSONObject(i);
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }

                Iterator<?> keys = c.keys();

                HashMap<String, String> map = new HashMap<String, String>();
                while (keys.hasNext())
                {
                    String key = (String) keys.next();
//                    Log.d("key : ", key);
                    try
                    {
                        String value = (String) c.get(key);
                        map.put(key,value);
//                        Log.d("Value Schedule : ", value);

                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }

                if(type.equals("H") || type.equals("C"))
                {
                    Table_Prev_Rec g = new Table_Prev_Rec(
                            map.get("Name"),
                            map.get("ConNumber"),
                            map.get("Designation"),
                            map.get("Degree") , type) ;

                    db.addPrevRec(db.sqldb, g);
                }
                else
                {
                    Table_Prev_Rec g = new Table_Prev_Rec(
                            map.get("Name"),
                            map.get("Year"),"","","S");

                    db.addPrevRec(db.sqldb, g);
                }




            }
        }

        private void parseGraduating(InputStream inStream)
        {
            JSONObject obj = getJson(inStream);
            JSONArray sheet = null;
            try
            {
                sheet = obj.getJSONArray("Graduating Student 50");
            } catch (JSONException e)
            {
                e.printStackTrace();
            }

            for (int i = 0; i < sheet.length(); i++)
            {
//                Log.d("Siez :" , String.valueOf(sheet.length()));
                JSONObject c = null;
                try
                {
                    c = sheet.getJSONObject(i);
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }

                Iterator<?> keys = c.keys();

                HashMap<String, String> map = new HashMap<String, String>();
                while (keys.hasNext())
                {
                    String key = (String) keys.next();
//                    Log.d("key : ", key);
                    try
                    {
                        String value = (String) c.get(key);
                        map.put(key,value);
//                        Log.d("Value Schedule : ", value);

                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }

                Table_Grad_Students g = new Table_Grad_Students();

                g.setRoll(map.get("Roll Number"));
                g.setName(map.get("Name"));
                g.setProgram(map.get("Program"));
                g.setDept(map.get("Dept."));
                g.setAdvisers(map.get("Advisors"));
                g.setDescription(map.get("Description"));

                //Log.d("NAME OF THE STUDENT : ", g.getName());
                db.addStudent1(db.sqldb, g);

            }

        }

        private void parseAwards(InputStream inStream)
        {
            JSONObject obj = getJson(inStream);
            JSONArray sheet = null;
            try
            {
                sheet = obj.getJSONArray("Award");
            } catch (JSONException e)
            {
                e.printStackTrace();
            }

            for (int i = 0; i < sheet.length(); i++)
            {
//                Log.d("Siez :" , String.valueOf(sheet.length()));
                JSONObject c = null;
                try
                {
                    c = sheet.getJSONObject(i);
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }

                Iterator<?> keys = c.keys();

                HashMap<String, String> map = new HashMap<String, String>();
                while (keys.hasNext())
                {
                    String key = (String) keys.next();
//                    Log.d("key : ", key);
                    try
                    {
                        String value = (String) c.get(key);
                        map.put(key,value);
//                        Log.d("Value Schedule : ", value);

                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }

                Table_Awards g = new Table_Awards();


                g.setRoll(map.get("Roll Number"));
                g.setName(map.get("Name"));
                g.setAward(map.get("Award"));
                g.setDescription(map.get("Award Description"));
                g.setProgram(map.get("Program"));
                g.setDept(map.get("Department"));
                g.setYear(map.get("Year"));
                g.setComment(map.get("Comment"));

                if(map.get("Picture") != null)
                    g.setPicture(map.get("Picture"));
                else
                    g.setPicture("");
                //Log.d("NAME OF THE STUDENT : ", g.getName());
                db.addStudent2(db.sqldb, g);


            }

        }

        private void parseSchedule()
        {
//            Log.d("Inside:", "doInback4");
            int r = mContext.getResources().getIdentifier("schedule", "raw", mContext.getPackageName());
            InputStream inStream = mContext.getResources().openRawResource(r);
            JSONObject obj = getJson(inStream);
            JSONArray sheet = null;
            try
            {
                sheet = obj.getJSONArray("Sheet1");
            } catch (JSONException e)
            {
                e.printStackTrace();
            }

            for (int i = 0; i < sheet.length(); i++)
            {
//                Log.d("Siez :" , String.valueOf(sheet.length()));
                JSONObject c = null;
                try
                {
                    c = sheet.getJSONObject(i);
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }

                Iterator<?> keys = c.keys();

                HashMap<String, String> map = new HashMap<String, String>();
                while (keys.hasNext())
                {
                    String key = (String) keys.next();
//                    Log.d("key : ", key);
                    try
                    {
                        String value = (String) c.get(key);
                        map.put(key,value);
//                        Log.d("Value Schedule : ", value);

                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
                db.addEvent(db.sqldb, map.get("Event"),map.get("Venue"),map.get("Date"),map.get("Time"));

            }
        }

        private void parseLinks(InputStream inStream)
        {

            JSONObject obj = getJson(inStream);
            JSONArray sheet = null;
            try
            {
                sheet = obj.getJSONArray("Sheet1");
            } catch (JSONException e)
            {
                e.printStackTrace();
            }

            for (int i = 0; i < sheet.length(); i++)
            {
//                Log.d("Siez :" , String.valueOf(sheet.length()));
                JSONObject c = null;
                try
                {
                    c = sheet.getJSONObject(i);
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }

                Iterator<?> keys = c.keys();

                HashMap<String, String> map = new HashMap<String, String>();
                while (keys.hasNext())
                {
                    String key = (String) keys.next();
//                    Log.d("key : ", key);
                    try
                    {
                        String value = (String) c.get(key);
                        map.put(key, value);
//                        Log.d("Value Schedule : ", value);

                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
                db.addLink(db.sqldb, map.get("Name"), map.get("Link"));
            }
        }

        private void parseContacts()
        {
//            Log.d("Inside:", "doInback4");
            int r = mContext.getResources().getIdentifier("taxi", "raw", mContext.getPackageName());
            InputStream inStream = mContext.getResources().openRawResource(r);
            JSONObject obj = getJson(inStream);
            JSONArray sheet = null;
            try
            {
                sheet = obj.getJSONArray("Sheet1");
            } catch (JSONException e)
            {
                e.printStackTrace();
            }

            for (int i = 0; i < sheet.length(); i++)
            {
//                Log.d("Siez :" , String.valueOf(sheet.length()));
                JSONObject c = null;
                try
                {
                    c = sheet.getJSONObject(i);
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }

                Iterator<?> keys = c.keys();

                HashMap<String, String> map = new HashMap<String, String>();
                while (keys.hasNext())
                {
                    String key = (String) keys.next();
//                    Log.d("key : ", key);
                    try
                    {
                        String value = (String) c.get(key);
                        map.put(key,value);
//                        Log.d("Value Schedule : ", value);

                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
                Table_Contact g = new Table_Contact();
                g.setName(map.get("Name"));
                g.setNumber(map.get("Contact Number"));
                g.setTransport(map.get("Transport"));
                db.addContact(db.sqldb, g);

            }
        }

        private void parseHonDegrees()
        {
//            Log.d("Inside:", "doInback4");
            int r = mContext.getResources().getIdentifier("hon_degree", "raw", mContext.getPackageName());
            InputStream inStream = mContext.getResources().openRawResource(r);
            JSONObject obj = getJson(inStream);
            JSONArray sheet = null;
            try
            {
                sheet = obj.getJSONArray("Sheet1");
            } catch (JSONException e)
            {
                e.printStackTrace();
            }

            for (int i = 0; i < sheet.length(); i++)
            {
//                Log.d("Siez :" , String.valueOf(sheet.length()));
                JSONObject c = null;
                try
                {
                    c = sheet.getJSONObject(i);
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }

                Iterator<?> keys = c.keys();

                HashMap<String, String> map = new HashMap<String, String>();
                while (keys.hasNext())
                {
                    String key = (String) keys.next();
//                    Log.d("key : ", key);
                    try
                    {
                        String value = (String) c.get(key);
                        map.put(key,value);
//                        Log.d("Value Schedule : ", value);

                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }

                Table_Guest g = new Table_Guest();
                g.setName(map.get("Name"));
                g.setTitle(map.get("Title"));
                g.setYear(map.get("Year"));
                g.setPicture(map.get("Picture"));
                g.setDescription(map.get("Description"));
                g.setType("H");

                if(g.getName().equals(""))continue;
                db.addGuest(db.sqldb, g);


            }
        }


        private void parseChiefGuests()
        {

            //            Log.d("Inside:", "doInback4");
            int r = mContext.getResources().getIdentifier("chief_guests", "raw", mContext.getPackageName());
            InputStream inStream = mContext.getResources().openRawResource(r);
            JSONObject obj = getJson(inStream);
            JSONArray sheet = null;
            try
            {
                sheet = obj.getJSONArray("Sheet1");
            } catch (JSONException e)
            {
                e.printStackTrace();
            }

            for (int i = 0; i < sheet.length(); i++)
            {
//                Log.d("Siez :" , String.valueOf(sheet.length()));
                JSONObject c = null;
                try
                {
                    c = sheet.getJSONObject(i);
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }

                Iterator<?> keys = c.keys();

                HashMap<String, String> map = new HashMap<String, String>();
                while (keys.hasNext())
                {
                    String key = (String) keys.next();
//                    Log.d("key : ", key);
                    try
                    {
                        String value = (String) c.get(key);
                        map.put(key,value);
//                        Log.d("Value Schedule : ", value);

                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }

                Table_Guest g = new Table_Guest();
                g.setName(map.get("Name"));
                g.setTitle(map.get("Title"));
                g.setYear(map.get("Year"));
                g.setPicture(map.get("Picture"));
                g.setDescription(map.get("Description"));
                g.setType("C");

                if(g.getName().equals(""))continue;
                db.addGuest(db.sqldb, g);

            }




        }

        //----------------------------------------------------------------------------------------------------------------------------------------


    //----------------------------------------------Functions to insert parsed data into local database-----------------------------------------

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

                db.addStudent1(db.sqldb, g);

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
                row.getCell(8, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);

                Table_Awards g = new Table_Awards();

                g.setRoll(row.getCell(0, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
                g.setName(row.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
                g.setAward(row.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
                g.setDescription(row.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
                g.setProgram(row.getCell(4, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
                g.setDept(row.getCell(5, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
                g.setYear(row.getCell(6, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
                g.setComment(row.getCell(7, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
                g.setPicture(row.getCell(8, Row.CREATE_NULL_AS_BLANK).getStringCellValue());


                //Log.d("NAME OF THE STUDENT : ", g.getName());

                db.addStudent2(db.sqldb, g);

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

                db.addEvent(db.sqldb, title,venue,date,time);
                count++;
            }
        }

        public void insertLinksExcelToSqlite(Sheet sheet)
        {

            int count = 0;

            //Also reads the first row of the excel file. i.e Name,Roll number etc
            for (Iterator<Row> rit = sheet.rowIterator(); rit.hasNext(); )
            {
                Row row = rit.next();

                if(count == 0)row = rit.next();

                row.getCell(0, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(1, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);


                String name = row.getCell(0, Row.CREATE_NULL_AS_BLANK).getStringCellValue();
                String link = row.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue();

                db.addLink(db.sqldb, name, link);
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

                db.addContact(db.sqldb, g);

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

                if(g.getName().equals(""))continue;
                db.addGuest(db.sqldb, g);
                count++;
            }
        }

        public void insertChiefGuestsExcelToSqlite(Sheet sheet)
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
                g.setType("C");

                if(g.getName().equals(""))continue;
                db.addGuest(db.sqldb, g);
                count++;
            }
        }

        private void insertImageList(ArrayList<String> imgList, String type)
        {

            for(String img : imgList)
            {

                Bitmap pic = null;
                if(type.equals("C") || type.equals("H"))
                    pic = getBitmapLocal(img);
                else
                    pic = getBitmapFromURL(img);

                db.addImage(db.sqldb, img, type, pic);
            }

        }

        public Bitmap getBitmapFromURL(String src)
        {
            InputStream input = getContent( mContext.getString(R.string.ip) + src);
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        }

        public Bitmap getBitmapLocal(String src)
        {
            int i = mContext.getResources().getIdentifier(src,"raw", mContext.getPackageName());
            InputStream input = mContext.getResources().openRawResource(i);
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        }



        //------------------------------------------------------------------------------------------------------------------------------
    }

