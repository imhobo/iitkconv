package com.aps.iitconv;

/**
 * Created by imhobo on 31/3/17.
 */


public class Table_Grad_Students
{
    private int id;
    private String roll;
    private String name;
    private String advisers;
    private String description;
    private String branch;
    private String dept;


    public Table_Grad_Students(int id, String roll, String name, String branch, String dept, String advisors, String description)
    {
        this.id = id;
        this.roll = roll;
        this.name= name;
        this.advisers = advisors;
        this.description = description;
        this.branch = branch;
        this.dept = dept;

    }

    public Table_Grad_Students(int id, String roll, String name, String branch, String dept )
    {
        this.id = id;
        this.roll = roll;
        this.name= name;

        this.advisers = "";
        this.description = "";
        this.branch = branch;
        this.dept = dept;

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

    public String getAdvisers()
    {
        return advisers;
    }

    public void setAdvisers(String advisers)
    {
        this.advisers = advisers;
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

    public String getDept()
    {
        return dept;
    }

    public void setDept(String dept)
    {
        this.dept = dept;
    }



}