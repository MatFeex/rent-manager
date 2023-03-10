package com.epf.rentmanager.service;

import java.util.List;

import com.epf.rentmanager.dao.RentDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Rent;

public class RentService {

    private RentDao reservationDao;
    public static RentService instance;

    private RentService() {
        this.reservationDao = RentDao.getInstance();
    }

    public static RentService getInstance() {
        if (instance == null) {
            instance = new RentService();
        }
        return instance;
    }


    public long create(Rent Reservation) throws ServiceException {
        try {
            return RentDao.getInstance().create(Reservation);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }
    }

    public List<Rent> findResaByClientId(int id) throws ServiceException {
        try {
            return RentDao.getInstance().findResaByClientId(id);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }
    }

    public List<Rent> findResaByVehicleId(int id) throws ServiceException {
        try {
            return RentDao.getInstance().findResaByVehicleId(id);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }
    }

    public List<Rent> findAll() throws ServiceException {
        try{
            return RentDao.getInstance().findAll();
        }
        catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }
    }

    public int getCount() throws ServiceException {
        try{
            return RentDao.getInstance().getCount();
        }
        catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }
    }



}
