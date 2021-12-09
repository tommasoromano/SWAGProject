package root.controller;

import root.util.CodiceFiscale;
import root.util.Data;
import root.util.Elettore;
import root.util.Sesso;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import com.google.common.hash.Hashing;
import java.util.NoSuchElementException;


public class DBController {
	
	private static DBController _instance;
	private static String url="jdbc:sqlite:database.db";
	private Connection db;
	
	private DBController() {
		
	}
	
	/**
	 * 
	 * @return l'istanza di DBController in uso (instanziandola se non già esistente)
	 * @throws Exception se c'è errore nella connessione con il databases
	 */
	public static DBController getInstance() throws Exception {
		if (_instance == null) {
			_instance = new DBController();
			try {
				_instance.db = DriverManager.getConnection(url);
				PreparedStatement st = _instance.db.prepareStatement("CREATE TABLE IF NOT EXISTS elettore (email TEXT, password TEXT, nome TEXT, cognome TEXT, tessera TEXT, luogo_nascita TEXT, data_nascita TEXT, CF TEXT, sesso TEXT,  PRIMARY KEY (CF))");
				st.executeUpdate();
				
				st = _instance.db.prepareStatement("CREATE TABLE IF NOT EXISTS scrutinatore (email TEXT, password TEXT, PRIMARY KEY (email))");
				st.executeUpdate();
				
			} catch (SQLException e) {
				throw new Exception("Errore nella connessione con il db :\n" + e.getMessage());
			}
		}
		
		return _instance;
	}
	
	/**
	 * 
	 * @param username dello scrutinatore
	 * @param psw, la password dello scrutinatore
	 * @return true se lo scrutinatore è inserito correttamente, false altrimenti
	 */
	private boolean insertScrutinatore(String username, String psw) {
		try {
			String hashed_psw = Hashing.sha256()
					  .hashString(psw, StandardCharsets.UTF_8)
					  .toString();
			PreparedStatement st = db.prepareStatement("INSERT INTO scrutinatore VALUES (?, ?)");
			st.setString(1, username);
			st.setString(2, hashed_psw);
			
			st.executeUpdate();
			return true;
		} catch (SQLException e) { 
			System.err.println(e.getMessage());
			return false;
		}
	}
	/**
	 * 
	 * @param username dell'utente.
	 * @return la password hashata relativa a quell'utente.
	 * @throws NoSuchElementException username non è presente nel database.
	 */
	private String getPswElettore(CodiceFiscale CF) {
		String s="";
		try {
			PreparedStatement st = db.prepareStatement("SELECT * FROM elettore AS U WHERE U.CF = ?");
			st.setString(1, CF.toString());
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
	
	public boolean elettoreLogin(CodiceFiscale CF, String psw) {
		try {		
    		String sha256hex = Hashing.sha256()
					  .hashString(psw, StandardCharsets.UTF_8)
					  .toString();
    		String password = getPswElettore(CF);
    		return password.equals(sha256hex);
    	} catch (Exception e) {
    		System.err.println(e.getMessage());
    		return false;
    	}
	}
	
	/**
	 * Inserisce i dati di login nel database
	 * @param username
	 * @param psw
	 * @return true se i dati sono inseriti correttamente nel database, false altrimenti.
	 */
	public boolean registerElettore(String mail, String psw, String nome, String cognome, Data nascita, String luogo, CodiceFiscale CF, String tessera, Sesso s) {
		
		try {
			PreparedStatement st = db.prepareStatement("INSERT INTO elettore VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
			String sha256hex = Hashing.sha256()
					  .hashString(psw, StandardCharsets.UTF_8)
					  .toString();
			st.setString(1, mail);
			st.setString(2, sha256hex);
			st.setString(3, nome);
			st.setString(4, cognome);
			st.setString(5,  tessera);
			st.setString(6, luogo);
			st.setString(7,  nascita.toString());
			st.setString(8, CF.toString());
			st.setString(9, s.toString());
			
			st.executeUpdate();
			return true;
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}
	}
}
