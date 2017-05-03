package com.aps.iitkconv.models;

/**
 * Created by imhobo on 31/3/17.
 */


public class Table_Prev_Rec
{

    private String name;
    private String designation;
    private String convo_num;
    private String comment;
    private String type;

    public Table_Prev_Rec(String name, String designation, String convo_num, String comment, String type)
    {
        this.name = name;
        this.designation = designation;
        this.convo_num = convo_num;
        this.comment = comment;
        this.type = type;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDesignation()
    {
        return designation;
    }

    public void setDesignation(String designation)
    {
        this.designation = designation;
    }

    public String getConvo_num()
    {
        return convo_num;
    }

    public void setConvo_num(String convo_num)
    {
        this.convo_num = convo_num;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
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