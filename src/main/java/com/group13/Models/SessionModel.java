package com.group13.Models;
import java.sql.Date;
import java.sql.Timestamp;


public class SessionModel 
{
    private int session_id;
    private Integer movieId;
    private Date sessionDate;
    private Timestamp sessionTime;
    private int hall_number;

    public void setSessionID(int session_id)
    {
        this.session_id = session_id;
    }

    public int getSessionID()
    {
        return session_id;
    }

    public void setSessionDate(Date sessionDate)
    {
        this.sessionDate = sessionDate;
    }

    public Date getSessionDate()
    {
        return this.sessionDate;
    }

    public void setSessionTime(Timestamp sessionTime)
    {
        this.sessionTime = sessionTime;
    }

    public Timestamp getSessionTime()
    {
        return this.sessionTime;
    }

    public void setMovieID(Integer movieId)
    {
        this.movieId = movieId;
    }

    public Integer getMovieID()
    {
        return movieId;
    }

    public void setHallNumber(int hall_number)
    {
        this.hall_number = hall_number;
    }

    public int getHallNumber()
    {
        return hall_number;
    }
}
