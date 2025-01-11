package com.group13.Models;

import com.group13.Views.ViewManager;
import com.mysql.cj.Session;

public class Model {
    private static Model model;
    private final ViewManager viewManager;
    private Worker currentWorker;
    private Worker nextWorker;
    private Product selectedProduct;
    private Prices prices;
    private MovieModel movieModel;
    private SeatModel seatModel;
    private SessionModel sessionModel;
    private TicketModel ticketModel;

    private Model() {
        this.viewManager = new ViewManager();
    }

    public static synchronized Model getInstance() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    public ViewManager getViewManager() {
        return viewManager;
    }

    public Worker getCurrentWorker() {
        return currentWorker;
    }

    public void setCurrentWorker(Worker currentWorker) {
        this.currentWorker = currentWorker;
    }

    public Worker getNextWorker() {
        return nextWorker;
    }

    public void setNextWorker(Worker nextWorker) {
        this.nextWorker = nextWorker;
    }

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public Prices getPrices() {
        return prices;
    }

    public void setPrices(Prices prices) {
        this.prices = prices;
    }

    public MovieModel getMovieModel() {
        return movieModel;
    }

    public void setMovieModel(MovieModel movieModel) {
        this.movieModel = movieModel;
    }

    public SeatModel getSeatModel() {
        return seatModel;
    }

    public void setSeatModel(SeatModel seatModel) {
        this.seatModel = seatModel;
    }

    public SessionModel getSessionModel() {
        return sessionModel;
    }

    public void setSessionModel(SessionModel sessionModel) {
        this.sessionModel = sessionModel;
    }

    public TicketModel getTicketModel() {
        return ticketModel;
    }

    public void setTicketModel(TicketModel ticketModel) {
        this.ticketModel = ticketModel;
    }
}
