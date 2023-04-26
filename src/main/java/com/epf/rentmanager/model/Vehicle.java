package com.epf.rentmanager.model;


public class Vehicle {

    private int seats;
    private String constructor;
    private String model;
    private int id;

    public Vehicle(int id, String constructor, String model, int seats) {
        this.id = id;
        this.constructor = constructor;
        this.model = model;
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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
    @Override
    public String toString() {
        return String.format("Le véhicule %d - %s %s a %d disponibles",id,constructor,model,seats);
    }
}
