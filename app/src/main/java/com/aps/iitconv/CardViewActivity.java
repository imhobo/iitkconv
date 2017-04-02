package com.aps.iitconv;

/**
 * Created by imhobo on 31/3/17.
 */

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;


public class CardViewActivity extends MainActivity
{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "CardViewActivity";
    private DBHandler_Grad db;
    int value = -1;

    private ArrayList<DataObject> depts;
    private ArrayList<DataObject> branches;
    private int dep = -1, branch = -1;

    private ArrayList<DataObject> awards;
    private final int AWARDS_WITH_PICS = 5;
    private int awardNum = -1;

    private Bundle b;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //When this class is called from MainActivity or some other activity
        if(value==-1)
        {
            super.onCreate(savedInstanceState);
        }

        b = getIntent().getExtras();
        if(b != null)
            value = b.getInt("key");


        //Clearing the existing UI
        frameLayout.removeAllViews();

        //Set different card views here.
        if(value == 3 && awardNum > -1)
        {
            getLayoutInflater().inflate(R.layout.card_view_award, frameLayout);
        }
        else
        {
            getLayoutInflater().inflate(R.layout.card_view_generic, frameLayout);
        }

        //Table created
        db = DBHandler_Grad.getInstance(this);


        //This should be done only after getLayoutInflater is called on frameLayout
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

//        Log.d("Value","val=" + value) ;
        if(value==1)
            mAdapter = new MyRecyclerViewAdapter(getDataSet(),1);

        else if(value==3 && awardNum == -1)
        {
            awards = getAwards();
            mAdapter = new MyRecyclerViewAdapter(awards, 3);
        }

        else if(value==3 && awardNum > -1)
        {
            String curAward = awards.get(awardNum).getmText1();
            String curDesc = db.getDesc2(curAward);
            ArrayList<DataObject> students = getStudents2(curAward);

            TextView award = (TextView) findViewById(R.id.textViewA1);
            TextView desc = (TextView) findViewById(R.id.textViewA2);
            award.setText(curAward);
            desc.setText(curDesc);


            if(awardNum<AWARDS_WITH_PICS)
                mAdapter = new MyRecyclerViewAdapter(students, 30);
            else
                mAdapter = new MyRecyclerViewAdapter(students, 31);
        }

        else if(value==4 && dep == -1)
        {
            depts = getDepts();
            mAdapter = new MyRecyclerViewAdapter(depts,4);
        }

        else if(value == 4 && dep > -1 && branch == -1)
        {
            String curDep = depts.get(dep).getmText1();
            this.setTitle(curDep);
            branches = getBranches(curDep);
            mAdapter = new MyRecyclerViewAdapter(branches,40);
        }

        else if(value == 4 && dep > -1 && branch > -1)
        {
            String curDep = depts.get(dep).getmText1();
            String curBr = branches.get(branch).getmText1();
            this.setTitle(curBr);
            ArrayList<DataObject> students = getStudents1(curDep, curBr);
            //Log.d("Branch", depts.get(dep).getmText1() + ":"+ branches.get(branch).getmText1());
            if(!curDep.equals("Phd"))
                mAdapter = new MyRecyclerViewAdapter(students,400);
            else
                mAdapter = new MyRecyclerViewAdapter(students,401);

        }

        mRecyclerView.setAdapter(mAdapter);

        // Code to Add an item with default animation
        //((MyRecyclerViewAdapter) mAdapter).addItem(obj, index);

        // Code to remove an item with default animation
        //((MyRecyclerViewAdapter) mAdapter).deleteItem(index);
    }


    @Override
    protected void onResume()
    {
        super.onResume();
        ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v)
            {
                Log.i(LOG_TAG, " Clicked on Item " + position );

                if(value == 3 && awardNum == -1)
                {
                    awardNum = position;
                    onCreate(b);
                }

                else if(value == 3 && awardNum > -1)
                {

                    //Click on the student name
                }

                else if(value==4 && dep == -1)
                {
                    dep = position;
                    onCreate(b);
                }

                else if(value==4 && dep > -1 && branch == -1)
                {
                    branch = position;
                    onCreate(b);
                }

                else if(value==4 && dep > -1 && branch > -1)
                {
                    //Click on the student name
                }


            }
        });
    }

    //Possibly the worst way to implement the back button feature.
    @Override
    public void onBackPressed()
    {
        Log.d("CDA", "onBackPressed Called");

        if(value==1 || value ==2)
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
            onCreate(b);
        }

        else if(value==4 && dep == -1)
        {
            finish();
        }

        else if(value==4 && dep > -1 && branch == -1)
        {
            dep = -1;
            onCreate(b);
        }

        else if(value==4 && dep > -1 && branch > -1)
        {
            branch = -1;
            onCreate(b);
        }

    }

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






    private ArrayList<DataObject> getDepts()
    {
        ArrayList results = new ArrayList<DataObject>();
        ArrayList<String> tempHolder = new ArrayList<String>();
        tempHolder = (ArrayList) db.getDept1();

        for (String i : tempHolder)
        {
            results.add(new DataObject(i));
        }

        return results;
    }

    private ArrayList<DataObject> getBranches(String dept)
    {
        ArrayList results = new ArrayList<DataObject>();
        ArrayList<String> tempHolder = new ArrayList<String>();
        tempHolder = (ArrayList) db.getBranch1(dept);

        for (String i : tempHolder)
        {
            results.add(new DataObject(i));
        }

        return results;
    }

    private ArrayList<DataObject> getStudents1(String dept, String branch)
    {
        ArrayList<Table_Grad_Students> students = new ArrayList<Table_Grad_Students>();
        ArrayList<DataObject> results = new ArrayList<DataObject>();

        String query = "SELECT * FROM Table_Grad_Students WHERE dept = " + "'" + dept + "'" + " AND branch = " + "'" + branch + "'";
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

        //String query = "SELECT * FROM Table_Awards WHERE award = " + "'" + award + "'";

        students = (ArrayList) db.runSelectQuery2(award);

        int size = students.size();
        Log.d("Size of students : ", String.valueOf(size));

        for (int i = 0 ; i< size; i++)
        {
            Table_Awards t = students.get(i);
            DataObject obj= new DataObject(String.valueOf(t.getId()),t.getRoll(), t.getName(), award, t.getDescription(), t.getComment(), t.getBranch(), t.getYear());
            //Log.d("getStudents2",String.valueOf(t.getId())+t.getRoll()+t.getName()+award+t.getDescription()+t.getDept()+t.getBranch()+t.getYear());
            results.add(obj);
        }
        return results;
    }


}