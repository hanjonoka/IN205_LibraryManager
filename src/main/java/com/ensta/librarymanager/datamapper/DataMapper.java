package com.ensta.librarymanager.datamapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.exception.DataMapperException;
import com.ensta.librarymanager.modele.Abonnement;
import com.ensta.librarymanager.modele.Emprunt;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.modele.Membre;

public class DataMapper {
	public static List<Emprunt> empruntMapper(ResultSet res) throws DataMapperException {
		try {
			List<Emprunt> emprunts = new ArrayList<Emprunt>();
			while(res.next()) {
				emprunts.add(new Emprunt(
						res.getInt("id"),
						res.getInt("idMembre"), 
						res.getInt("idLivre"), 
						res.getDate("dateEmprunt").toLocalDate(), 
						res.getDate("dateRetour") != null ? res.getDate("dateRetour").toLocalDate() : null));
			}
			return emprunts;
		}catch (SQLException e){
			e.printStackTrace();
			throw new DataMapperException("Error in Membre Mapper");
		}
	}
	
	public static List<Membre> membreMapper(ResultSet res) throws DataMapperException {
		try {
			List<Membre> membres = new ArrayList<Membre>();
			while(res.next()) {
				membres.add(new Membre(
						res.getInt("id"),
						res.getString("nom"),
						res.getString("prenom"),
						res.getString("adresse"),
						res.getString("email"),
						res.getString("telephone"),
						Abonnement.valueOf(res.getString("abonnement")))
						);
			}
			return membres;
		}catch (SQLException e){
			e.printStackTrace();
			throw new DataMapperException("Error in Membre Mapper");
		}
	}

	public static List<Livre> livreMapper(ResultSet res) throws DataMapperException {
		try {
			List<Livre> livres = new ArrayList<Livre>();
			while(res.next()) {
				livres.add(new Livre(
						res.getInt("id"),
						res.getString("titre"),
						res.getString("auteur"),
						res.getString("isbn")
						));
			}
			return livres;
		}catch (SQLException e){
			e.printStackTrace();
			throw new DataMapperException("Error in Membre Mapper");
		}
	}

}
