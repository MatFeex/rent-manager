package com.epf.rentmanager.service;


import java.time.LocalDate;
import java.util.List;

import com.epf.rentmanager.dao.RentDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Rent;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.VehicleDao;
import org.springframework.stereotype.Service;


@Service
public class VehicleService {

	private static final int MIN_SEATS = 2;
	private static final int MAX_SEATS = 9;
	private VehicleDao vehicleDao;
	private RentDao rentDao;
	private VehicleService(VehicleDao vehicleDao, RentDao rentDao) {
		this.vehicleDao = vehicleDao;
		this.rentDao = rentDao;
	}

	public void validateVehicleData(Vehicle vehicle) throws ServiceException {
		if (vehicle.getConstructor().isEmpty()) {
			throw new ServiceException("Invalid manufacturer");
		}
		if (vehicle.getModel().isEmpty()) {
			throw new ServiceException("Invalid model");
		}
		if (vehicle.getSeats() < MIN_SEATS || vehicle.getSeats() > MAX_SEATS) {
			throw new ServiceException("Invalid number of seats");
		}
	}

	public long create(Vehicle vehicle) throws ServiceException {
		validateVehicleData(vehicle);
		try {
			return this.vehicleDao.create(vehicle);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public void update(Vehicle vehicle) throws ServiceException {
		validateVehicleData(vehicle);
		try {
			this.vehicleDao.update(vehicle);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public void delete(Vehicle vehicle) throws ServiceException {
		try {
			for(Rent rent : rentDao.findResaByVehicleId(vehicle.getId())){
				rentDao.delete(rent);
			}
			vehicleDao.delete(vehicle);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public Vehicle findById(long id) throws ServiceException {
		try {
			return this.vehicleDao.findById(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public List<Vehicle> findAll() throws ServiceException {
		try{
			return this.vehicleDao.findAll();
		}
		catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}

	}

	public int getCount() throws ServiceException {
		try{
			return this.vehicleDao.getCount();
		}
		catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}
	
}
