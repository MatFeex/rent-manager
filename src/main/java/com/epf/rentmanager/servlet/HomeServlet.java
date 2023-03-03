package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ClientService clientService = ClientService.getInstance();
	private VehicleService vehiculeService = VehicleService.getInstance();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)





			throws ServletException, IOException {
				try {
					request.setAttribute("nbClients", ClientService.getInstance().getCount());
					//request.setAttribute("nbVehicules", VehicleService.getInstance().findAll().size());
					// request.setAttribute("nbReservations", ReservationService.getInstance().findAll().size());
				} catch (ServiceException e) {
					throw new ServletException();
				}
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
	}

}
