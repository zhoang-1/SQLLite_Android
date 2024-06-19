package com.example.learning.Model;

public class Beer implements Serializable {
    private int BeerCode;
    private String BeerName;
    private double BeerPrice;
    //constructor
    public Beer(int beerCode, String beerName, double beerPrice) {
        BeerCode = beerCode;
        BeerName = beerName;
        BeerPrice = beerPrice;
    }

    public int getBeerCode() {
        return BeerCode;
    }

    public void setBeerCode(int beerCode) {
        BeerCode = beerCode;
    }

    public String getBeerName() {
        return BeerName;
    }

    public void setBeerName(String beerName) {
        BeerName = beerName;
    }

    public double getBeerPrice() {
        return BeerPrice;
    }

    public void setBeerPrice(double beerPrice) {
        BeerPrice = beerPrice;
    }

    @Override
    public String toString() {
        return String.valueOf(BeerCode) + " - " + BeerName + " - " + String.format("%.0f", BeerPrice) + "VND";
    }
}
