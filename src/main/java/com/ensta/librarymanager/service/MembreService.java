package com.ensta.librarymanager.service;

import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.dao.MembreDao;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Membre;

public class MembreService implements IMembreService {
	
	private static MembreService instance;
	
	private MembreService() {}
	
	public static MembreService getInstance() {
		if(instance == null) {
			instance = new MembreService();
		}
		return instance;
	}

	@Override
	public List<Membre> getList() throws ServiceException {
		try {
			MembreDao dao = MembreDao.getInstance();
			return dao.getList();
		} catch(DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public List<Membre> getListMembreEmpruntPossible() throws ServiceException {
		try {
			MembreDao dao = MembreDao.getInstance();
			List<Membre> possible = new ArrayList<>();
			List<Membre> membres = dao.getList();
			
			EmpruntService empruntService = EmpruntService.getInstance();
			
			for(Membre membre : membres) {
				if(empruntService.isEmpruntPossible(membre.getId())) {
					possible.add(membre);
				}
			}
			return possible;
		} catch(DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public Membre getById(int id) throws ServiceException {
		try {
			MembreDao dao = MembreDao.getInstance();
			return dao.getById(id);
		} catch(DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public void create(String nom, String prenom, String adresse, String email, String telephone)
			throws ServiceException {
		if(nom.isBlank() || prenom.isBlank()) throw new ServiceException();
		nom = nom.toUpperCase();
		try {
			MembreDao dao = MembreDao.getInstance();
			dao.create(nom, prenom, adresse, email, telephone);
		} catch(DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public void update(Membre membre) throws ServiceException {
		if(membre.getNom().isBlank() || membre.getPrenom().isBlank()) throw new ServiceException();
		membre.setNom(membre.getNom().toUpperCase());
		try {
			MembreDao dao = MembreDao.getInstance();
			dao.update(membre);
		} catch(DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public void delete(int id) throws ServiceException {
		try {
			MembreDao dao = MembreDao.getInstance();
			dao.delete(id);
		} catch(DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public int count() throws ServiceException {
		try {
			MembreDao dao = MembreDao.getInstance();
			return dao.count();
		} catch(DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

}
