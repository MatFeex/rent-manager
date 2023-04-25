package com.epf.rentmanager.ui;


import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Rent;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.RentService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Test {
    public static void main(String[] args) throws Exception {
        try {
            ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
            ClientService clientService = context.getBean(ClientService.class);
            VehicleService vehicleService = context.getBean(VehicleService.class);
        }catch (Exception e){
            System.out.println(e);
        }
    }
}