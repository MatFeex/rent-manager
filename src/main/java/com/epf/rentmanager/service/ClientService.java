package com.epf.rentmanager.service;

import java.sql.SQLException;
import java.util.List;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

	private ClientDao clientDao;
	private ClientService(ClientDao clientDao){this.clientDao = clientDao;}

	private Client validateClientData(Client client) throws ServiceException {
		if (client.getLast_name().isEmpty() || client.getName().isEmpty()) {
			throw new ServiceException("Le nom et le prénom du client ne peuvent pas être vides.");
		}
		if (client.getLast_name() != null && !client.getLast_name().isEmpty()) {
			client.setLast_name(client.getLast_name().toUpperCase());
		}
		return client;
	}

	public long create(Client client) throws ServiceException {
		try {
			client = validateClientData(client);
			return this.clientDao.create(client);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public void update(Client client) throws ServiceException {
		try {
			client = validateClientData(client);
			this.clientDao.update(client);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public void delete(Client client) throws ServiceException {
		try {
			client = validateClientData(client);
			this.clientDao.delete(client);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public Client findById(int id) throws ServiceException {
		try {
			return this.clientDao.findById(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public List<Client> findAll() throws ServiceException {
		try{
			return this.clientDao.findAll();
		}
		catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public int getCount() throws ServiceException {
		try{
			return this.clientDao.getCount();
		}
		catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

}
