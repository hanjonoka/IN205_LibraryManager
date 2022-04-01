package com.ensta.librarymanager.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Abonnement;
import com.ensta.librarymanager.modele.Emprunt;
import com.ensta.librarymanager.modele.Membre;
import com.ensta.librarymanager.service.EmpruntService;
import com.ensta.librarymanager.service.LivreService;
import com.ensta.librarymanager.service.MembreService;

public class MembreDetailsServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2570076522407824562L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			MembreService membreService = MembreService.getInstance();
			Membre membre = membreService.getById(id);
			
			request.setAttribute("membre", membre);
			
			EmpruntService empruntService = EmpruntService.getInstance();
			List<Emprunt> emprunts = empruntService.getListCurrentByMembre(id);
			
			LivreService livreService = LivreService.getInstance();
			
			for (Emprunt emprunt : emprunts) {
				emprunt.setLivre(livreService.getById(emprunt.getIdLivre()));
			}
			
			request.setAttribute("emprunts", emprunts);
			
			
			request.getRequestDispatcher("/WEB-INF/View/membre_details.jsp").forward(request, response);
		} catch (IOException | ServiceException e) {
			e.printStackTrace();
			throw new ServletException();
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		int id = Integer.parseInt(request.getParameter("id"));
		String prenom = request.getParameter("prenom");
		String nom = request.getParameter("nom");
		String email = request.getParameter("email");
		String telephone = request.getParameter("telephone");
		String adresse = request.getParameter("adresse");
		Abonnement abonnement = Abonnement.valueOf(request.getParameter("abonnement"));
		
		MembreService membreService = MembreService.getInstance();
		try {
			membreService.update(new Membre(id, nom, prenom, adresse, email, telephone, abonnement));
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new ServletException();
		}
		
		doGet(request,response);
	}
}
