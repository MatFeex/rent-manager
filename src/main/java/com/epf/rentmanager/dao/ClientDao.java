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


import com.epf.rentmanager.persistence.ConnectionManager;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import org.springframework.stereotype.Repository;

@Repository
public class ClientDao {
	private static final String UPDATE_CLIENT_QUERY = "UPDATE Client SET nom=?, prenom=?, email=?, naissance=? WHERE id=?";
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	private  static final String COUNT_CLIENTS_QUERY = "SELECT COUNT(*) as count FROM Client";

	public long create(Client client) throws DaoException {
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement statement = connection.prepareStatement(CREATE_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, client.getLast_name());
			statement.setString(2, client.getName());
			statement.setString(3, client.getEmail());
			statement.setDate(4, Date.valueOf(client.getBirthday()));
			int id = statement.executeUpdate();
			return id;
		} catch (SQLException e) {
			throw new DaoException("Erreur rencontrée lors de création du client");
		}
	}

	public void update(Client client) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement statement = connection.prepareStatement(UPDATE_CLIENT_QUERY);
			statement.setString(1, client.getLast_name());
			statement.setString(2, client.getName());
			statement.setString(3, client.getEmail());
			statement.setDate(4, Date.valueOf(client.getBirthday()));
			statement.setLong(5, client.getId());
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Erreur rencontrée lors de la mise à jour du client");
		}
	}
	
	public long delete(Client client) throws DaoException {
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement statement = connection.prepareStatement(DELETE_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, client.getId());
			int id = statement.executeUpdate();
			return id;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Erreur rencontrée lors de la suppression du client");
		}
	}

	public Client findById(long id) throws DaoException {

		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement statement = connection.prepareStatement(FIND_CLIENT_QUERY);
			statement.setLong(1, id);
			ResultSet rs = statement.executeQuery();
			rs.next();
			String lastName = rs.getString("nom");
			String name = rs.getString("prenom");
			String email = rs.getString("email");
			LocalDate birthday = rs.getDate("naissance").toLocalDate();
			return new Client((int) id,lastName,name,email,birthday);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Erreur rencontrée lors de la recherche par id du client");
		}
	}

	public List<Client> findAll() throws DaoException {

		List<Client> clients = new ArrayList<Client>();
		try{
			Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(FIND_CLIENTS_QUERY);
			while (rs.next()){
				int id = rs.getInt("id");
				String lastName = rs.getString("nom");
				String name = rs.getString("prenom");
				String email = rs.getString("email");
				LocalDate birthday = rs.getDate("naissance").toLocalDate();
				clients.add(new Client(id,lastName,name,email,birthday));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Erreur rencontrée lors de la récupération de tous les clients");
		}
		return clients;
	}

	public int getCount() throws DaoException{
		try{
			Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(COUNT_CLIENTS_QUERY);
			rs.next();
			return rs.getInt("count");
			}
		catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Erreur rencontrée lors du comptage des clients");
		}
	}


}
