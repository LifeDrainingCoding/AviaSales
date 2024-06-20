package com.pracktic.aviasales;

import java.util.ArrayList;
public class AviaLine {
    private boolean isSeatsAvailable = true;
    private static final int airCraftCapacity = 40;
    private int aviaLineId;
    private String crews;
    private String airPlaneType;
    private Long startDate;
    private int availableEcoSeats;
    private int availablePremEcoSeats;
    private int availableBusinessSeats;
    private int availableFirstSeats;
    private int availableSeats;
    private double ecoSeatPrice, premEcoSeatPrice, businessSeatPrice, firstSeatPrice;

    public AviaLine(int aviaLineId, String crews, String airPlaneType, Long startDate, int availableEcoSeats,
                    int availablePremEcoSeats, int availableBusinessSeats, int availableFirstSeats, double ecoSeatPrice,
                    double premEcoSeatPrice, double businessSeatPrice, double firstSeatPrice) {
        this.aviaLineId = aviaLineId;
        this.crews = crews;
        this.airPlaneType = airPlaneType;
        this.startDate = startDate;
        this.availableEcoSeats = availableEcoSeats;
        this.availablePremEcoSeats = availablePremEcoSeats;
        this.availableBusinessSeats = availableBusinessSeats;
        this.availableFirstSeats = availableFirstSeats;
        this.ecoSeatPrice = ecoSeatPrice;
        this.premEcoSeatPrice = premEcoSeatPrice;
        this.businessSeatPrice = businessSeatPrice;
        this.firstSeatPrice = firstSeatPrice;


        availableSeats = availableEcoSeats + availablePremEcoSeats + availableBusinessSeats + availableBusinessSeats;
        if (availableSeats > airCraftCapacity) {
            throw new IllegalArgumentException("Entered seats more than aircraft capacity!");
        } else if (availableSeats == airCraftCapacity) {
            isSeatsAvailable = false;
        } else {
            isSeatsAvailable = true;
        }
    }

    public AviaLine(int aviaLineId) {
        this.aviaLineId = aviaLineId;

    }

    public AviaLine() {

    }

    public boolean isSeatsAvailable() {
        return isSeatsAvailable;
    }

    public void setSeatsAvailable(boolean seatsAvailable) {
        isSeatsAvailable = seatsAvailable;
    }

    public int getAviaLineId() {
        return aviaLineId;
    }

    public void setAviaLineId(int aviaLineId) {
        this.aviaLineId = aviaLineId;
    }

    public String getCrews() {
        return crews;
    }

    public void setCrews(String crews) {
        this.crews = crews;
    }

    public String getAirPlaneType() {
        return airPlaneType;
    }

    public void setAirPlaneType(String airPlaneType) {
        this.airPlaneType = airPlaneType;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public int getAvailablePremEcoSeats() {
        return availablePremEcoSeats;
    }

    public void setAvailablePremEcoSeats(int availablePremEcoSeats) {
        this.availablePremEcoSeats = availablePremEcoSeats;
    }

    public int getAvailableEcoSeats() {
        return availableEcoSeats;
    }

    public void setAvailableEcoSeats(int availableEcoSeats) {
        this.availableEcoSeats = availableEcoSeats;
    }

    public int getAvailableBusinessSeats() {
        return availableBusinessSeats;
    }

    public void setAvailableBusinessSeats(int availableBusinessSeats) {
        this.availableBusinessSeats = availableBusinessSeats;
    }

    public int getAvailableFirstSeats() {
        return availableFirstSeats;
    }

    public void setAvailableFirstSeats(int availableFirstSeats) {
        this.availableFirstSeats = availableFirstSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public double getEcoSeatPrice() {
        return ecoSeatPrice;
    }

    public void setEcoSeatPrice(double ecoSeatPrice) {
        this.ecoSeatPrice = ecoSeatPrice;
    }

    public double getPremEcoSeatPrice() {
        return premEcoSeatPrice;
    }

    public void setPremEcoSeatPrice(double premEcoSeatPrice) {
        this.premEcoSeatPrice = premEcoSeatPrice;
    }

    public double getBusinessSeatPrice() {
        return businessSeatPrice;
    }

    public void setBusinessSeatPrice(double businessSeatPrice) {
        this.businessSeatPrice = businessSeatPrice;
    }

    public double getFirstSeatPrice() {
        return firstSeatPrice;
    }

    public void setFirstSeatPrice(double firstSeatPrice) {
        this.firstSeatPrice = firstSeatPrice;
    }
}
