package it.polito.tdp.meteo.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import it.polito.tdp.meteo.model.Citta;
import it.polito.tdp.meteo.model.Rilevamento;

public class MeteoDAO {
	
	public List<Rilevamento> getAllRilevamenti() {

		final String sql = "SELECT Localita, Data, Umidita FROM situazione ORDER BY data ASC";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<Rilevamento> getAllRilevamentiLocalitaMese(int mese, String localita) {

		String sql="SELECT Localita, Data, Umidita "+
				   " FROM situazione "+
				   " WHERE MONTH(data)=? AND Localita=? ";
		
		List<Rilevamento> rilevamenti = new LinkedList<Rilevamento>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setString(1, Integer.toString(mese));
			st.setString(2, localita);
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
public Map<String,Double> getRilevamentiPerLocalitaMese(int mese) {
		
		String sql="SELECT Localita, AVG(Umidita) AS media "+
				   " FROM situazione "+
				   " WHERE MONTH(data)=? "+
				   "GROUP BY Localita ";
		
		Map<String,Double> rilevamenti = new TreeMap<String,Double>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setString(1, Double.toString(mese));
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				rilevamenti.put(rs.getString("Localita"),Double.parseDouble(rs.getString("media")));
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	
	public List<Integer> getMesiDatabase(){
		
		final String sql = "SELECT DISTINCT MONTH(data) FROM situazione ORDER BY data ASC";

		List<Integer> mesiDatabase = new ArrayList<Integer>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
			
				Date d=rs.getDate("data");
				int numeroMese=Integer.parseInt(d.toString());
				mesiDatabase.add(numeroMese);
			}

			conn.close();
			return mesiDatabase;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public List<Citta> getAllCitta(){
		String sql=" SELECT DISTINCT Localita "+
					" FROM situazione ";
		List<Citta> citta = new ArrayList<Citta>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Citta cittaTemp=new Citta(rs.getString("Localita"));
				citta.add(cittaTemp);
			}

			conn.close();
			return citta;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

}
