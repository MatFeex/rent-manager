package com.epf.rentmanager.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.persistence.ConnectionManager;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Vehicle;

public class VehicleDao {
	
	private static VehicleDao instance = null;
	private VehicleDao() {}
	public static VehicleDao getInstance() {
		if(instance == null) {
			instance = new VehicleDao();
		}
		return instance;
	}
	
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, nb_places) VALUES(?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, nb_places FROM Vehicle;";
	private  static final String COUNT_VEHICLES_QUERY = "SELECT COUNT(*) as nbVehicles FROM Vehicle";


	public long create(Vehicle vehicle) throws DaoException {
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement statement = connection.prepareStatement(CREATE_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, vehicle.getConstructor());
			statement.setInt(2, vehicle.getNbPlaces());
			int id = statement.executeUpdate();
			return id;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	public long delete(Vehicle vehicle) throws DaoException {
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement statement = connection.prepareStatement(DELETE_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, vehicle.getId());
			int id = statement.executeUpdate();
			return id;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	public Vehicle findById(long id) throws DaoException {

		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement statement = connection.prepareStatement(FIND_VEHICLE_QUERY);
			statement.setLong(1, id);
			ResultSet rs = statement.executeQuery();
			rs.next();
			String constructor = rs.getString("constructeur");
			int nb_places = rs.getInt("nb_places");
			return new Vehicle((int) id,constructor, nb_places);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}

	}


	public List<Vehicle> findAll() throws DaoException {
		List<Vehicle> vehicles = new ArrayList<Vehicle>();

		try{
			Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(FIND_VEHICLES_QUERY);
			while (rs.next()){
				int id = rs.getInt("id");
				String constructeur = rs.getString("constructeur");
				int nb_places = rs.getInt("nb_places");
				vehicles.add(new Vehicle(id,constructeur,nb_places));
			}
		} catch (SQLException e) {
			throw new DaoException();
		}
		return vehicles;
	}


}
