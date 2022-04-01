package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.service.LivreService;

public class LivreAddServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6876747677936836060L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException {
		try {
			request.getRequestDispatcher("WEB-INF/View/livre_add.jsp").forward(request, response);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ServletException();
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		
		String titre = request.getParameter("titre");
		String auteur = request.getParameter("auteur");
		String isbn = request.getParameter("isbn");
		
		LivreService livreService = LivreService.getInstance();
		try {
			livreService.create(titre, auteur, isbn);
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new ServletException();
		}
		
		doGet(request, response);
	}
}
