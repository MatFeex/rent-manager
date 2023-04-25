package com.epf.rentmanager.service;


import java.util.List;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.VehicleDao;
import org.springframework.stereotype.Service;


@Service
public class VehicleService {

	private VehicleDao vehicleDao;
	private VehicleService(VehicleDao vehicleDao) {
		this.vehicleDao = vehicleDao;
	}

	private void validateVehicleData(Vehicle vehicle) throws ServiceException {
		if (vehicle.getConstructor().isEmpty()) {
			throw new ServiceException("Invalid manufacturer");
		}
		if (vehicle.getModel().isEmpty()) {
			throw new ServiceException("Invalid model");
		}
		if (vehicle.getSeats() <= 1) {
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
			validateVehicleData(vehicle);
			this.vehicleDao.delete(vehicle);
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
