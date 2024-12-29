package com.group13.Models;

public class MovieModel 
{
    
    public String movieName;
    public String imgPath;
    public String genre;

    public void printMovie()
    {
        System.out.println("Name: "+movieName);
        System.out.println("Image: "+imgPath);
        System.out.println("Genre: "+genre);
    }
}
