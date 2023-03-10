package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Rent;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;

public class RentDao {

	private static RentDao instance = null;
	private RentDao() {}
	public static RentDao getInstance() {
		if(instance == null) {
			instance = new RentDao();
		}
		return instance;
	}
	
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
	private  static final String COUNT_RESERVATIONS_QUERY = "SELECT COUNT(*) as nbVehicles FROM Reservation";

	public long create(Rent reservation) throws DaoException {
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement statement = connection.prepareStatement(CREATE_RESERVATION_QUERY, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, reservation.getClient_id());
			statement.setInt(2, reservation.getVehicle_id());
			statement.setDate(3, Date.valueOf(reservation.getStart()));
			statement.setDate(4, Date.valueOf(reservation.getEnd()));
			int id = statement.executeUpdate();
			return id;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}
	
	public long delete(Rent reservation) throws DaoException {
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement statement = connection.prepareStatement(DELETE_RESERVATION_QUERY, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, reservation.getId());
			int id = statement.executeUpdate();
			return id;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	public List<Rent> findResaByClientId(long clientId) throws DaoException {
		List<Rent> reservations = new ArrayList<Rent>();
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement statement = connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);
			statement.setLong(1, clientId);
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				int id = rs.getInt("id");
				Client client = ClientService.getInstance().findById((int) clientId);
				Vehicle vehicle = VehicleService.getInstance().findById(rs.getInt("vehicle_id"));
				LocalDate start = rs.getDate("start").toLocalDate();
				LocalDate end = rs.getDate("end").toLocalDate();
				reservations.add(new Rent(id, vehicle, client, start,end));
			}
		} catch (SQLException | ServiceException e) {
			throw new DaoException(e.getMessage());
		}
		return reservations;
	}
	
	public List<Rent> findResaByVehicleId(long vehicleId) throws DaoException {
		List<Rent> reservations = new ArrayList<Rent>();
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement statement = connection.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY);
			statement.setLong(1, vehicleId);
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				int id = rs.getInt("id");
				LocalDate start = rs.getDate("start").toLocalDate();
				LocalDate end = rs.getDate("end").toLocalDate();
				Client client = ClientService.getInstance().findById(rs.getInt("client_id"));
				Vehicle vehicle = VehicleService.getInstance().findById(rs.getInt("vehicle_id"));
				reservations.add(new Rent(id, vehicle, client, start,end));
			}
		} catch (SQLException | ServiceException e) {
			e.printStackTrace();
			throw new DaoException();
		}
		return reservations;
	}

	public List<Rent> findAll() throws DaoException {
		List<Rent> reservations = new ArrayList<Rent>();
		try{
			Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(FIND_RESERVATIONS_QUERY);
			while (rs.next()){
				int id = rs.getInt("id");
				Client client = ClientService.getInstance().findById(rs.getInt("client_id"));
				Vehicle vehicle = VehicleService.getInstance().findById(rs.getInt("vehicle_id"));
				LocalDate start = rs.getDate("debut").toLocalDate();
				LocalDate end = rs.getDate("fin").toLocalDate();
				reservations.add(new Rent(id, vehicle, client,start,end));
			}
		} catch (SQLException | ServiceException e) {
			throw new DaoException(e.getMessage());
		}
		return reservations;
	}

	public int getCount() throws DaoException{
		try{
			Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(COUNT_RESERVATIONS_QUERY);
			rs.next();
			return rs.getInt("nbVehicles");
		}
		catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
	}

}
