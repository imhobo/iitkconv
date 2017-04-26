package com.aps.iitconv;

/**
 * Created by imhobo on 31/3/17.
 */


public class Table_Guest
{

    private String name;
    private String title;
    private String year;
    private String picture;

    public Table_Guest(String name, String title, String year, String picture, String description, String type)
    {
        this.name = name;
        this.title = title;
        this.year = year;
        this.picture = picture;
        this.description = description;
        this.type = type;
    }

    private String description;
    private String type;


    public Table_Guest()
    {

    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
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

    public void setPicture(String picture)
    {
        this.picture = picture;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }
}