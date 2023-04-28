package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Rent;
import com.epf.rentmanager.service.RentService;
import com.epf.rentmanager.utils.IOUtils;

import java.time.LocalDate;
import java.util.List;

public class CLIRent {
    private final RentService rentService;
    private final CLIClient cliClient;
    private final CLIVehicle cliVehicle;

    public CLIRent(RentService rentService,CLIClient cliClient,CLIVehicle cliVehicle) {
        this.rentService = rentService;
        this.cliClient = cliClient;
        this.cliVehicle = cliVehicle;
    }

    public void options() {

        IOUtils.print("""
						  [1] Lister toutes les réservations
						  [2] Lister toutes les réservations associées à un Client donné
						  [3] Lister toutes les réservations associées à un Véhicule donné
						  """);
        int choice = IOUtils.readInt("\nVotre choix : ");
        switch (choice) {
            case 1 -> readAll();
            case 2 -> readForClient();
            case 3 -> readForVehicle();
            default -> IOUtils.print("Option invalide.");
        }
    }

    public void readAll() {
        try {
            for (Rent rent : rentService.findAll()) {
                IOUtils.print(rent.toString());
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    public void readForClient() {
        try {
            for (Rent rent : rentService.findResaByClientId(cliClient.select().getId())) {
                IOUtils.print(rent.toString());
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    public void readForVehicle() {
        try {
            for (Rent rent : rentService.findResaByVehicleId(cliVehicle.select().getId())) {
                IOUtils.print(rent.toString());
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    public void create() {
        IOUtils.print("Création d'une réservation");
        Rent rent = new Rent();
        try {
            rent.setClient(cliClient.select());
            rent.setVehicle(cliVehicle.select());
            rent.setStart(IOUtils.readDate("Entrez une date de début de réservation : ", true));
            LocalDate end;
            do {
                end = IOUtils.readDate("Entrez une date de fin de réservation : ", true);
            } while (end.isBefore(rent.getStart()));
            rent.setEnd(end);
            IOUtils.print(String.format("La réservation a été créée avec l'identifiant %d",rentService.create(rent)));
        } catch (ServiceException e) {
            e.printStackTrace();
            IOUtils.print("La réservation n'a pas pu être créée.");
        }
    }

    public Rent select() throws ServiceException {
        List<Rent> rentList = rentService.findAll();
        int index;
        do {
            for (int i = 0; i < rentList.size(); i++) {
                IOUtils.print(String.format(" [%d] %s", i+1, rentList.get(i)));
            }
            index = IOUtils.readInt("Saisissez l'index : ");
        } while (index < 1 || index > rentList.size());

        return rentList.get(index - 1);
    }

    public void delete() {
        IOUtils.print("Supprimer une réservation");
        try {
            rentService.delete(rentService.findById(select().getId()));
            IOUtils.print("Reservation supprimée.");
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}