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

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.persistence.ConnectionManager;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Reservation;

public class ReservationDao {

	private static ReservationDao instance = null;
	private ReservationDao() {}
	public static ReservationDao getInstance() {
		if(instance == null) {
			instance = new ReservationDao();
		}
		return instance;
	}
	
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
	private  static final String COUNT_RESERVATIONS_QUERY = "SELECT COUNT(*) as nbVehicles FROM Reservation";

	public long create(Reservation reservation) throws DaoException {
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
	
	public long delete(Reservation reservation) throws DaoException {
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

	public List<Reservation> findResaByClientId(long clientId) throws DaoException {
		List<Reservation> reservations = new ArrayList<Reservation>();
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement statement = connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);
			statement.setLong(1, clientId);
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				int id = rs.getInt("id");
				int vehicleId = rs.getInt("vehicle");
				LocalDate start = rs.getDate("start").toLocalDate();
				LocalDate end = rs.getDate("end").toLocalDate();
				reservations.add(new Reservation((int) clientId,vehicleId, start,end));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
		return reservations;
	}
	
	public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {
		List<Reservation> reservations = new ArrayList<Reservation>();
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement statement = connection.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY);
			statement.setLong(1, vehicleId);
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				int id = rs.getInt("id");
				int client_id = rs.getInt("client_id");
				LocalDate start = rs.getDate("start").toLocalDate();
				LocalDate end = rs.getDate("end").toLocalDate();
				reservations.add(new Reservation(id,client_id,(int) vehicleId, start,end));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
		return reservations;
	}

	public List<Reservation> findAll() throws DaoException {
		List<Reservation> reservations = new ArrayList<Reservation>();

		try{
			Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(FIND_RESERVATIONS_QUERY);
			while (rs.next()){
				int id = rs.getInt("id");
				int client_id = rs.getInt("client_id");
				int vehicule_id = rs.getInt("vehicule_id");
				LocalDate start = rs.getDate("debut").toLocalDate();
				LocalDate end = rs.getDate("fin").toLocalDate();
				reservations.add(new Reservation((int) id,client_id,vehicule_id,start,end));
			}

		} catch (SQLException e) {
			throw new DaoException();
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
			e.printStackTrace();
			throw new DaoException();
		}
	}

}
