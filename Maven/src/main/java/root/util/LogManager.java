package root.util;

import java.util.logging.*;
import java.sql.Timestamp;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.io.File;

public class LogManager {
	
	private static final String path = FileSystems.getDefault().getPath("").toAbsolutePath().toString() + "/LOG.log";
	private static final Logger logger = Logger.getLogger(LogManager.class.getName());
	private static LogManager _instance;
	
	private LogManager() {
		try {
			File f = new File(path);
			if (!f.exists()) {
				f.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
			
	}
	
	public static LogManager getInstance() {
		if (_instance == null) {
			_instance = new LogManager();
		}
		
		return _instance;
	}
	
	/**
	 * Questa funzione effettua il logging di un elettore
	 * @param el l'elettore
	 * @param msg messaggio aggiuntivo del log
	 */
	private void logElettore(Elettore el, String msg) {
		FileHandler f = null;
		try {
			f = new FileHandler(path, true);
			f.setFormatter(new SimpleFormatter());
			String cf = el.getCF().toString();
			logger.addHandler(f);
			logger.setLevel(Level.INFO);
			logger.info("Elettore " + cf +" "+ msg);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * Questa funzione ha lo scopo di loggare un'eccezione
	 * @param e l'eccezione da loggare
	 */
	public void logException(Exception e) {
		FileHandler f = null;
		try {
			f = new FileHandler(path, true);
			f.setFormatter(new SimpleFormatter());
			logger.addHandler(f);
			logger.log(Level.SEVERE, "An exception occurred", e);
		} catch (IOException ex) {
			ex.printStackTrace();
		}	
	}
	
	public void logElettoreRegistrazione(Elettore e) {
		logElettore(e, "registrazione");
	}
	
	public void logElettoreLogin(Elettore e) {
		logElettore(e, "log in");
	}
	
	/**
	 * Questa funzione effettua il logging di uno scrutinatore
	 * @param s lo scrutinatore 
	 * @param msg messaggio aggiuntivo per il logging
	 */
	private void logScrutinatore(Scrutinatore s, String msg) {
		FileHandler f = null;
		try {
			f = new FileHandler(path, true);
			f.setFormatter(new SimpleFormatter());
			String nome = s.getEmail();
			logger.addHandler(f);
			logger.setLevel(Level.INFO);
			logger.info("Scrutinatore " + nome +" " + msg);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public void logElettoreLogout(Elettore e) {
		logElettore(e, "log out");
	}
	
	public void logScrutinatoreLogout(Scrutinatore s) {
		logScrutinatore(s, "log out");
	}
	
	public void logScrutinatoreLogin(Scrutinatore s) {
		logScrutinatore(s, "log in");
	}
	
	public void logCreaScheda(Scrutinatore scr, String sch) {
		logScrutinatore(scr, "crea nuova scheda: " + sch);
	}
	
	public void logVotazione(Elettore e, String sch) {
		logElettore(e, "ha votato scheda: "+ sch);
	}
	
}