package com.ensta.librarymanager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.ensta.librarymanager.datamapper.DataMapper;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.exception.DataMapperException;
import com.ensta.librarymanager.modele.Emprunt;
import com.ensta.librarymanager.persistence.ConnectionManager;

public class EmpruntDao implements IEmpruntDao {

	private static EmpruntDao instance;
	
	private EmpruntDao() {}
	
	public static EmpruntDao getInstance() {
		if( (instance == null)) {
			instance = new EmpruntDao();
		}
		return instance;
	}
	
	@Override
	public List<Emprunt> getList() throws DaoException {
		try (Connection conn = ConnectionManager.getConnection()) {
			PreparedStatement psmt = conn.prepareStatement("Select * FROM EMPRUNT");
			
			psmt.executeQuery();
			
			ResultSet res = psmt.getResultSet();
			
			List<Emprunt> emprunts = DataMapper.empruntMapper(res);
			
			return emprunts;
		} catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException();
        } catch (DataMapperException e) {
        	e.printStackTrace();
        	throw new DaoException();
        }
	}

	@Override
	public List<Emprunt> getListCurrent() throws DaoException {
		try (Connection conn = ConnectionManager.getConnection()) {
			
			PreparedStatement psmt = conn.prepareStatement("Select * FROM EMPRUNT WHERE dateRetour IS NULL");
			
			psmt.executeQuery();
			
			ResultSet res = psmt.getResultSet();
			
			List<Emprunt> emprunts = DataMapper.empruntMapper(res);
			
			conn.close();
			return emprunts;
		} catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException();
        } catch (DataMapperException e) {
        	e.printStackTrace();
        	throw new DaoException();
        }
	}

	@Override
	public List<Emprunt> getListCurrentByMembre(int idMembre) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection()) {
			
			PreparedStatement psmt = conn.prepareStatement("Select * FROM EMPRUNT WHERE idMembre=? AND dateRetour IS NULL");
			psmt.setInt(1,idMembre);
			
			psmt.executeQuery();
			
			ResultSet res = psmt.getResultSet();
			
			List<Emprunt> emprunts = DataMapper.empruntMapper(res);
			return emprunts;
		} catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException();
        } catch (DataMapperException e) {
        	e.printStackTrace();
        	throw new DaoException();
        }
	}

	@Override
	public List<Emprunt> getListCurrentByLivre(int idLivre) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection()) {
			
			PreparedStatement psmt = conn.prepareStatement("Select * FROM EMPRUNT WHERE idLivre=? AND dateRetour IS NULL");
			psmt.setInt(1,idLivre);
			
			psmt.executeQuery();
			
			ResultSet res = psmt.getResultSet();
			
			List<Emprunt> emprunts = DataMapper.empruntMapper(res);
			return emprunts;
		} catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException();
        } catch (DataMapperException e) {
        	e.printStackTrace();
        	throw new DaoException();
        }
	}

	@Override
	public Emprunt getById(int id) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection()){
			
			
			PreparedStatement psmt = conn.prepareStatement("Select * FROM EMPRUNT WHERE id=?");
			psmt.setInt(1,id);
			
			psmt.executeQuery();
			
			ResultSet res = psmt.getResultSet();
			
			Emprunt emprunt = DataMapper.empruntMapper(res).get(0);
			return emprunt;
		} catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException();
        } catch (DataMapperException e) {
        	e.printStackTrace();
        	throw new DaoException();
        }
	}

	@Override
	public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection()){
			PreparedStatement psmt = conn.prepareStatement("INSERT INTO emprunt(idMembre, idLivre, dateEmprunt, dateRetour) VALUES (?,?,?,NULL)");
			psmt.setInt(1, idMembre);
			psmt.setInt(2, idLivre);
			psmt.setDate(3, Date.valueOf(dateEmprunt));
			
			psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	@Override
	public void update(Emprunt emprunt) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection()){
			PreparedStatement psmt = conn.prepareStatement("UPDATE emprunt SET idMembre = ?, idLivre = ?, dateEmprunt = ?, dateRetour = ? WHERE id = ?");
			psmt.setInt(1, emprunt.getIdMembre());
			psmt.setInt(2, emprunt.getIdLivre());
			psmt.setDate(3, Date.valueOf(emprunt.getDateEmprunt()));
			psmt.setDate(4, Date.valueOf(emprunt.getDateRetour()));
			psmt.setInt(5, emprunt.getId());
			
			psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}

	}

	@Override
	public int count() throws DaoException {
		try (Connection conn = ConnectionManager.getConnection()){
			PreparedStatement psmt = conn.prepareStatement("SELECT Count(*) AS count FROM emprunt");
			psmt.executeQuery();
			ResultSet res = psmt.getResultSet();
			res.next();
			return res.getInt("count");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}
}
