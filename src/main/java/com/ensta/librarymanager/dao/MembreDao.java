package com.ensta.librarymanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ensta.librarymanager.datamapper.DataMapper;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.exception.DataMapperException;
import com.ensta.librarymanager.modele.Abonnement;
import com.ensta.librarymanager.modele.Membre;
import com.ensta.librarymanager.persistence.ConnectionManager;

public class MembreDao implements IMembreDao {
	private static MembreDao instance;
	
	private MembreDao() {}
	
	public static MembreDao getInstance() {
		if(instance == null) {
			instance = new MembreDao();
		}
		return instance;
	}

	@Override
	public List<Membre> getList() throws DaoException {
		try (Connection conn = ConnectionManager.getConnection()) {
			PreparedStatement psmt = conn.prepareStatement("Select * FROM membre");
			
			psmt.executeQuery();
			
			ResultSet res = psmt.getResultSet();
			
			List<Membre> membres = DataMapper.membreMapper(res);
			
			return membres;
		} catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException();
        } catch (DataMapperException e) {
        	e.printStackTrace();
        	throw new DaoException();
        }
	}

	@Override
	public Membre getById(int id) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection()) {
			PreparedStatement psmt = conn.prepareStatement("Select * FROM membre WHERE id=?");
			psmt.setInt(1, id);
			
			psmt.executeQuery();
			
			ResultSet res = psmt.getResultSet();
			
			List<Membre> membres = DataMapper.membreMapper(res);
			
			return membres.get(0);
		} catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException();
        } catch (DataMapperException e) {
        	e.printStackTrace();
        	throw new DaoException();
        }
	}

	@Override
	public void create(String nom, String prenom, String adresse, String email, String telephone) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection()) {
			PreparedStatement psmt = conn.prepareStatement("INSERT INTO membre(nom,prenom,adresse,email,telephone,abonnement) VALUES(?,?,?,?,?,?)");
			psmt.setString(1, nom);
			psmt.setString(2, prenom);
			psmt.setString(3, adresse);
			psmt.setString(4, email);
			psmt.setString(5, telephone);
			psmt.setString(6, Abonnement.BASIC.toString());
			
			psmt.executeUpdate();
			
		} catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException();
        }
	}

	@Override
	public void update(Membre membre) throws DaoException {
		try(Connection conn = ConnectionManager.getConnection()){
			PreparedStatement psmt = conn.prepareStatement("UPDATE membre SET nom=?, prenom=?, adresse=?, email=?, telephone=?, abonnement=? WHERE id=?");

			psmt.setString(1, membre.getNom());
			psmt.setString(2, membre.getPrenom());
			psmt.setString(3, membre.getAdresse());
			psmt.setString(4, membre.getEmail());
			psmt.setString(5, membre.getTelephone());
			psmt.setString(6, membre.getAbonnement().toString());
			psmt.setInt(7, membre.getId());
			
			psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	@Override
	public void delete(int id) throws DaoException {
		try(Connection conn = ConnectionManager.getConnection()){
			PreparedStatement psmt = conn.prepareStatement("DELETE FROM membre WHERE id=?");

			psmt.setInt(1, id);
			
			psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	@Override
	public int count() throws DaoException {
		try (Connection conn = ConnectionManager.getConnection()){
			PreparedStatement psmt = conn.prepareStatement("SELECT Count(*) AS count FROM membre");
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
