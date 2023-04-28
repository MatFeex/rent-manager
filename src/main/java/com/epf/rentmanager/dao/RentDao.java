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

import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Rent;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Repository;

@Repository
public class RentDao {

	private static final String CLIENT_FIELDS = "Client.id, nom, prenom, email, naissance";
	private static final String VEHICLE_FIELDS = "Vehicle.id, constructeur, model, nb_places";
	private static final String INNER_JOIN_TABLES = "INNER JOIN Client ON Reservation.client_id = Client.id INNER JOIN Vehicle ON Reservation.vehicle_id = Vehicle.id";
	private static final String CREATE_RENT_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RENT_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String UPDATE_RENT_QUERY = "UPDATE Reservation SET client_id=?, vehicle_id=?, debut=?, fin=? WHERE id=?;";
	private static final String FIND_RENTS_BY_CLIENT_QUERY = String.format("SELECT Reservation.id, Reservation.vehicle_id, debut, fin, %s, %s FROM Reservation %s WHERE Reservation.client_id=?;",CLIENT_FIELDS,VEHICLE_FIELDS,INNER_JOIN_TABLES);
	private static final String FIND_RENTS_BY_VEHICLE_QUERY = String.format("SELECT Reservation.id, Reservation.client_id, debut, fin, %s, %s FROM Reservation %s WHERE Reservation.vehicle_id=?;",CLIENT_FIELDS,VEHICLE_FIELDS,INNER_JOIN_TABLES);
	private static final String FIND_RENT_QUERY = String.format("SELECT Reservation.id, Reservation.client_id, vehicle_id, debut, fin, %s, %s FROM Reservation %s WHERE Reservation.id=?;",CLIENT_FIELDS,VEHICLE_FIELDS,INNER_JOIN_TABLES);
	private static final String FIND_RENTS_QUERY = String.format("SELECT Reservation.id, Reservation.client_id, vehicle_id, debut, fin, %s , %s FROM Reservation %s;",CLIENT_FIELDS,VEHICLE_FIELDS,INNER_JOIN_TABLES);
	private static final String COUNT_RENTS_QUERY = "SELECT COUNT(id) AS count FROM Reservation;";
	private static final String FIND_VEHICLES_RENTED_BY_CLIENT_QUERY = String.format("SELECT %s FROM Reservation INNER JOIN Vehicle ON Reservation.vehicle_id = Vehicle.id WHERE Reservation.client_id=?;", VEHICLE_FIELDS);

	public long create(Rent rent) throws DaoException {
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement statement = connection.prepareStatement(CREATE_RENT_QUERY, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, rent.getClient().getId());
			statement.setInt(2, rent.getVehicle().getId());
			statement.setDate(3, Date.valueOf(rent.getStart()));
			statement.setDate(4, Date.valueOf(rent.getEnd()));
			int id = statement.executeUpdate();
			return id;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Erreur rencontrée lors de la création de la réservation");
		}
	}

	public void update(Rent rent) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement statement = connection.prepareStatement(UPDATE_RENT_QUERY);
			statement.setInt(1, rent.getClient().getId());
			statement.setInt(2, rent.getVehicle().getId());
			statement.setDate(3, Date.valueOf(rent.getStart()));
			statement.setDate(4, Date.valueOf(rent.getEnd()));
			statement.setInt(5,rent.getId());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			// throw new DaoException("Erreur rencontrée lors de la mise à jour de la réservation");
		}
	}
	
	public long delete(Rent rent) throws DaoException {
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement statement = connection.prepareStatement(DELETE_RENT_QUERY, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, rent.getId());
			int id = statement.executeUpdate();
			return id;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	public List<Rent> findResaByClientId(long clientId) throws DaoException {
		List<Rent> rents = new ArrayList<Rent>();
		try{

			Connection connection = ConnectionManager.getConnection();
			PreparedStatement statement = connection.prepareStatement(FIND_RENTS_BY_CLIENT_QUERY);
			statement.setLong(1, clientId);
			ResultSet rs = statement.executeQuery();
			// get services
			Object[] services = getServices();
			ClientService clientService = (ClientService) services[0];
			VehicleService vehicleService = (VehicleService) services[1];
			while(rs.next()){
				int id = rs.getInt("id");
				Client client = clientService.findById((int) clientId);
				Vehicle vehicle = vehicleService.findById(rs.getInt("vehicle_id"));
				LocalDate start = rs.getDate("debut").toLocalDate();
				LocalDate end = rs.getDate("fin").toLocalDate();
				rents.add(new Rent(id, vehicle, client, start,end));
			}
		} catch (SQLException | ServiceException e) {
			throw new DaoException(e.getMessage());
		}
		return rents;
	}
	
	public List<Rent> findResaByVehicleId(long vehicleId) throws DaoException {
		List<Rent> rents = new ArrayList<Rent>();
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement statement = connection.prepareStatement(FIND_RENTS_BY_VEHICLE_QUERY);
			statement.setLong(1, vehicleId);
			ResultSet rs = statement.executeQuery();
			// get services
			Object[] services = getServices();
			ClientService clientService = (ClientService) services[0];
			VehicleService vehicleService = (VehicleService) services[1];
			while(rs.next()){
				int id = rs.getInt("id");
				LocalDate start = rs.getDate("debut").toLocalDate();
				LocalDate end = rs.getDate("fin").toLocalDate();
				Client client = clientService.findById(rs.getInt("client_id"));
				Vehicle vehicle = vehicleService.findById((int) vehicleId);
				rents.add(new Rent(id, vehicle, client, start,end));
			}
		} catch (SQLException | ServiceException e) {
			e.printStackTrace();
			throw new DaoException();
		}
		return rents;
	}

	public Rent findById(int id) throws DaoException {
		Rent rent = null;
		try (
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_RENT_QUERY)
		) {
			preparedStatement.setLong(1, id);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Client client = new Client(rs.getInt("client_id"), rs.getString("nom"),rs.getString("prenom"), rs.getString("email"),rs.getDate("naissance").toLocalDate());
				Vehicle vehicle = new Vehicle(rs.getInt("vehicle_id"),rs.getString("constructeur"), rs.getString("model"),rs.getShort("nb_places"));
				rent = new Rent(id, vehicle, client, rs.getDate("debut").toLocalDate(),rs.getDate("fin").toLocalDate());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
		return rent;
	}

	public List<Vehicle> findVehiclesRentedByClient(int client_id) throws DaoException {
		List<Vehicle> clientVehicles = new ArrayList<>();
		try (
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_VEHICLES_RENTED_BY_CLIENT_QUERY)
		) {
			preparedStatement.setLong(1, client_id);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				clientVehicles.add(new Vehicle(rs.getInt("Vehicle.id"), rs.getString("constructeur"),rs.getString("model"), rs.getShort("nb_places")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
		return clientVehicles;
	}

	public List<Rent> findAll() throws DaoException {
		List<Rent> rents = new ArrayList<Rent>();
		try{
			Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(FIND_RENTS_QUERY);
			// get services
			Object[] services = getServices();
			ClientService clientService = (ClientService) services[0];
			VehicleService vehicleService = (VehicleService) services[1];

			while (rs.next()){
				int id = rs.getInt("id");
				Client client = clientService.findById(rs.getInt("client_id"));
				Vehicle vehicle = vehicleService.findById(rs.getInt("vehicle_id"));
				LocalDate start = rs.getDate("debut").toLocalDate();
				LocalDate end = rs.getDate("fin").toLocalDate();
				rents.add(new Rent(id, vehicle, client,start,end));
			}
		} catch (SQLException | ServiceException e) {
			throw new DaoException(e.getMessage());
		}
		return rents;
	}

	public int getCount() throws DaoException{
		try{
			Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(COUNT_RENTS_QUERY);
			rs.next();
			return rs.getInt("count");
		}
		catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
	}

	private Object[] getServices() {
		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
		return new Object[] {
				context.getBean(ClientService.class),
				context.getBean(VehicleService.class),
		};
	}
}
