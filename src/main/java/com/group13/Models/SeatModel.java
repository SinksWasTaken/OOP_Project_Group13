package com.group13.Models;

public class SeatModel 
{
    public int seat_id; 
    public boolean sold; 
    public int hall; 
    public String seat_name;
    public int session_id; 

    public void setSeatID(int seat_id)
    {
        this.seat_id = seat_id;
    }

    public int getSeatID()
    {
        return seat_id;
    }

    public void setSold(boolean sold)
    {
        this.sold = sold;
    }

    public boolean getSold()
    {
        return sold;
    }

    public void setHall(int hall)
    {
        this.hall = hall;
    }

    public int getHall()
    {
        return hall;
    }

    public void setSeatName(String seat_name)
    {
        this.seat_name = seat_name;
    }

    public String getSeatName()
    {
        return seat_name;
    }

    public void setSessionID(int session_id)
    {
        this.session_id = session_id;
    }

    public int getSessionID()
    {
        return session_id;
    }
}
