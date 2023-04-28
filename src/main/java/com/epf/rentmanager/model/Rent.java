package com.epf.rentmanager.model;


import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;

import java.time.LocalDate;

public class Rent {

    private int id;
    private LocalDate start;
    private LocalDate end;

    private Vehicle vehicle;
    private Client client;

    public Rent(){}

    public Rent(int id, Vehicle vehicle, Client client, LocalDate start, LocalDate end) {
        this.id = id;
        this.vehicle = vehicle;
        this.client = client;
        this.start = start;
        this.end = end;
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

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return String.format("Réservation %d pour le client %d (%s %s): %s de chez %s (%d places)",id,client.getId(),client.getName(),client.getLast_name(),vehicle.getModel(),vehicle.getConstructor(), vehicle.getSeats());
    }
}
