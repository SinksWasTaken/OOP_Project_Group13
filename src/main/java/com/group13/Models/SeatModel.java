package com.group13.Models;

public class SeatModel 
{
    public int seat_id; 
    public boolean sold; 
    public int hall; 
    public int c; 
    public String r; 
    public int session_id; 

    public void printSeat()
    {
        System.out.println("session_id: " +this.session_id); 
        System.out.println("sold: " +this.sold); 
        System.out.println("hall: " +this.hall); 
        System.out.println("c: " +this.c); 
        System.out.println("r: " +this.r); 
        System.out.println("seat_id:" +this.seat_id); 
        
    }
}
