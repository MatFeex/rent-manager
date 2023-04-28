package com.epf.rentmanager.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.RentDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Rent;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
	private static final int MINIMUN_CLIENT_AGE = 18;
	private ClientDao clientDao;
	private RentDao rentDao;

	private ClientService(ClientDao clientDao, RentDao rentDao){
		this.clientDao = clientDao;
		this.rentDao = rentDao;
	}

	private Client validateClientData(Client client, int email_count) throws ServiceException, DaoException {
		String lastName = client.getLast_name();
		String firstName = client.getName();
		int age = Period.between(client.getBirthday(), LocalDate.now()).getYears();;
		String email = client.getEmail();

		if (lastName.isEmpty() || firstName.isEmpty()) {
			throw new ServiceException("Le nom et le prénom du client ne peuvent pas être vides.");
		}
		if (lastName.length() < 3 || firstName.length() < 3) {
			throw new ServiceException("Le nom et le prénom du client doivent contenir au moins 3 caractères.");
		}
		if (age < MINIMUN_CLIENT_AGE) {
			throw new ServiceException(String.format("Le client doit avoir au moins %d ans.",MINIMUN_CLIENT_AGE));
		}
		if (clientDao.emailExists(email, email_count)) {
			throw new ServiceException("Cette adresse e-mail est déjà prise.");
		}
		client.setLast_name(lastName.toUpperCase());
		return client;
	}

	public long create(Client client) throws ServiceException {
		try {
			client = validateClientData(client, 0);
			return this.clientDao.create(client);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public void update(Client client) throws ServiceException {
		try {
			client = validateClientData(client,1);
			this.clientDao.update(client);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public void delete(Client client) throws ServiceException {
		try {
			for(Rent rent : rentDao.findResaByClientId(client.getId())){
				rentDao.delete(rent);
			}
			clientDao.delete(client);
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
