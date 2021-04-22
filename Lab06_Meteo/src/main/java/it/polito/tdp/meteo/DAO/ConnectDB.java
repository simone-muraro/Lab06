package it.polito.tdp.meteo.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectDB {
	
	private static final String jdbcURL = "jdbc:mysql://localhost/meteo";
	private static HikariDataSource ds;
	// check user e password
	//static private final String jdbcUrl = "jdbc:mysql://localhost/meteo?user=root&password=simone";
	

	public static Connection getConnection() {

		if(ds == null) {
			HikariConfig config = new HikariConfig();
			config.setJdbcUrl(jdbcURL);
			config.setUsername("root");
			config.setPassword("simone");
			
			config.addDataSourceProperty("cachePrepStmts", true);
			config.addDataSourceProperty("prepStmtChacheSize", 250);
			config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
			
			ds = new HikariDataSource(config);
		}
		try {
//				Connection connection = DriverManager.getConnection(jdbcUrl);
//				return connection;
			return ds.getConnection();
			

		} catch (SQLException e) {

//			e.printStackTrace();
//			throw new RuntimeException("Cannot get a connection " + jdbcUrl, e);
			System.err.println("Errore di connessione ad db");
			throw new RuntimeException(e);
		}
		
	}

}
