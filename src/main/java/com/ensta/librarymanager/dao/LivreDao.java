package com.ensta.librarymanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ensta.librarymanager.datamapper.DataMapper;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.exception.DataMapperException;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.persistence.ConnectionManager;

public class LivreDao implements ILivreDao {

	private static LivreDao instance;
	
	private LivreDao() {}
	
	public static LivreDao getInstance() {
		if(instance==null) {
			instance = new LivreDao();
		}
		return instance;
	}
	
	@Override
	public List<Livre> getList() throws DaoException {
		try (Connection conn = ConnectionManager.getConnection()) {
			PreparedStatement psmt = conn.prepareStatement("Select * FROM LIVRE");
			
			psmt.executeQuery();
			
			ResultSet res = psmt.getResultSet();
			
			List<Livre> livres = DataMapper.livreMapper(res);
			
			return livres;
		} catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException();
        } catch (DataMapperException e) {
        	e.printStackTrace();
        	throw new DaoException();
        }
	}

	@Override
	public Livre getById(int id) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection()) {
			PreparedStatement psmt = conn.prepareStatement("Select * FROM LIVRE WHERE id=?");
			psmt.setInt(1, id);
			
			psmt.executeQuery();
			
			ResultSet res = psmt.getResultSet();
			
			List<Livre> livres = DataMapper.livreMapper(res);
			
			return livres.get(0);
		} catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException();
        } catch (DataMapperException e) {
        	e.printStackTrace();
        	throw new DaoException();
        }
	}

	@Override
	public void create(String titre, String auteur, String isbn) throws DaoException {
		try(Connection conn = ConnectionManager.getConnection()) {
			PreparedStatement psmt = conn.prepareStatement("INSERT INTO livre(titre,auteur,isbn) VALUES(?,?,?)");
			psmt.setString(1, titre);
			psmt.setString(2, auteur);
			psmt.setString(3, isbn);
			
			psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	@Override
	public void update(Livre livre) throws DaoException {
		try(Connection conn = ConnectionManager.getConnection()) {
			PreparedStatement psmt = conn.prepareStatement("UPDATE livre SET titre=?, auteur=?, isbn=? WHERE id=?");
			psmt.setString(1, livre.getTitre());
			psmt.setString(2, livre.getAuteur());
			psmt.setString(3, livre.getIsbn());
			psmt.setInt(4, livre.getId());
			
			psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	@Override
	public void delete(int id) throws DaoException {
		try(Connection conn = ConnectionManager.getConnection()){
			PreparedStatement psmt = conn.prepareStatement("DELETE FROM livre WHERE id=?");

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
			PreparedStatement psmt = conn.prepareStatement("SELECT Count(*) AS count FROM livre");
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
