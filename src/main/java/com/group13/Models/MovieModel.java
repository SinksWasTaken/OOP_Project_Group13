package com.group13.Models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MovieModel {
    private int movieID;
    private String movieName;
    private String imgPath;
    private String genre;
    private String summary;
    private ImageView movieImage;

    public MovieModel(int movieID, String movieName, String imgPath, String genre, String summary) {
        this.movieID = movieID;
        this.movieName = movieName;
        this.imgPath = imgPath;
        this.genre = genre;
        this.summary = summary;
        this.movieImage = new ImageView(new Image("file:" + imgPath, 60, 60, true, true));
    }

    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getImgPath() {
        return imgPath;
    }

    public String getGenre() {
        return genre;
    }

    public String getSummary() {
        return summary;
    }

    public ImageView getMovieImage() {
        return movieImage;
    }
}