package com.ensta.librarymanager.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.modele.Membre;
import com.ensta.librarymanager.service.EmpruntService;
import com.ensta.librarymanager.service.LivreService;
import com.ensta.librarymanager.service.MembreService;

public class EmpruntAddServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -325611416139255243L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		LivreService livreService = LivreService.getInstance();
		MembreService membreSerice = MembreService.getInstance();
		
		List<Livre> livresDispo = null;
		List<Membre> membresPossibles = null;
		
		try {
			livresDispo = livreService.getListDispo();
			membresPossibles = membreSerice.getListMembreEmpruntPossible();
			
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("livresDispo", livresDispo);
		request.setAttribute("membresPossibles", membresPossibles);
		
		request.getRequestDispatcher("WEB-INF/View/emprunt_add.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		int idMembre = Integer.parseInt(request.getParameter("idMembre"));
		int idLivre = Integer.parseInt(request.getParameter("idLivre"));
		
		EmpruntService empruntService = EmpruntService.getInstance();
		
		try {
			
			if(empruntService.isEmpruntPossible(idMembre) && empruntService.isLivreDispo(idLivre)) {
				empruntService.create(idMembre, idLivre, LocalDate.now());
			}
			
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new ServletException();
		}
		
		doGet(request, response);
		
	}
}
