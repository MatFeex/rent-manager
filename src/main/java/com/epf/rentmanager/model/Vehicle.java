package com.epf.rentmanager.model;

import java.time.LocalDate;

public class Vehicle {

    private int nbPlaces;
    private String constructor;
    private int id;
    private LocalDate start;
    private LocalDate end;

    public Vehicle(int id, String constructor, int nbPlaces) {
        this.id = id;
        this.constructor = constructor;
        this.nbPlaces = nbPlaces;
    }

    public Vehicle(){}

    public int getNbPlaces() {
        return nbPlaces;
    }

    public void setNbPlaces(int nbPlaces) {
        this.nbPlaces = nbPlaces;
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
