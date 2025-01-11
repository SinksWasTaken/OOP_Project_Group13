package com.group13.Models;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class SessionModel 
{
    public int session_id;
    public int movie_id;
    public LocalDateTime session_datetime;
    public int hall_number;

    public void setSessionID(int session_id)
    {
        this.session_id = session_id;
    }

    public int getSessionID()
    {
        return session_id;
    }

    public void setSessionDate(LocalDateTime session_date)
    {
        this.session_datetime = session_date;
    }

    public LocalDateTime getSessionDate()
    {
        return session_datetime;
    }

    public void setMovieID(int movie_id)
    {
        this.movie_id = movie_id;
    }

    public int getMovieID()
    {
        return movie_id;
    }

    public void setHallNumber(int hall_number)
    {
        this.hall_number = hall_number;
    }

    public int setHallNumber()
    {
        return hall_number;
    }
}
