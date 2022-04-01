package com.ensta.librarymanager.service;

import java.time.LocalDate;
import java.util.List;

import com.ensta.librarymanager.dao.EmpruntDao;
import com.ensta.librarymanager.dao.MembreDao;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Emprunt;
import com.ensta.librarymanager.modele.Membre;

public class EmpruntService implements IEmpruntService {

	private static EmpruntService instance;
	
	private EmpruntService() {}
	
	public static EmpruntService getInstance() {
		if(instance == null) {
			instance = new EmpruntService();
		}
		return instance;
	}
	
	@Override
	public List<Emprunt> getList() throws ServiceException {
		try {
			EmpruntDao dao = EmpruntDao.getInstance();
			List<Emprunt> emprunts = dao.getList();
			return emprunts;
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public List<Emprunt> getListCurrent() throws ServiceException {
		try {
			EmpruntDao dao = EmpruntDao.getInstance();
			List<Emprunt> emprunts = dao.getListCurrent();
			return emprunts;
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public List<Emprunt> getListCurrentByMembre(int idMembre) throws ServiceException {
		try {
			EmpruntDao dao = EmpruntDao.getInstance();
			List<Emprunt> emprunts = dao.getListCurrentByMembre(idMembre);
			return emprunts;
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public List<Emprunt> getListCurrentByLivre(int idLivre) throws ServiceException {
		try {
			EmpruntDao dao = EmpruntDao.getInstance();
			List<Emprunt> emprunts = dao.getListCurrentByLivre(idLivre);
			return emprunts;
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public Emprunt getById(int id) throws ServiceException {
		try {
			EmpruntDao dao = EmpruntDao.getInstance();
			Emprunt emprunt = dao.getById(id);
			return emprunt;
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws ServiceException {
		try {
			EmpruntDao dao = EmpruntDao.getInstance();
			dao.create(idMembre, idLivre, dateEmprunt);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public void returnBook(int id) throws ServiceException {
		try {
			EmpruntDao dao = EmpruntDao.getInstance();
			Emprunt emprunt = dao.getById(id);
			emprunt.setDateRetour(LocalDate.now());
			dao.update(emprunt);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public int count() throws ServiceException {
		try {
			EmpruntDao dao = EmpruntDao.getInstance();
			int count = dao.count();
			return count;
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public boolean isLivreDispo(int idLivre) throws ServiceException {
		try {
			EmpruntDao dao = EmpruntDao.getInstance();
			List<Emprunt> emprunts = dao.getListCurrentByLivre(idLivre);
			return emprunts.isEmpty();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public boolean isEmpruntPossible(Emprunt emprunt) throws ServiceException {		
		return isLivreDispo(emprunt.getIdLivre()) && isEmpruntPossible(emprunt.getIdMembre());
	}

	@Override
	public boolean isEmpruntPossible(int idMembre) throws ServiceException {
		int nbemprunt = getListCurrentByMembre(idMembre).size();
		MembreDao membreDao = MembreDao.getInstance();
		
		int quota;
		try {
			Membre membre = membreDao.getById(idMembre);
			quota = membre.getAbonnement().getQuota();
		} catch(DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		
		return nbemprunt < quota;
	}
}
