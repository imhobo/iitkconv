package com.aps.iitconv;

/**
 * Created by imhobo on 31/3/17.
 */


public class Table_Awards
{
    private int id;
    private String roll;
    private String name;
    private String award;
    private String description;
    private String branch;
    private String comment;
    private String year;


    public Table_Awards(int id, String roll, String name, String award, String description, String branch, String comment, String year)
    {
        this.id = id;
        this.roll = roll;
        this.name= name;
        this.award = award;
        this.description = description;
        this.branch = branch;
        this.comment = comment;
        this.year = year;

    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
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

    public String getBranch()
    {
        return branch;
    }

    public void setBranch(String branch)
    {
        this.branch = branch;
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
}