package com.ensta.librarymanager.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.service.LivreService;

public class LivreListServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6272416602967781080L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		try {
			LivreService livreService = LivreService.getInstance();
			
			List<Livre> livres = livreService.getList();
			
			request.setAttribute("livres", livres);

			request.getRequestDispatcher("/WEB-INF/View/livre_list.jsp").forward(request, response);
			
		} catch (IOException e) {
			e.printStackTrace();
			throw new ServletException();
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new ServletException();
		}
	}
}
