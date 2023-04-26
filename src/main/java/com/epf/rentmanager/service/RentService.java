package com.epf.rentmanager.service;

import java.util.List;

import com.epf.rentmanager.dao.RentDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Rent;
import com.epf.rentmanager.model.Vehicle;
import org.springframework.stereotype.Service;

@Service
public class RentService {

    private RentDao rentDao;
    private RentService(RentDao rentDao) {
        this.rentDao = rentDao;
    }

    private void validateRentData(Rent rent) throws ServiceException {
        if(rent.getEnd().isBefore(rent.getStart())){
            throw new ServiceException("La date de fin ne peut être avant celle du début");
        }
        if (rent.getVehicle() == null) {
            throw new ServiceException("Aucun vehicule associé à cette reservation");
        }
        if (rent.getClient() == null) {
            throw new ServiceException("Aucun client associé à cette reservation");
        }
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
        validateRentData(rent);
        try {
            this.rentDao.update(rent);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }
    }

    public void delete(Rent rent) throws ServiceException {
        try {
            validateRentData(rent);
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
