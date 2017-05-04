package com.aps.iitkconv.models;

/**
 * Created by imhobo on 31/3/17.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.Pair;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DBHandler_Grad extends SQLiteOpenHelper
{

    private static DBHandler_Grad sInstance;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Database";

    public static SQLiteDatabase sqldb = null;

    //Table 1 and Table 2 resp.
    private static final String TABLE_GRAD = "Table_Grad_Students";
    private static final String TABLE_AWARDS = "Table_Awards";
    private static final String TABLE_SCHEDULE = "Table_Schedule";
    private static final String TABLE_ANNOUNCEMENTS = "Table_Announcements";
    private static final String TABLE_CONTACTS = "Table_Contacts";
    private static final String TABLE_GUESTS = "Table_Guests";
    private static final String TABLE_IMAGES = "Table_Images";
    private static final String TABLE_PREV = "Table_Prev";
    private static final String TABLE_LINKS = "Table_Links";

    // Table1 Columns names

    private static final String T1_KEY_ROLL = "roll";
    private static final String T1_KEY_NAME = "name";
    private static final String T1_KEY_ADVISERS = "advisers";
    private static final String T1_KEY_DESCRIPTION = "desc";
    private static final String T1_KEY_DEPT = "dept";
    private static final String T1_KEY_PROGRAM = "program";


    // Table2 Columns names

    private static final String T2_KEY_ROLL = "roll";
    private static final String T2_KEY_NAME = "name";
    private static final String T2_KEY_AWARD = "award";
    private static final String T2_KEY_DESCRIPTION = "desc";
    private static final String T2_KEY_DEPT = "dept";
    private static final String T2_KEY_PROGRAM = "program";
    private static final String T2_KEY_COMMENT = "comment";
    private static final String T2_KEY_YEAR = "year";
    private static final String T2_KEY_PICTURE = "picture";

    // Table3 Columns names

    private static final String T3_KEY_EVENT = "event";
    private static final String T3_KEY_VENUE = "venue";
    private static final String T3_KEY_DATE = "date";
    private static final String T3_KEY_TIME = "time";

    // Table4 Columns names

    private static final String T4_KEY_TITLE = "title";


    // Table5 Columns names

    private static final String T5_KEY_NAME = "name";
    private static final String T5_KEY_NUMBER = "number";
    private static final String T5_KEY_TRANSPORT = "transport";


    // Table6 Columns names

    private static final String T6_KEY_NAME = "name";
    private static final String T6_KEY_TITLE = "title";
    private static final String T6_KEY_YEAR = "year";
    private static final String T6_KEY_PICTURE = "picture";
    private static final String T6_KEY_DESCRIPTION = "description";
    private static final String T6_KEY_TYPE = "type";

    // Table7 Columns names

    private static final String T7_KEY_NAME = "name";
    private static final String T7_KEY_IMAGE = "image";
    private static final String T7_KEY_TYPE = "type";


    // Table8 Columns names

    private static final String T8_KEY_NAME = "name";
    private static final String T8_KEY_DESIGNATION = "designation";
    private static final String T8_KEY_CONVO_NUM = "convo_num";
    private static final String T8_KEY_COMMENT = "comment";
    private static final String T8_KEY_TYPE = "type";


    // Table9 Columns names

    private static final String T9_KEY_NAME = "name";
    private static final String T9_KEY_LINK = "link";




    public static synchronized DBHandler_Grad getInstance(Context context)
    {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DBHandler_Grad(context.getApplicationContext());
        }
        return sInstance;
    }

    public DBHandler_Grad(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

        String CREATE_TABLE_1 = "CREATE TABLE " + TABLE_GRAD + "("
        + T1_KEY_ROLL + " TEXT," + T1_KEY_NAME + " TEXT," + T1_KEY_DEPT + " TEXT," + T1_KEY_PROGRAM + " TEXT," + T1_KEY_ADVISERS + " TEXT," + T1_KEY_DESCRIPTION + " TEXT)";

        db.execSQL(CREATE_TABLE_1);

        String CREATE_TABLE_2 = "CREATE TABLE " + TABLE_AWARDS + "(" + T2_KEY_ROLL + " TEXT,"
                + T2_KEY_NAME + " TEXT," + T2_KEY_AWARD + " TEXT," + T2_KEY_DESCRIPTION + " TEXT," + T2_KEY_DEPT + " TEXT," + T2_KEY_PROGRAM + " TEXT," + T2_KEY_COMMENT + " TEXT," +
                T2_KEY_YEAR + " TEXT," + T2_KEY_PICTURE + " TEXT)";

        db.execSQL(CREATE_TABLE_2);


        String CREATE_TABLE_3 = "CREATE TABLE " + TABLE_SCHEDULE + "(" + T3_KEY_EVENT + " TEXT,"
                + T3_KEY_VENUE + " TEXT," + T3_KEY_DATE + " TEXT," + T3_KEY_TIME + " TEXT)";

        db.execSQL(CREATE_TABLE_3);


        String CREATE_TABLE_4 = "CREATE TABLE " + TABLE_ANNOUNCEMENTS + "(" + T4_KEY_TITLE + " TEXT)";
        db.execSQL(CREATE_TABLE_4);

        String CREATE_TABLE_5 = "CREATE TABLE " + TABLE_CONTACTS + "(" + T5_KEY_NAME + " TEXT,"
                + T5_KEY_NUMBER + " TEXT," + T5_KEY_TRANSPORT + " TEXT)";

        db.execSQL(CREATE_TABLE_5);

        String CREATE_TABLE_6 = "CREATE TABLE " + TABLE_GUESTS + "(" + T6_KEY_NAME + " TEXT,"
                + T6_KEY_TITLE + " TEXT," + T6_KEY_YEAR + " TEXT,"+ T6_KEY_PICTURE + " TEXT," + T6_KEY_TYPE + " TEXT," +  T6_KEY_DESCRIPTION + " TEXT)";

        db.execSQL(CREATE_TABLE_6);


        String CREATE_TABLE_7 = "CREATE TABLE " + TABLE_IMAGES + "(" + T7_KEY_NAME + " TEXT," + T6_KEY_TYPE + " TEXT," + T7_KEY_IMAGE + " BLOB)";

        db.execSQL(CREATE_TABLE_7);


        String CREATE_TABLE_8 = "CREATE TABLE " + TABLE_PREV + "(" + T8_KEY_NAME + " TEXT," + T8_KEY_DESIGNATION + " TEXT," + T8_KEY_CONVO_NUM + " TEXT,"
                + T8_KEY_COMMENT + " TEXT,"+ T8_KEY_TYPE + " TEXT)";

        db.execSQL(CREATE_TABLE_8);


        String CREATE_TABLE_9 = "CREATE TABLE " + TABLE_LINKS + "(" + T9_KEY_NAME + " TEXT," + T9_KEY_LINK + " TEXT)";

        db.execSQL(CREATE_TABLE_9);



        Log.d("DATABASE onCreate", "Tables created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GRAD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AWARDS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANNOUNCEMENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCHEDULE);
        // Creating tables again
        onCreate(db);
    }


    //----------------------------------------------------------------------------Methods for Table 1 -- TABLE_GRAD_STUDENTS

    // Adding new student
    public void addStudent1(SQLiteDatabase db, Table_Grad_Students student)
    {

        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(T1_KEY_NAME, student.getName());
        values.put(T1_KEY_ROLL, student.getRoll());
        values.put(T1_KEY_ADVISERS, student.getAdvisers());
        values.put(T1_KEY_DESCRIPTION, student.getDescription());
        values.put(T1_KEY_PROGRAM, student.getProgram());
        values.put(T1_KEY_DEPT, student.getDept());

        // Inserting Row
        db.insert(TABLE_GRAD, null, values);
        db.close();
        Log.d("AddStudent","Student added + " + student.getName());
    }


    // Getting All Students
    public List<Table_Grad_Students> getStudents1()
    {
        List<Table_Grad_Students> studentList = new ArrayList<Table_Grad_Students>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_GRAD;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst())
        {
            do
            {
                Table_Grad_Students student = new Table_Grad_Students(cursor.getString(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
                studentList.add(student);
            } while (cursor.moveToNext());
        }

        return studentList;
    }

    //Run any "Select *" query
    public List<Table_Grad_Students> runSelectQuery1(String query)
    {
        List<Table_Grad_Students> studentList = new ArrayList<Table_Grad_Students>();

        Log.d("Query : ", query);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst())
        {
            do
            {
                Table_Grad_Students student = new Table_Grad_Students(cursor.getString(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
                studentList.add(student);
            } while (cursor.moveToNext());
        }
        return studentList;
    }

    //Get list of all departments in a program
    public List<String> getDept1(String program)
    {

        String query = "SELECT DISTINCT dept FROM " + TABLE_GRAD + " WHERE program = " + "'" + program + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        // looping through all rows and adding to list

        List<String> results = new ArrayList<String>();
        if (cursor.moveToFirst())
        {
            do
            {
                results.add(cursor.getString(0));

            } while (cursor.moveToNext());
        }
        return results;

    }

    //Get list of all programs
    public List<String> getProgram1()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT DISTINCT program FROM " + TABLE_GRAD;
        Cursor cursor = db.rawQuery(query, null);
        // looping through all rows and adding to list

        List<String> results = new ArrayList<String>();

        if (cursor.moveToFirst())
        {
            do
            {
                results.add(cursor.getString(0));
                Log.d("getDept", cursor.getString(0));

            } while (cursor.moveToNext());
        }

        return results;
    }

    // Getting student Count
    public int getStudentCount1()
    {
        String countQuery = "SELECT * FROM " + TABLE_GRAD;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }


    //------------------------------------------------------------------------- Methods for Table 2 -- TABLE_AWARDS

    // Adding new student
    public void addStudent2(SQLiteDatabase db, Table_Awards student)
    {

        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(T2_KEY_NAME, student.getName());
        values.put(T2_KEY_ROLL, student.getRoll());
        values.put(T2_KEY_AWARD, student.getAward());
        values.put(T2_KEY_DESCRIPTION, student.getDescription());
        values.put(T2_KEY_PROGRAM, student.getProgram());
        values.put(T2_KEY_DEPT, student.getDept());
        values.put(T2_KEY_COMMENT, student.getComment());
        values.put(T2_KEY_YEAR, student.getYear());
        values.put(T2_KEY_PICTURE, student.getPicture());

        // Inserting Row
        db.insert(TABLE_AWARDS, null, values);

        db.close();
        Log.d("AddStudent2","Student added + " + student.getName());
    }

    // Getting students for a particular award
    public List<Table_Awards> getStudents2(String award)
    {
        List<Table_Awards> studentList = new ArrayList<Table_Awards>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_GRAD, new String[]{T2_KEY_ROLL, T2_KEY_NAME, T2_KEY_AWARD, T2_KEY_DESCRIPTION, T2_KEY_PROGRAM, T2_KEY_DEPT, T2_KEY_COMMENT, T2_KEY_YEAR}, T2_KEY_AWARD + "=?",
                new String[]{award}, null, null, null, null);

        if (cursor.moveToFirst())
        {
            do
            {
                Table_Awards student= new Table_Awards(cursor.getString(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8));
                studentList.add(student);
            } while (cursor.moveToNext());
        }

        return studentList;
    }

    //Run any "Select *" query
    public List<Table_Awards> runSelectQuery2(String award)
    {
        List<Table_Awards> studentList = new ArrayList<Table_Awards>();

        //Log.d("Query : ", query);

        String table = TABLE_AWARDS;
        String[] columns = {T2_KEY_ROLL,T2_KEY_NAME,T2_KEY_AWARD,T2_KEY_DESCRIPTION,T2_KEY_COMMENT, T2_KEY_PROGRAM,T2_KEY_DEPT,T2_KEY_YEAR, T2_KEY_PICTURE};
        String selection = T2_KEY_AWARD + " =?";
        String[] selectionArgs = {award};
        String groupBy = null;
        String having = null;
        String orderBy = null;
        String limit = null;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);

        // looping through all rows and adding to list
        if (cursor.moveToFirst())
        {
            do
            {
                Table_Awards student = new Table_Awards(cursor.getString(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8));
                studentList.add(student);
            } while (cursor.moveToNext());
        }
        return studentList;
    }



    //Get all Award names
    public List<String> getAwards2()
    {

        String query = "SELECT DISTINCT award FROM " + TABLE_AWARDS ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        // looping through all rows and adding to list

        List<String> results = new ArrayList<String>();
        if (cursor.moveToFirst())
        {
            do
            {
                results.add(cursor.getString(0));

            } while (cursor.moveToNext());
        }
        return results;
    }

    //Get Award Description
    public String getDesc2(String award)
    {

        String table = TABLE_AWARDS;
        String[] columns = {T2_KEY_DESCRIPTION};
        String selection = T2_KEY_AWARD + " =?";
        String[] selectionArgs = {award};
        String groupBy = null;
        String having = null;
        String orderBy = null;
        String limit = null;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);

        // looping through all rows and adding to list
        String results = null;
        if (cursor.moveToFirst())
        {
            do
            {
                results = cursor.getString(0);

            } while (cursor.moveToNext());
        }
        return results;
    }

    //Get Comment
    public String getComment2(String award)
    {

        String table = TABLE_AWARDS;
        String[] columns = {T2_KEY_COMMENT};
        String selection = T2_KEY_AWARD + " =?";
        String[] selectionArgs = {award};
        String groupBy = null;
        String having = null;
        String orderBy = null;
        String limit = null;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);

        // looping through all rows and adding to list
        String results = null;
        if (cursor.moveToFirst())
        {
            do
            {
                results = cursor.getString(0);

            } while (cursor.moveToNext());
        }
        return results;
    }

    public void deleteAwardsAndStudents()
    {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_GRAD,null,null);
        db.delete(TABLE_AWARDS,null,null);
        db.close();

    }

    //------------------------------------------------------------------------- Methods for Adding Events in Schedule --TABLE_SCHEDULE --TABLE 3
    // Adding new Event
    public void addEvent(SQLiteDatabase db, String title, String venue, String date, String time)
    {

        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(T3_KEY_EVENT, title);
        values.put(T3_KEY_VENUE, venue);
        values.put(T3_KEY_DATE, date);
        values.put(T3_KEY_TIME, time);

        // Inserting Row
        db.insert(TABLE_SCHEDULE, null, values);

        db.close();
        Log.d("AddEvent","Event added + " + time);
    }

    //Get all Events

    public List<Table_Schedule> getSchedule()
    {
        List<Table_Schedule> eventList = new ArrayList<Table_Schedule>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SCHEDULE, new String[]{T3_KEY_EVENT, T3_KEY_VENUE, T3_KEY_DATE, T3_KEY_TIME},
                null, null, null, null, null);

        if (cursor.moveToFirst())
        {
            do
            {
                Table_Schedule event= new Table_Schedule(cursor.getString(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3));
                eventList.add(event);
                Log.d("AddEventGet","Event added + " + event.getTime());
            } while (cursor.moveToNext());
        }

        return eventList;
    }

    public void deleteSchedule()
    {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SCHEDULE,null,null);
        db.close();
    }


    //------------------------------------------------------------------------- Methods for Adding Announcements --TABLE_ANNOUNCEMENTS -- TABLE 4

    // Adding new Announcement
    public void addAnnouncement(SQLiteDatabase db, String title)
    {

        if(title.equals(""))return;

        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(T4_KEY_TITLE, title);

        // Inserting Row
        db.insert(TABLE_ANNOUNCEMENTS, null, values);

        db.close();
        Log.d("AddAnnouncement","Added + " + title);
    }


    public List<String> getAnnouncements()
    {
        List<String> eventList = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ANNOUNCEMENTS, new String[]{T4_KEY_TITLE},
                null, null, null, null, null);

        if (cursor.moveToFirst())
        {
            do
            {
                String a= new String(cursor.getString(0));
                eventList.add(a);
                Log.d("AddAnnouncementGet","Added + " + a);
            } while (cursor.moveToNext());
        }

        return eventList;
    }

    public void deleteAnnouncements()
    {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ANNOUNCEMENTS,null,null);
        db.close();
    }


    //------------------------------------------------------------------------- Methods for Adding Contacts Auto/Taxi --TABLE_CONTACTS -- TABLE 5


    // Adding new Event
    public void addContact(SQLiteDatabase db, Table_Contact c)
    {

        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(T5_KEY_NAME, c.getName());
        values.put(T5_KEY_NUMBER, c.getNumber());
        values.put(T5_KEY_TRANSPORT, c.getTransport());


        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);

        db.close();
        Log.d("AddContact","Contact added + " + c.getNumber());
    }

    //Get all Contacts

    public List<Table_Contact> getContacts()
    {
        List<Table_Contact> contactList = new ArrayList<Table_Contact>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[]{T5_KEY_NAME, T5_KEY_NUMBER, T5_KEY_TRANSPORT},
                null, null, null, null, null);

        if (cursor.moveToFirst())
        {
            do
            {
                Table_Contact c= new Table_Contact(cursor.getString(0), cursor.getString(1),
                        cursor.getString(2));
                contactList.add(c);
                Log.d("AddContactGet","Contact added + " + c.getNumber());
            } while (cursor.moveToNext());
        }

        return contactList;
    }

    public void deleteContacts()
    {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS,null,null);
        db.close();
    }


    //------------------------------------------------------------------------- Methods for Adding Guests --TABLE_GUESTS -- TABLE 6

    // Adding new Guest
    public void addGuest(SQLiteDatabase db, Table_Guest c)
    {

        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(T6_KEY_NAME, c.getName());
        values.put(T6_KEY_TITLE, c.getTitle());
        values.put(T6_KEY_YEAR, c.getYear());
        values.put(T6_KEY_PICTURE, c.getPicture());
        values.put(T6_KEY_DESCRIPTION, c.getDescription());
        values.put(T6_KEY_TYPE, c.getType());

        // Inserting Row
        db.insert(TABLE_GUESTS, null, values);

        db.close();
        Log.d("AddGuest","Guest added + " + c.getName());

    }

    //Get all guests of a particular type

    public List<Table_Guest> getGuests(String type)
    {
        List<Table_Guest> guestList = new ArrayList<Table_Guest>();

        //Log.d("Query : ", query);

        String table = TABLE_GUESTS;
        String[] columns = {T6_KEY_NAME,T6_KEY_TITLE,T6_KEY_YEAR,T6_KEY_PICTURE,T6_KEY_DESCRIPTION, T6_KEY_TYPE};
        String selection = T6_KEY_TYPE + " =?";
        String[] selectionArgs = {type};
        String groupBy = null;
        String having = null;
        String orderBy = null;
        String limit = null;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);

        // looping through all rows and adding to list
        if (cursor.moveToFirst())
        {
            do
            {
                Table_Guest g = new Table_Guest(cursor.getString(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
                guestList.add(g);
            } while (cursor.moveToNext());
        }
        return guestList;
    }


    public void deleteGuests(String type)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        String table = TABLE_GUESTS;
        String whereClause = T6_KEY_TYPE + "=?";
        String[] whereArgs = new String[] { type };
        db.delete(table, whereClause, whereArgs);
        db.close();
    }

    //------------------------------------------------------------------------- Methods for Adding images --TABLE_IMAGES -- TABLE 7

    // Adding new Image
    public void addImage(SQLiteDatabase db, String name, String type, Bitmap img)
    {

        db = this.getWritableDatabase();

        byte[] data = getBitmapAsByteArray(img);
        ContentValues values = new ContentValues();

        values.put(T7_KEY_NAME, name);
        values.put(T7_KEY_IMAGE, data);
        values.put(T7_KEY_TYPE, type);

        // Inserting Row
        db.insert(TABLE_IMAGES, null, values);

        db.close();
        Log.d("AddImage","Image added + " + name);

    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }
    //Get image

    public Bitmap getImage(String name)
    {

        if(name.equals(""))return null;

        String table = TABLE_IMAGES;
        String[] columns = {T7_KEY_IMAGE};
        String selection = T7_KEY_NAME + " =?";
        String[] selectionArgs = {name};
        String groupBy = null;
        String having = null;
        String orderBy = null;
        String limit = null;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);

        // looping through all rows and adding to list
        if (cur.moveToFirst()){
            byte[] imgByte = cur.getBlob(0);
            cur.close();
            return BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
        }
        if (cur != null && !cur.isClosed()) {
            cur.close();
        }

        return null ;
    }

    public String getImageName(String name, String award)
    {

        if(name.equals(""))return null;

        String table = TABLE_AWARDS;
        String[] columns = {T2_KEY_PICTURE};
        String selection = T2_KEY_NAME + " =? AND  " + T2_KEY_AWARD + " = ?";
        String[] selectionArgs = {name,award};
        String groupBy = null;
        String having = null;
        String orderBy = null;
        String limit = null;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);


        // looping through all rows and adding to list
        String img = "";
        if (cursor.moveToFirst())
        {
            do
            {
                img = new String(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        return img;
    }


    public List<String> getImageList(String type)
    {
        List<String> imageList = new ArrayList<String>();

        //Log.d("Query : ", query);

        String table = "";
        String name = "";
        String[] selectionArgs = null;
        String selection = null;

        if(type.equals("H") || type.equals("C"))
        {
            table = TABLE_GUESTS;
            name = T6_KEY_PICTURE;
            selectionArgs = new String[]{type};
            selection = T6_KEY_TYPE + " =?";
        }
        else
        {
            table = TABLE_AWARDS;
            name = T2_KEY_PICTURE;

        }

        String[] columns = {name};

        String groupBy = null;
        String having = null;
        String orderBy = null;
        String limit = null;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);

        // looping through all rows and adding to list
        if (cursor.moveToFirst())
        {
            do
            {
                String img = new String(cursor.getString(0));
                Log.d("ImageList", img);
                if(!img.equals(""))
                    imageList.add(img);
            } while (cursor.moveToNext());
        }
        return imageList;
    }


    //Delete image of a particular type
    public void deleteImages(String type)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        String table = TABLE_IMAGES;
        String whereClause = T7_KEY_TYPE + "=?";
        String[] whereArgs = new String[] { type };
        db.delete(table, whereClause, whereArgs);
        db.close();
    }


    //------------------------------------------------------------------------- Methods for Adding Prev Recipients --TABLE_PREV -- TABLE 8


    // Adding new prev recipient
    public void addPrevRec(SQLiteDatabase db, Table_Prev_Rec c)
    {

        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(T8_KEY_NAME, c.getName());
        values.put(T8_KEY_DESIGNATION, c.getDesignation());
        values.put(T8_KEY_CONVO_NUM, c.getConvo_num());
        values.put(T8_KEY_COMMENT, c.getComment());
        values.put(T8_KEY_TYPE, c.getType());

        // Inserting Row
        db.insert(TABLE_PREV, null, values);

        db.close();
        Log.d("AddPrev","Prev Rec added + " + c.getName());

    }

    //Get all prev recipients of a particular type

    public List<Table_Prev_Rec> getPrevRec(String type)
    {
        List<Table_Prev_Rec> prevList = new ArrayList<Table_Prev_Rec>();

        //Log.d("Query : ", query);

        String table = TABLE_PREV;
        String[] columns = {T8_KEY_NAME,T8_KEY_DESIGNATION,T8_KEY_CONVO_NUM,T8_KEY_COMMENT,T8_KEY_TYPE};
        String selection = T8_KEY_TYPE + " =?";
        String[] selectionArgs = {type};
        String groupBy = null;
        String having = null;
        String orderBy = null;
        String limit = null;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);

        // looping through all rows and adding to list
        if (cursor.moveToFirst())
        {
            do
            {
                Table_Prev_Rec g = new Table_Prev_Rec(cursor.getString(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor.getString(4));
                prevList.add(g);
            } while (cursor.moveToNext());
        }
        return prevList;
    }


    public void deletePrevRec(String type)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        String table = TABLE_PREV;
        String whereClause = T8_KEY_TYPE + "=?";
        String[] whereArgs = new String[] { type };
        db.delete(table, whereClause, whereArgs);
        db.close();
    }

    //------------------------------------------------------------------------- Methods for Adding Links --TABLE_LINKS -- TABLE 9


    public void addLink(SQLiteDatabase db, String name, String link)
    {

        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(T9_KEY_NAME, name);
        values.put(T9_KEY_LINK, link);


        // Inserting Row
        db.insert(TABLE_LINKS, null, values);

        db.close();
        Log.d("AddLink","Link added + " + name);

    }

    //Get all prev recipients of a particular type

    public List<Pair<String,String>> getLinks()
    {
        List<Pair<String,String>> linkList = new ArrayList<Pair<String, String>>();

        //Log.d("Query : ", query);

        String table = TABLE_LINKS;
        String[] columns = {T9_KEY_NAME,T9_KEY_LINK};
        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;
        String limit = null;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);

        // looping through all rows and adding to list
        if (cursor.moveToFirst())
        {
            do
            {
                String n = cursor.getString(0);
                String l = cursor.getString(1);
                linkList.add(new Pair<String, String>(n,l));
            } while (cursor.moveToNext());
        }
        return linkList;
    }

    public void deleteLinks()
    {

        SQLiteDatabase db = this.getWritableDatabase();
        String table = TABLE_LINKS;
        String whereClause = null;
        String[] whereArgs = null;
        db.delete(table, whereClause, whereArgs);
        db.close();
    }



}


