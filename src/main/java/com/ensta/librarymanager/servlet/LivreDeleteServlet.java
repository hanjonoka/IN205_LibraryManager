package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.service.LivreService;

public class LivreDeleteServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5430512734674304270L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			LivreService livreService = LivreService.getInstance();
			Livre livre = livreService.getById(id);
			request.setAttribute("livre", livre);
			request.getRequestDispatcher("WEB-INF/View/livre_delete.jsp").forward(request, response);
		} catch (IOException | ServiceException e) {
			e.printStackTrace();
			throw new ServletException();
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		int id = Integer.parseInt(request.getParameter("id"));
		LivreService livreService = LivreService.getInstance();
		try {
			livreService.delete(id);
			response.sendRedirect("livre_list");
		} catch (ServiceException | IOException e) {
			e.printStackTrace();
			throw new ServletException();
		}
	}
}
