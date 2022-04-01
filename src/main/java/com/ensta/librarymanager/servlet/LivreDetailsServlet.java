package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Emprunt;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.service.EmpruntService;
import com.ensta.librarymanager.service.LivreService;
import com.ensta.librarymanager.service.MembreService;

public class LivreDetailsServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5472505153433628698L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException {
		
		try {
			if(request.getParameter("id") != null) {
				int id = Integer.parseInt(request.getParameter("id"));
				LivreService livreService = LivreService.getInstance();
				Livre livre = livreService.getById(id);
				request.setAttribute("livre", livre);
				
				EmpruntService empruntService = EmpruntService.getInstance();
				if(!empruntService.isLivreDispo(livre.getId())) {
					Emprunt emprunt = empruntService.getListCurrentByLivre(livre.getId()).get(0);
					
					MembreService membreService = MembreService.getInstance();
					emprunt.setMembre(membreService.getById(emprunt.getIdMembre()));
					request.setAttribute("emprunt", emprunt);
					request.setAttribute("debug", "coucou");
				}
			}
			request.getRequestDispatcher("/WEB-INF/View/livre_details.jsp").forward(request, response);
		} catch (IOException | ServiceException e) {
			e.printStackTrace();
			throw new ServletException();
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException {
		try {
			String titre = request.getParameter("titre");
			String auteur = request.getParameter("auteur");
			String isbn = request.getParameter("isbn");
			int id = Integer.parseInt(request.getParameter("id"));
			
			LivreService livreService = LivreService.getInstance();
			Livre livre = new Livre(id,titre, auteur, isbn);
			livreService.update(livre);
			
		} catch(ServiceException e) {
			e.printStackTrace();
			throw new ServletException();
		}
		
		doGet(request, response);
	}
}
