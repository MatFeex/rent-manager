package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Rent;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.RentService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/rents/update")
public class RentUpdateServlet extends HttpServlet {
    @Autowired
    ClientService clientService;
    @Autowired
    VehicleService vehicleService;
    @Autowired
    RentService rentService;
    private int rent_id;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.rent_id = Integer.parseInt(request.getParameter("id"));
        try{
            request.setAttribute("rent",rentService.findById(rent_id));
            request.setAttribute("vehicles",vehicleService.findAll());
            request.setAttribute("clients",clientService.findAll());
        }catch (ServiceException e){
            e.printStackTrace();
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/update.jsp").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        Vehicle vehicle = new Vehicle();
        Client client = new Client();
        try {
            System.out.println(request.getParameter("vehicle"));
            vehicle = vehicleService.findById(Integer.parseInt(request.getParameter("vehicle")));
            client = clientService.findById(Integer.parseInt(request.getParameter("client")));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        Rent updatedRent = new Rent(rent_id, vehicle,client,LocalDate.parse(request.getParameter("start")),LocalDate.parse(request.getParameter("end")));
        try {
            rentService.update(updatedRent);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        response.sendRedirect("/rentmanager/rents");
    }
}