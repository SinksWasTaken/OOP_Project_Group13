package com.group13.Models;
import java.sql.Date;
import java.sql.Timestamp;

public class TicketModel 
{
    public int ticketNumber;
    public String customerName;
    public int age;
    public String extraProducts;
    public Double price;
    public String customerSuggestions;
    public String movieName;
    public Timestamp sessionTime;
    public Date sessionDate;
    public int sessionHall;
    public int seatCol;
    public String seatRow;

    
    public String genre;

    public void printMovie()
    {
        
        System.out.println("ticketNumber:\t"+ this.ticketNumber);
        System.out.println("customerName:\t"+ this.customerName);
        System.out.println("age:\t"+ this.age);
        System.out.println("extraProducts:\t"+ this.extraProducts);
        System.out.println("price:\t"+ this.price);
        System.out.println("customerSuggestions:\t"+ this.customerSuggestions);
        System.out.println("movieName:\t"+ this.movieName);
        System.out.println("sessionTime:\t"+ this.sessionTime);
        System.out.println("sessionDate:\t"+ this.sessionDate);
        System.out.println("sessionHall:\t"+ this.sessionHall);
        System.out.println("seatCol:\t"+ this.seatCol);
        System.out.println("seatRow:\t"+ this.seatRow);
    }
}
