package com.group13.Models;
import java.sql.Date;
import java.sql.Timestamp;

public class SessionModel 
{
    public int session_id;
    public Date session_date; 
    public Timestamp session_time; 
    public int hall_number; 
    public String movie_name; 

    public void printSession()
    {
        System.out.print("\n");
        System.out.println("ID:\t"+this.session_id);
        System.out.println("Date:\t"+this.session_date.toString());
        System.out.println("Time:\t"+this.session_time.toString().substring(11, 19));
        System.out.println("Hall:\t"+this.hall_number);
        System.out.println("Movie:\t"+this.movie_name);

    }
}
