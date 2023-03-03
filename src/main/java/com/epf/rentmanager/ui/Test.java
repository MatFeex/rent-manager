package com.epf.rentmanager.ui;


import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Test {

    public static void main(String[] args) throws Exception {
        try {
            List<Client> clients = ClientService.getInstance().findAll(); // get all
            Client client = ClientService.getInstance().findById(1); // get specific row with id

            ClientService.getInstance().create(new Client("Mat","Fre","mathieu.fresson@epfedu.fr", LocalDate.of(2001, 7, 17)));

        }catch (Exception e){
            System.out.println(e);
        }
    }
}