package com.epf.rentmanager.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.dao.RentDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;

import com.epf.rentmanager.model.Rent;
import com.epf.rentmanager.model.Vehicle;
import org.springframework.stereotype.Service;

@Service
public class RentService {
    private static final int MAX_CONSECUTIVE_DAYS = 7;
    private static final int MAX_TOTAL_DAYS = 30;
    private RentDao rentDao;
    private RentService(RentDao rentDao) {
        this.rentDao = rentDao;
    }

    private void validateRentData(Rent rent) throws ServiceException {

        // data
        List<Rent> rents;
        try{
            rents = rentDao.findResaByVehicleId(rent.getVehicle().getId());
            rents.remove(rent);
        } catch (DaoException e){
            throw new ServiceException("Impossible de trouver les autres réservations associées à ce véhicule.");
        }
        LocalDate startDate = rent.getStart();
        LocalDate endDate = rent.getEnd();
        // -------------

        if(rent.getEnd().isBefore(rent.getStart())){
            throw new ServiceException("La date de fin ne peut être avant celle du début");
        }
        if (rent.getVehicle() == null) {
            throw new ServiceException("Aucun vehicule associé à cette reservation");
        }
        if (rent.getClient() == null) {
            throw new ServiceException("Aucun client associé à cette reservation");
        }
        if (isVehicleDoubleBooked(rents, rent.getStart(), rent.getEnd())) {
            throw new ServiceException("La voiture est déjà réservée pour la période spécifiée");
        }
        if (isVehicleReservedConsecutively(rents, startDate, endDate)) {
            throw new ServiceException("La voiture est réservée pendant plus de 7 jours consécutifs");
        }
        if (isVehicleReservedWithoutBreak(rents, startDate, endDate)) {
            throw new ServiceException("La voiture est réservée pendant plus de 30 jours sans pause");
        }
    }

    private boolean doPeriodsOverlap(LocalDate start, LocalDate end) {
        return !end.isBefore(start) ;
    }
    private boolean isVehicleDoubleBooked(List<Rent> rents, LocalDate startDate, LocalDate endDate) {
        return rents.stream().anyMatch(rent -> doPeriodsOverlap(rent.getStart(), rent.getEnd()));
    }
    private boolean isVehicleReservedConsecutively(List<Rent> rents, LocalDate startDate, LocalDate endDate) {
        long consecutiveDays = startDate.datesUntil(endDate.plusDays(1))
                .filter(date -> rents.stream().anyMatch(rent -> rent.getStart().compareTo(date) <= 0 && rent.getEnd().compareTo(date) >= 0))
                .count();
        return consecutiveDays > MAX_CONSECUTIVE_DAYS;
    }
    private boolean isVehicleReservedWithoutBreak(List<Rent> rents, LocalDate startDate, LocalDate endDate) {
        long reservedDays = startDate.datesUntil(endDate.plusDays(1))
                .filter(date -> rents.stream().anyMatch(rent -> rent.getStart().compareTo(date) <= 0 && rent.getEnd().compareTo(date) >= 0))
                .count();
        return reservedDays > MAX_TOTAL_DAYS;
    }

    public long create(Rent rent) throws ServiceException {
        validateRentData(rent);
        try {
            return this.rentDao.create(rent);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }
    }

    public void update(Rent rent) throws ServiceException {
        // validateRentData(rent);
        try {
            this.rentDao.update(rent);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }
    }

    public void delete(Rent rent) throws ServiceException {
        try {
            this.rentDao.delete(rent);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }
    }

    public List<Rent> findResaByClientId(int id) throws ServiceException {
        try {
            return rentDao.findResaByClientId(id);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }
    }

    public List<Rent> findResaByVehicleId(int id) throws ServiceException {
        try {
            return rentDao.findResaByVehicleId(id);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }
    }

    public Rent findById(int id) throws ServiceException {
        try {
            return rentDao.findById(id);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    public List<Rent> findAll() throws ServiceException {
        try{
            return rentDao.findAll();
        }
        catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }
    }

    public List<Vehicle> findVehiclesRentedByClient(int client_id) throws ServiceException {
        try{
            return rentDao.findVehiclesRentedByClient(client_id);
        }
        catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }
    }

    public int getCount() throws ServiceException {
        try{
            return rentDao.getCount();
        }
        catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }
    }
}
