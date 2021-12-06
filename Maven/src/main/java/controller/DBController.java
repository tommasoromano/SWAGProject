package controller;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import com.google.common.hash.Hashing;
import java.util.NoSuchElementException;


public class DBController {
	private static String url="jdbc:sqlite:database.db";
	private Connection db;
	
	/**
	 * Inizializza this a un controller del database degli elettori 
	 * @throws Exception se c'è un errore nella connessione con il database 
	 */
	public DBController() throws Exception {
		try {
			db = DriverManager.getConnection(url);
			PreparedStatement st = db.prepareStatement("CREATE TABLE IF NOT EXISTS users (user TEXT, password TEXT, PRIMARY KEY (user))");
			st.executeUpdate();
			
		} catch (SQLException e) {
			throw new Exception("Errore nella connessione con il db :\n" + e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param username dell'utente.
	 * @return la password hashata relativa a quell'utente.
	 * @throws NoSuchElementException username non è presente nel database.
	 */
	public String getPsw(String username) {
		String s="";
		try {
			PreparedStatement st = db.prepareStatement("SELECT * FROM users AS U WHERE U.user = ?");
			st.setString(1, username);
			ResultSet set = st.executeQuery();
			if (set==null) throw new NoSuchElementException("Username not found");		
			
			while (set.next()) {
				s = set.getString(2);
			}
		} catch (SQLException e ) {
			System.err.println(e.getMessage());
		}
		return s;
	}
	
	/**
	 *
	 * @param username
	 * @param psw
	 * @return true se l'username e la password (hashata) sono inseriti correttamente nel database, false altrimenti.
	 */
	public boolean insertUserPsw(String username, String psw) {
		try {
			PreparedStatement st = db.prepareStatement("INSERT INTO users VALUES (?, ?)");
			String sha256hex = Hashing.sha256()
					  .hashString(psw, StandardCharsets.UTF_8)
					  .toString();
			st.setString(1, username);
			st.setString(2, sha256hex);
			st.executeUpdate();
			return true;
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}
	}
}
