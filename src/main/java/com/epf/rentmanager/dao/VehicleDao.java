package com.epf.rentmanager.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.persistence.ConnectionManager;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Vehicle;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleDao {

	private static final String UPDATE_VEHICLE_QUERY = "UPDATE Vehicle SET constructeur = ?, model = ?, nb_places = ? WHERE id = ?";
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, model ,nb_places) VALUES(?, ?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, model, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, model, nb_places FROM Vehicle;";
	private  static final String COUNT_VEHICLES_QUERY = "SELECT COUNT(*) as count FROM Vehicle";

	public long create(Vehicle vehicle) throws DaoException {
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement statement = connection.prepareStatement(CREATE_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, vehicle.getConstructor());
			statement.setString(2, vehicle.getModel());
			statement.setInt(3, vehicle.getSeats());
			int id = statement.executeUpdate();
			return id;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	public void update(Vehicle vehicle) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement statement = connection.prepareStatement(UPDATE_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, vehicle.getConstructor());
			statement.setString(2, vehicle.getModel());
			statement.setInt(3, vehicle.getSeats());
			statement.setLong(4, vehicle.getId());
			int rowsUpdated = statement.executeUpdate();
			if (rowsUpdated == 0) {
				throw new DaoException("Vehicle not found");
			}
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
			String model = rs.getString("model");
			int nb_places = rs.getInt("nb_places");
			return new Vehicle((int) id,constructor,model, nb_places);

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
				String model = rs.getString("model");
				int nb_places = rs.getInt("nb_places");
				vehicles.add(new Vehicle(id,constructeur,model,nb_places));
			}
		} catch (SQLException e) {
			throw new DaoException();
		}
		return vehicles;
	}


	public int getCount() throws DaoException{
		try{
			Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(COUNT_VEHICLES_QUERY);
			rs.next();
			return rs.getInt("count");
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}
}
