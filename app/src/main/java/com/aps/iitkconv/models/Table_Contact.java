package com.aps.iitkconv.models;

/**
 * Created by imhobo on 31/3/17.
 */


public class Table_Contact
{

    private String name;
    private String number;
    private String transport;


    public Table_Contact(String name, String number, String transport)
    {


        this.name= name;
        this.number = number;
        this.transport = transport;


    }

    public Table_Contact()
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

    public String getNumber()
    {
        return number;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }

    public String getTransport()
    {
        return transport;
    }

    public void setTransport(String transport)
    {
        this.transport = transport;
    }



}