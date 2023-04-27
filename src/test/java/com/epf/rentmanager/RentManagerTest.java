package com.epf.rentmanager;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.dao.RentDao;
import com.epf.rentmanager.model.Rent;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.service.RentService;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class RentManagerTest {
    @Mock
    private ClientDao clientDao;

    @Mock
    private VehicleDao vehicleDao;

    @Mock
    private RentDao reservationDao;

    @InjectMocks
    private ClientService clientService;

    @InjectMocks
    private VehicleService vehicleService;

    @InjectMocks
    private RentService rentService;

    @Before
    public void setUp() {
        // Initial setup or configurations for tests
    }

    @Test
    public void testAddClient() throws ServiceException, DaoException {
        Client client = new Client(0, "Doe", "John", "john.doe@email.com", LocalDate.now());
        Mockito.when(clientDao.create(client)).thenReturn(1L);
        clientService.create(client);

        // Exemple d'assertion pour vérifier si un client a été créé avec succès
        assertEquals(1L, client.getId());
    }

    @Test
    public void testCreateVehicle() throws ServiceException, DaoException {
        Vehicle vehicle = new Vehicle(0,"Tesla","Model S",4);
        Mockito.when(vehicleDao.create(vehicle)).thenReturn(1L);
        vehicleService.update(vehicle);
        // Exemple d'assertion pour vérifier si un véhicule a été crée avec succès
        assertEquals(1L, vehicle.getId());
    }

    @Test
    public void testDeleteReservation() throws ServiceException, DaoException {
        Rent rent = rentService.findById(1);
        Mockito.when(reservationDao.delete(rent)).thenReturn(1L);
        rentService.delete(rent);
        // Exemple de vérification pour vérifier si la méthode delete de RentDao a été appelée avec la bonne réservation
        Mockito.verify(reservationDao).delete(rent);
    }
}
