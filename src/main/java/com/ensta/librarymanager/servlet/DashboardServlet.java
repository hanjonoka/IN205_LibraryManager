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

public class DashboardServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3056144780739437035L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		LivreService livreService = LivreService.getInstance();
		MembreService membreService = MembreService.getInstance();
		EmpruntService empruntService = EmpruntService.getInstance();
		
		int nb_livres = 0;
		int nb_membres = 0;
		int nb_emprunts = 0;
		List<Emprunt> emprunts_current = null;
		try {
			nb_livres = livreService.count();
			nb_membres = membreService.count();
			nb_emprunts = empruntService.count();
			emprunts_current = empruntService.getListCurrent();
			
			for(Emprunt e : emprunts_current) {
				e.setMembre(membreService.getById(e.getIdMembre()));
				e.setLivre(livreService.getById(e.getIdLivre()));
			}
			
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("nb_livres", nb_livres);
		request.setAttribute("nb_membres", nb_membres);
		request.setAttribute("nb_emprunts", nb_emprunts);
		request.setAttribute("emprunts_current", emprunts_current);
		
		request.getRequestDispatcher("/WEB-INF/View/dashboard.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		doGet(request, response);
	}
}
