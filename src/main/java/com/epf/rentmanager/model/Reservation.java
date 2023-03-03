package com.epf.rentmanager.model;

import java.time.LocalDate;

public class Reservation {
    private String constructor;
    private String model;
    private int nb_places;

    public Reservation(int id, int client_id, int vehicule_id, LocalDate start, LocalDate end){

    }


    public String getConstructor() {
        return constructor;
    }

    public void setConstructor(String constructor) {
        this.constructor = constructor;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getNb_places() {
        return nb_places;
    }

    public void setNb_places(int nb_places) {
        this.nb_places = nb_places;
    }
}
