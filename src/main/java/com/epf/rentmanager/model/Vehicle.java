package com.epf.rentmanager.model;

import java.time.LocalDate;

public class Vehicle {

    private int seats;
    private String constructor;
    private int id;
    private LocalDate start;
    private LocalDate end;


    public Vehicle(int id, String constructor, int seats) {
        this.id = id;
        this.constructor = constructor;
        this.seats = seats;
    }

    public Vehicle(){}

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String getConstructor() {
        return constructor;
    }

    public void setConstructor(String constructor) {
        this.constructor = constructor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }
}
