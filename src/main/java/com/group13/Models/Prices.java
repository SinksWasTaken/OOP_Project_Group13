package com.group13.Models;

public class Prices {

    private double ticketTaxRate;
    private double productTaxRate;
    private double discountRate;
    private double movieTicketPrice;

    public Prices(double ticketTaxRate, double productTaxRate, double discountRate, double movieTicketPrice) {
        this.ticketTaxRate = ticketTaxRate;
        this.productTaxRate = productTaxRate;
        this.discountRate = discountRate;
        this.movieTicketPrice = movieTicketPrice;
    }

    public double getTicketTaxRate() {
        return ticketTaxRate;
    }

    public void setTicketTaxRate(double ticketTaxRate) {
        this.ticketTaxRate = ticketTaxRate;
    }

    public double getProductTaxRate() {
        return productTaxRate;
    }

    public void setProductTaxRate(double productTaxRate) {
        this.productTaxRate = productTaxRate;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }

    public double getMovieTicketPrice() {
        return movieTicketPrice;
    }

    public void setMovieTicketPrice(double movieTicketPrice) {
        this.movieTicketPrice = movieTicketPrice;
    }
}
