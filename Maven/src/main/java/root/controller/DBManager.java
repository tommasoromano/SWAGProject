package root.controller;

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
import java.util.NoSuchElementException;
import java.util.Random;


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
				
				st = _instance.db.prepareStatement("CREATE TABLE IF NOT EXISTS scheda (id TEXT, nome TEXT, dataInizio TEXT, dataFine, tipoVoto TEXT, datiVoto TEXT, tipoVincitore TEXT, scrutinata TEXT, PRIMARY KEY (id))");
				st.executeUpdate();
				
				st = _instance.db.prepareStatement("CREATE TABLE IF NOT EXISTS votoElettore (CF TEXT, scheda TEXT)");
				st.executeUpdate();
				
				st = _instance.db.prepareStatement("CREATE TABLE IF NOT EXISTS votoScheda (voto TEXT, scheda TEXT)");
				st.executeUpdate();
				
				st = _instance.db.prepareStatement("CREATE TABLE IF NOT EXISTS gruppo (nome TEXT, PRIMARY KEY(nome))");
				st.executeUpdate();
				
				st = _instance.db.prepareStatement("CREATE TABLE IF NOT EXISTS candidato (nome TEXT, cognome TEXT, partito TEXT, PRIMARY KEY (nome, cognome, partito), FOREIGN KEY(partito) REFERENCES gruppo(nome))");
				st.executeUpdate();
				
				//boolean res = _instance.insertScrutinatore("rompa@bob.it","123456");
				//res = _instance.insertScrutinatore("admin@test.it","123456");
				
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
			App.getInstance().setElettore(new Elettore(nome, cognome, s, nascita, luogo, CF, tessera));
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * @param nome
	 * @param inizio
	 * @param fine
	 * @param tipoVoto
	 * @param tipoVincitore
	 * @return
	 */
	public boolean creaScheda(String nome, Data inizio, Data fine, ModalitaVoto modVoto, DatiVoto datiVoto, ModalitaConteggio modConteggio) {
		// crea un nuovo id univoco
		Random r = new Random();
		int id = r.nextInt(1000000-0) + 0;
		while (getSchedaById(id) != null) {
			r = new Random();
			id = r.nextInt(1000000-0) + 0;
		}
		
		// crea la scheda
		try {
			PreparedStatement st = db.prepareStatement("INSERT INTO scheda VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

			st.setString(1, Integer.toString(id));
			st.setString(2, nome);
			st.setString(3, inizio.toString());
			st.setString(4, fine.toString());
			st.setString(5, modVoto.toString());
			st.setString(6, datiVoto.toString());
			st.setString(7, modConteggio.toString());
			st.setString(8, "false");
			
			st.executeUpdate();
			return true;
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}
	}
	
	public Scheda getSchedaById(int id) {
		try {		
    		Scheda scheda = null;
    		
			PreparedStatement st = db.prepareStatement("SELECT * FROM scheda AS S WHERE S.id = ?");
			st.setString(1, Integer.toString(id));
			ResultSet set = st.executeQuery();
			if (set==null) {
				throw new NoSuchElementException("Scheda non trovata");		
			}
			while (set.next()) {
				scheda =
						new Scheda(
								Integer.parseInt(set.getString(1)),
								set.getString(2),
								new Data(set.getString(3)),
								new Data(set.getString(4)),
								new ModalitaVoto(set.getString(5)),
								new DatiVoto(set.getString(6)),
								new ModalitaConteggio(set.getString(7)),
								set.getString(8).equals("true")
								);
				return scheda;
			}
			return scheda;
    	} catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}
	}
	
	public boolean scrutinaScheda(Scheda s) {
		try {		
			PreparedStatement st = db.prepareStatement("UPDATE scheda SET scrutinata=? WHERE id=?");
			st.setString(1, "true");
			st.setString(2, Integer.toString(s.getId()));
			st.executeUpdate();
			return true;
    	} catch (Exception e) {
    		e.printStackTrace();
    		return false;
    	}
	}
	
	public boolean visualizzaEsito(Scheda s) {
		return false;
	}
	
	public Scheda[] getSchede() {
		try {		
    		
			PreparedStatement st = db.prepareStatement("SELECT * FROM scheda");
			ResultSet row = db.prepareStatement("SELECT COUNT(*) FROM scheda").executeQuery();
			ResultSet set = st.executeQuery();
			
			if (set==null || row == null) return null;		
			
			Scheda []schede = new Scheda[row.getInt(1)];
			while (set.next()) {
				schede[set.getRow()-1] =
					new Scheda(
							Integer.parseInt(set.getString(1)),
							set.getString(2),
							new Data(set.getString(3)),
							new Data(set.getString(4)),
							new ModalitaVoto(set.getString(5)),
							new DatiVoto(set.getString(6)),
							new ModalitaConteggio(set.getString(7)),
							set.getString(8).equals("true")
							);
			}
    		
    		return schede;
    	} catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}
	}
	
	public boolean hasElettoreVotoScheda(Scheda s) {
		try {		
    		PreparedStatement st = db.prepareStatement("SELECT * FROM votoElettore AS V WHERE v.CF = ? AND V.scheda = ?");
			st.setString(1, App.getInstance().getElettore().getCF().toString());
			st.setString(2, Integer.toString(s.getId()));
			ResultSet set = st.executeQuery();
			if (set==null) {
				return false;		
			}
			while (set.next()) {
				return true;
			}
			return false;
    	} catch (Exception e) {
    		e.printStackTrace();
    		return false;
    	}
	}
	
	/**
	 * Inserisce il partito nel database
	 * @param nome il nome del partito
	 * @return true se il partito non è già presente, false altrimenti
	 */
	public boolean insertPartito(String nome) {
		try {
			PreparedStatement st = db.prepareStatement("INSERT INTO gruppo VALUES (?)");
			st.setString(1, nome);
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Inserisce il candidato nel db, se il suo partito è già nel database
	 * @param nome del candidato
	 * @param cognome del candidato
	 * @param partito del candidato
	 * @return true se il candidato è inserito correttamente, false altrimenti
	 */
	public boolean insertCandidato(String nome, String cognome, String partito) {
		try {
			PreparedStatement st = db.prepareStatement("SELECT * FROM gruppo AS G WHERE G.nome = ?");
			st.setString(1, partito);
			ResultSet res = st.executeQuery();
			if (res == null) return false;
			
			st = db.prepareStatement("INSERT INTO candidato VALUES (?, ?, ?)");
			st.setString(1, nome);
			st.setString(2, cognome);
			st.setString(3, partito);
			st.executeUpdate();
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 
	 * @return l'array conposto dai nomi e partito dei candidati come stringa 
	 */
	public String [] getCandidati() {
		String [] candidati;
		try {
			PreparedStatement st = db.prepareStatement("SELECT COUNT(*) FROM candidato");
			ResultSet res = st.executeQuery();
			if (res == null) return null;
			candidati = new String [res.getInt(1)];
			
			st = db.prepareStatement("SELECT * FROM candidato");
			res = st.executeQuery();
			if (res == null) return null;
			
			int i=0;
			while (res.next()) {
				candidati[i] = res.getString(1)+ " " + res.getString(2) + ", partito: " + res.getString(3);
				i++;
			}
			
			return candidati;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean votaScheda(Scheda s, String voto) {
		try {
			PreparedStatement st = db.prepareStatement("INSERT INTO votoScheda VALUES (?, ?)");
			st.setString(1, voto);
			st.setString(2, Integer.toString(s.getId()));
			st.executeUpdate();
			

			st = db.prepareStatement("INSERT INTO votoElettore VALUES (?, ?)");
			st.setString(1, App.getInstance().getElettore().getCF().toString());
			st.setString(2, Integer.toString(s.getId()));
			st.executeUpdate();
			return true;
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}
	}
	
}
