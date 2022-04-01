package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.service.MembreService;

public class MembreAddServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6323410865962712778L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		try {
			request.getRequestDispatcher("WEB-INF/View/membre_add.jsp").forward(request, response);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ServletException();
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String adresse = request.getParameter("adresse");
		String mail = request.getParameter("email");
		String telephone = request.getParameter("telephone");
		
		MembreService membreService = MembreService.getInstance();
		
		try {
			membreService.create(nom, prenom, adresse, mail, telephone);
			doGet(request, response);
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new ServletException();
		}
	}
}
