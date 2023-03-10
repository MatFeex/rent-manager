package com.epf.rentmanager.ui;


import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Rent;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.RentService;

import java.time.LocalDate;
import java.util.List;

public class Test {

    public static void main(String[] args) throws Exception {
        try {
            List<Rent> rents = RentService.getInstance().findAll();
            for(Rent rent : rents){rent.toString();}
            //ClientService.getInstance().create(new Client("Lucky","Luck","mathieu.fresson@epfedu.fr", LocalDate.of(2001, 7, 17)));
            // RentService.getInstance().create(new Rent(1,1, LocalDate.of(2001, 7, 17),LocalDate.of(2001, 7, 17)));

        }catch (Exception e){
            System.out.println(e);
        }
    }
}