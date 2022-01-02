package root;

import root.util.*;
import java.util.logging.*;
import java.io.IOException;
import java.nio.file.FileSystems;

public class LogManager {
	
	private static final String path = FileSystems.getDefault().getPath("").toAbsolutePath().toString() + "/LOG.log";
	private static final Logger logger = Logger.getLogger(LogManager.class.getName());
	private static FileHandler filehandler;
	private static LogManager _instance;
	
	private LogManager() {
		try {
			filehandler = new FileHandler(path, true);
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
		filehandler.setFormatter(new SimpleFormatter());
		String cf = el.getCF().toString();
		logger.addHandler(filehandler);
		logger.setLevel(Level.INFO);
		logger.info("Elettore " + cf +" "+ msg);
			
	}
	
	/**
	 * Questa funzione ha lo scopo di loggare un'eccezione
	 * @param e l'eccezione da loggare
	 */
	public void logException(Exception e) {
		filehandler.setFormatter(new SimpleFormatter());
		logger.addHandler(filehandler);
		logger.log(Level.SEVERE, "An exception occurred", e);
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
		filehandler.setFormatter(new SimpleFormatter());
		String nome = s.getEmail();
		logger.addHandler(filehandler);
		logger.setLevel(Level.INFO);
		logger.info("Scrutinatore " + nome +" " + msg);
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