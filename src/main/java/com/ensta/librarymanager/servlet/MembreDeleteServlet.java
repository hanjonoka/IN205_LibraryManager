package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Membre;
import com.ensta.librarymanager.service.MembreService;

public class MembreDeleteServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4251453310288640117L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		int id = Integer.parseInt(request.getParameter("id"));
		MembreService membreService = MembreService.getInstance();
		try {
			Membre membre = membreService.getById(id);
			request.setAttribute("membre", membre);
			request.getRequestDispatcher("WEB-INF/View/membre_delete.jsp").forward(request, response);
		} catch (IOException | ServiceException e) {
			e.printStackTrace();
			throw new ServletException();
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		int id = Integer.parseInt(request.getParameter("id"));
		MembreService membreService = MembreService.getInstance();
		try {
			membreService.delete(id);
			response.sendRedirect("membre_list");
		} catch (ServiceException | IOException e) {
			e.printStackTrace();
			throw new ServletException();
		}
	}
}
