package com.ensta.librarymanager.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Emprunt;
import com.ensta.librarymanager.service.EmpruntService;
import com.ensta.librarymanager.service.LivreService;
import com.ensta.librarymanager.service.MembreService;

public class EmpruntReturnServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3117073470538931331L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException {
		try {
			List<Emprunt> emprunts = null;
			EmpruntService empruntService = EmpruntService.getInstance();
			MembreService membreService = MembreService.getInstance();
			LivreService livreService = LivreService.getInstance();
						
			emprunts = empruntService.getListCurrent();				
			
			for(Emprunt e : emprunts) {
				e.setMembre(membreService.getById(e.getIdMembre()));
				e.setLivre(livreService.getById(e.getIdLivre()));
			}
			request.setAttribute("emprunts_current", emprunts);
			if(request.getParameter("id") != null) {
				int id = Integer.parseInt(request.getParameter("id"));
				request.setAttribute("id", id);
			}
			
			request.getRequestDispatcher("/WEB-INF/View/emprunt_return.jsp").forward(request, response);
			
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new ServletException();
		} catch (IOException e) {
			e.printStackTrace();
			throw new ServletException();
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException {
		
		int id = Integer.parseInt(request.getParameter("id"));
		
		EmpruntService empruntService = EmpruntService.getInstance();
		
		try {
			empruntService.returnBook(id);
		} catch(ServiceException e) {
			e.printStackTrace();
			throw new ServletException();
		}
		doGet(request, response);
	}
}
