package com.ensta.librarymanager.service;

import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.dao.LivreDao;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Livre;

public class LivreService implements ILivreService {

	private static LivreService instance;
	
	private LivreService() {}
	
	public static LivreService getInstance() {
		if(instance==null) {
			instance = new LivreService();
		}
		return instance;
	}
	
	@Override
	public List<Livre> getList() throws ServiceException {
		try {
			LivreDao dao = LivreDao.getInstance();
			return dao.getList();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public List<Livre> getListDispo() throws ServiceException {
		try {
			LivreDao dao = LivreDao.getInstance();
			List<Livre> livres = dao.getList();
			List<Livre> dispo = new ArrayList<>();
			
			EmpruntService empruntService = EmpruntService.getInstance();
			
			for (int i=0; i<livres.size(); i++) {
				Livre livre = livres.get(livres.size()-i-1);
				if(empruntService.isLivreDispo(livre.getId())) {
					dispo.add(livre);
				}
			}
			
			return dispo;
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public Livre getById(int id) throws ServiceException {
		try {
			LivreDao dao = LivreDao.getInstance();
			return dao.getById(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public void create(String titre, String auteur, String isbn) throws ServiceException {
		if(titre.isBlank()) throw new ServiceException();
		try {
			LivreDao dao = LivreDao.getInstance();
			dao.create(titre, auteur, isbn);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public void update(Livre livre) throws ServiceException {
		if(livre.getTitre().isBlank()) throw new ServiceException();
		try {
			LivreDao dao = LivreDao.getInstance();
			dao.update(livre);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public void delete(int id) throws ServiceException {
		try {
			LivreDao dao = LivreDao.getInstance();
			dao.delete(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public int count() throws ServiceException {
		try {
			LivreDao dao = LivreDao.getInstance();
			return dao.count();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

}
