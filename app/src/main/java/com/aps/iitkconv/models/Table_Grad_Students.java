package com.aps.iitkconv.models;

/**
 * Created by imhobo on 31/3/17.
 */


public class Table_Grad_Students
{

    private String roll;
    private String name;
    private String advisers;
    private String description;
    private String program;
    private String dept;


    public Table_Grad_Students(String roll, String name, String program, String dept, String advisors, String description)
    {

        this.roll = roll;
        this.name= name;
        this.advisers = advisors;
        this.description = description;
        this.program = program;
        this.dept = dept;

    }

    public Table_Grad_Students(String roll, String name, String program, String dept )
    {

        this.roll = roll;
        this.name= name;

        this.advisers = "";
        this.description = "";
        this.program = program;
        this.dept = dept;

    }

    public Table_Grad_Students()
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



}