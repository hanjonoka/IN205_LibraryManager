package com.ensta.librarymanager.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Membre;
import com.ensta.librarymanager.service.MembreService;

public class MembreListServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6705423970995160112L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		try {
			MembreService membreService = MembreService.getInstance();
			List<Membre> membres = membreService.getList();
			request.setAttribute("membres", membres);
			request.getRequestDispatcher("WEB-INF/View/membre_list.jsp").forward(request, response);
		} catch (IOException | ServiceException e) {
			e.printStackTrace();
			throw new ServletException();
		}
	}
}
