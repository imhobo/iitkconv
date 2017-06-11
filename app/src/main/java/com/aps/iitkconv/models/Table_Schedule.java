package com.aps.iitkconv.models;

/**
 * Created by imhobo on 31/3/17.
 */


public class Table_Schedule {

    private String event;
    private String venue;
    private String date;
    private String time;


    public Table_Schedule(String event, String venue, String date, String time) {

        this.event = event;
        this.venue = venue;
        this.date = date;
        this.time = time;
    }

    public Table_Schedule() {

    }


    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}