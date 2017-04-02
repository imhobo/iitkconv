package com.aps.iitconv;

/**
 * Created by imhobo on 31/3/17.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBHandler_Grad extends SQLiteOpenHelper
{

    private static DBHandler_Grad sInstance;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Database";

    private static SQLiteDatabase db = null;

    //Table 1 and Table 2 resp.
    private static final String TABLE_GRAD = "Table_Grad_Students";
    private static final String TABLE_AWARDS = "Table_Awards";

    // Table1 Columns names
    private static final String T1_KEY_ID = "id";
    private static final String T1_KEY_ROLL = "roll";
    private static final String T1_KEY_NAME = "name";
    private static final String T1_KEY_ADVISERS = "advisers";
    private static final String T1_KEY_DESCRIPTION = "desc";
    private static final String T1_KEY_BRANCH = "branch";
    private static final String T1_KEY_DEPT = "dept";

    // Table2 Columns names
    private static final String T2_KEY_ID = "id";
    private static final String T2_KEY_ROLL = "roll";
    private static final String T2_KEY_NAME = "name";
    private static final String T2_KEY_AWARD = "award";
    private static final String T2_KEY_DESCRIPTION = "desc";
    private static final String T2_KEY_BRANCH = "branch";
    private static final String T2_KEY_COMMENT = "comment";
    private static final String T2_KEY_YEAR = "year";



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
        + T1_KEY_ID + " INTEGER PRIMARY KEY," + T1_KEY_ROLL + " TEXT,"
        + T1_KEY_NAME + " TEXT," + T1_KEY_BRANCH + " TEXT," + T1_KEY_DEPT + " TEXT," + T1_KEY_ADVISERS + " TEXT," + T1_KEY_DESCRIPTION + " TEXT)";

        db.execSQL(CREATE_TABLE_1);

        String CREATE_TABLE_2 = "CREATE TABLE " + TABLE_AWARDS + "("
                + T2_KEY_ID + " INTEGER PRIMARY KEY," + T2_KEY_ROLL + " TEXT,"
                + T2_KEY_NAME + " TEXT," + T2_KEY_AWARD + " TEXT," + T2_KEY_DESCRIPTION + " TEXT," + T2_KEY_BRANCH + " TEXT," + T2_KEY_COMMENT + " TEXT," + T2_KEY_YEAR + " TEXT)";

        db.execSQL(CREATE_TABLE_2);



        //Write code for inserting all students data in the database.
        //Use the addStudent function
        addStudent1(db,new Table_Grad_Students(1,"15111041","safal","Computer Science","M.Tech","TVP","Mookit"));
        addStudent1(db,new Table_Grad_Students(2,"15111042","ankit","Elec Science","M.Des","TVP","Mookit2"));
        addStudent1(db,new Table_Grad_Students(3,"15111043","pulkit","Mech Science","MBA","TVP","Mookit3"));
        addStudent1(db,new Table_Grad_Students(4,"15111044","t1","Bio Science","Phd","TVP","Mookit4"));
        addStudent1(db,new Table_Grad_Students(5,"15111045","t2","Env Science","Dual","TVP","Mookit5"));
        addStudent1(db,new Table_Grad_Students(6,"15111046","t3","Computer Science","MS","TVP","Mookit6"));
        addStudent1(db,new Table_Grad_Students(7,"15111047","t4","Computer Science","B.Tech","TVP","Mookit7"));

        addStudent2(db,new Table_Awards(1,"15111041","safal","President's Gold Medal","Prestigious very amazing","Maths","M.Tech","2008"));
        addStudent2(db,new Table_Awards(2,"15111042","ankit","Award 2","Some text 2","Civil","M.Des","2009"));
        addStudent2(db,new Table_Awards(3,"15111043","pulkit","Award 3","Some text 3","Mechanical","MBA","3012"));
        addStudent2(db,new Table_Awards(4,"15111044","t1","Award 4","Some text 4","Physics","Phd","1678"));
        addStudent2(db,new Table_Awards(5,"15111045","t2","Award 5","Some text 5","Env","Dual","1879"));
        addStudent2(db,new Table_Awards(6,"15111046","t3","Award 6","Some text 6","Computer","MS","1409"));
        addStudent2(db,new Table_Awards(7,"15111047","t4","Award 7","Some text 7","Electrical","B.Tech","1564"));


        Log.d("DATABASE onCreate", "Tables created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GRAD);
        // Creating tables again
        onCreate(db);
    }

    //----------------------------------------------------------------------------Methods for Table 1 -- TABLE_GRAD_STUDENTS

    // Adding new student
    public void addStudent1(SQLiteDatabase db, Table_Grad_Students student)
    {

        ContentValues values = new ContentValues();

        values.put(T1_KEY_ID, student.getId());
        values.put(T1_KEY_NAME, student.getName());
        values.put(T1_KEY_ROLL, student.getRoll());
        values.put(T1_KEY_ADVISERS, student.getAdvisers());
        values.put(T1_KEY_DESCRIPTION, student.getDescription());
        values.put(T1_KEY_BRANCH, student.getBranch());
        values.put(T1_KEY_DEPT, student.getDept());

        // Inserting Row
        db.insert(TABLE_GRAD, null, values);

        Log.d("AddStudent","Student added + " + student.getName());
    }

    // Getting one student
    public Table_Grad_Students getStudent1(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_GRAD, new String[]{T1_KEY_ID, T1_KEY_ROLL, T1_KEY_NAME, T1_KEY_ADVISERS, T1_KEY_DESCRIPTION, T1_KEY_BRANCH, T1_KEY_DEPT}, T1_KEY_ID + "=?",
        new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Table_Grad_Students student = new Table_Grad_Students(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));

        return student;
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
                Table_Grad_Students student = new Table_Grad_Students(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
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
                Table_Grad_Students student = new Table_Grad_Students(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
                studentList.add(student);
            } while (cursor.moveToNext());
        }
        return studentList;
    }

    //Get list of all departments
    public List<String> getDept1()
    {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT DISTINCT dept FROM " + TABLE_GRAD;
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

    //Get list of all branches in a department
    public List<String> getBranch1(String dep)
    {

        String query = "SELECT DISTINCT branch FROM " + TABLE_GRAD + " WHERE dept = " + "'" + dep + "'";
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

    // Getting student Count
    public int getStudentCount1()
    {
        String countQuery = "SELECT * FROM " + TABLE_GRAD;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }
    // Updating a student
    public int updateStudent1(Table_Grad_Students student)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(T1_KEY_NAME, student.getName());
        values.put(T1_KEY_ROLL, student.getRoll());
        values.put(T1_KEY_ADVISERS, student.getAdvisers());
        values.put(T1_KEY_DESCRIPTION, student.getDescription());
        values.put(T1_KEY_BRANCH, student.getBranch());
        values.put(T1_KEY_DEPT, student.getDept());


        return db.update(TABLE_GRAD, values, T1_KEY_ID + " = ?",
                new String[]{String.valueOf(student.getId())});
    }

    // Deleting a student
    public void deleteStudent1(Table_Grad_Students student)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_GRAD, T1_KEY_ID + " = ?",
                new String[] { String.valueOf(student.getId()) });
        db.close();
    }



    //------------------------------------------------------------------------- Methods for Table 2 -- TABLE_AWARDS

    // Adding new student
    public void addStudent2(SQLiteDatabase db, Table_Awards student)
    {

        ContentValues values = new ContentValues();

        values.put(T2_KEY_ID, student.getId());
        values.put(T2_KEY_NAME, student.getName());
        values.put(T2_KEY_ROLL, student.getRoll());
        values.put(T2_KEY_AWARD, student.getAward());
        values.put(T2_KEY_DESCRIPTION, student.getDescription());
        values.put(T2_KEY_BRANCH, student.getBranch());
        values.put(T2_KEY_COMMENT, student.getComment());
        values.put(T2_KEY_YEAR, student.getYear());

        // Inserting Row
        db.insert(TABLE_AWARDS, null, values);

        Log.d("AddStudent2","Student added + " + student.getName());
    }

    // Getting students for a particular award
    public List<Table_Awards> getStudents2(String award)
    {
        List<Table_Awards> studentList = new ArrayList<Table_Awards>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_GRAD, new String[]{T2_KEY_ID, T2_KEY_ROLL, T2_KEY_NAME, T2_KEY_AWARD, T2_KEY_DESCRIPTION, T2_KEY_BRANCH, T2_KEY_COMMENT, T2_KEY_YEAR}, T2_KEY_AWARD + "=?",
                new String[]{award}, null, null, null, null);

        if (cursor.moveToFirst())
        {
            do
            {
                Table_Awards student= new Table_Awards(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
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
        String[] columns = {T2_KEY_ID,T2_KEY_ROLL,T2_KEY_NAME,T2_KEY_AWARD,T2_KEY_DESCRIPTION,T2_KEY_COMMENT,T2_KEY_BRANCH,T2_KEY_YEAR};
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
                Table_Awards student = new Table_Awards(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
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



}
