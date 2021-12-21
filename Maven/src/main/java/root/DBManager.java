package root;

import root.App;
import root.util.CodiceFiscale;
import root.util.Data;
import root.util.DatiVoto;
import root.util.Elettore;
import root.util.ModalitaConteggio;
import root.util.ModalitaVoto;
import root.util.Scheda;
import root.util.Scrutinatore;
import root.util.Sesso;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import com.google.common.hash.Hashing;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


public class DBManager {
	
	private static DBManager _instance;
	private static String url="jdbc:sqlite:database.db";
	private Connection db;
	
	private DBManager() {
		
	}
	
	/**
	 * 
	 * @return l'istanza di DBController in uso (instanziandola se non già esistente)
	 * @throws Exception se c'è errore nella connessione con il databases
	 */
	public static DBManager getInstance() {
		if (_instance == null) {
			_instance = new DBManager();
			try {
				_instance.db = DriverManager.getConnection(url);
				PreparedStatement st = _instance.db.prepareStatement("CREATE TABLE IF NOT EXISTS elettore (email TEXT, password TEXT, nome TEXT, cognome TEXT, tessera TEXT, luogo_nascita TEXT, data_nascita TEXT, CF TEXT, sesso TEXT,  PRIMARY KEY (CF))");
				st.executeUpdate();
				
				st = _instance.db.prepareStatement("CREATE TABLE IF NOT EXISTS scrutinatore (email TEXT, password TEXT, PRIMARY KEY (email))");
				st.executeUpdate();
				
				st = _instance.db.prepareStatement("CREATE TABLE IF NOT EXISTS scheda (nome TEXT, dataInizio TEXT, dataFine, tipoVoto TEXT, datiVoto TEXT, tipoVincitore TEXT, scrutinata TEXT)");
				st.executeUpdate();
				
				st = _instance.db.prepareStatement("CREATE TABLE IF NOT EXISTS votoElettore (CF TEXT, scheda TEXT)");
				st.executeUpdate();
				
				st = _instance.db.prepareStatement("CREATE TABLE IF NOT EXISTS votoScheda (voto TEXT, scheda TEXT)");
				st.executeUpdate();
				
				boolean res = _instance.insertScrutinatore("rompa@bob.it","123456");
				
			} catch (SQLException e) {
				System.err.println("Errore nella connessione con il db :\n" + e.getMessage());
				//throw new Exception("Errore nella connessione con il db :\n" + e.getMessage());
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
	 * Se CF e psw sono corretti, crea istanza di Elettore in App e ritorna true
	 * @param CF
	 * @param psw
	 * @return
	 */
	public boolean elettoreLogin(CodiceFiscale CF, String psw) {
		try {		
    		String sha256hex = Hashing.sha256()
					  .hashString(psw, StandardCharsets.UTF_8)
					  .toString();
    		
    		// prendi riga dell'elettore
    		String email = "";
    		String password = "";
    		String nome = "";
    		String cognome = "";
    		String tessera = "";
    		String luogo = "";
    		Data data = null;
    		Sesso sesso = Sesso.M;
			PreparedStatement st = db.prepareStatement("SELECT * FROM elettore AS U WHERE U.CF = ?");
			st.setString(1, CF.toString());
			ResultSet set = st.executeQuery();
			if (set==null) throw new NoSuchElementException("Elettore non trovato");		
			//email, password, nome, cognome, tessera, luogo_nascita, data_nascita, CF, sesso
			while (set.next()) {
				email = set.getString(1);
				password = set.getString(2);
				nome = set.getString(3);
				cognome = set.getString(4);
				tessera = set.getString(5);
				luogo = set.getString(6);
				data = new Data(set.getString(7));
				sesso = set.getString(8).equals("M") ? Sesso.M : Sesso.F;
			}
    		
    		if (password.equals(sha256hex)) {
    			// nome cognome sesso data luogo CF tessera
    			App.getInstance().setElettore(new Elettore(
    					nome, cognome, sesso, data, luogo, CF, tessera
    					));
    			return true;
    		} else {
    			return false;
    		}
    	} catch (Exception e) {
    		System.err.println(e.getMessage());
    		return false;
    	}
	}
	
	/**
	 * Se email e psw sono corretti, crea istanza di Scrutinatore in App e ritorna true
	 * @param CF
	 * @param psw
	 * @return
	 */
	public boolean scrutinatoreLogin(String email, String psw) {
		try {		
    		String sha256hex = Hashing.sha256()
					  .hashString(psw, StandardCharsets.UTF_8)
					  .toString();
    		
    		// prendi riga dell'scrutinatore
    		String password = "";
			PreparedStatement st = db.prepareStatement("SELECT * FROM scrutinatore AS U WHERE U.email = ?");
			st.setString(1, email);
			ResultSet set = st.executeQuery();
			if (set==null) throw new NoSuchElementException("Scrutinatore non trovato");		
			//email, password
			while (set.next()) {
				email = set.getString(1);
				password = set.getString(2);
			}
    		
    		if (password.equals(sha256hex)) {
    			App.getInstance().setScrutinatore(new Scrutinatore(email));
    			return true;
    		} else {
    			return false;
    		}
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
			e.printStackTrace();
			return false;
		}
	}
	
}
