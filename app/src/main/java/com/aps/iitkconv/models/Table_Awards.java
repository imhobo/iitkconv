package com.aps.iitkconv.models;

import android.graphics.Bitmap;

/**
 * Created by imhobo on 31/3/17.
 */


public class Table_Awards
{

    private String roll;
    private String name;
    private String award;
    private String description;
    private String program;
    private String dept;
    private String comment;
    private String year;
    private String picture;


    public Table_Awards(String roll, String name, String award, String description, String program, String dept, String comment, String year, String picture)
    {

        this.roll = roll;
        this.name= name;
        this.award = award;
        this.description = description;
        this.program = program;
        this.dept = dept;
        this.comment = comment;
        this.year = year;
        this.picture = picture;

    }

    public Table_Awards()
    {

    }


    public String getRoll()
    {
        return roll;
    }

    public void setRoll(String roll)
    {
        this.roll = roll;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAward()
    {
        return award;
    }

    public void setAward(String award)
    {
        this.award = award;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getProgram()
    {
        return program;
    }

    public void setProgram(String program)
    {
        this.program = program;
    }

    public String getDept()
    {
        return dept;
    }

    public void setDept(String dept)
    {
        this.dept = dept;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public String getYear()
    {
        return year;
    }

    public void setYear(String year)
    {
        this.year = year;
    }
    public String getPicture()
    {
        return picture;
    }

    public void setPicture(String img)
    {
        this.picture = img;
    }

}