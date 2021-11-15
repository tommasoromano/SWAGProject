import java.util.Arrays;

public class CodiceFiscale {
	
	private char[] codiceFiscale;
	
	public CodiceFiscale(char[] codiceFiscale) {
		if (!controllaValidita(codiceFiscale)) throw new IllegalArgumentException("Codice fiscale non valido");
		
		this.codiceFiscale = codiceFiscale;
		
	}
	
	//@ requires (codice != null);
	//@ requires (codice.length == 16);
	//@ requires (codice[11] >= 'A' && codice[11] <= 'Z');
	//@ requires (codice[12] >= '0' && codice[12] <= '9');
	//@ requires (codice[13] >= '0' && codice[13] <= '9');
	//@ requires (codice[14] >= '0' && codice[14] <= '9');
	//@ requires (codice[15] >= 'A' && codice[15] <= 'Z');
	// TODO -> @ requires ((stato.equals("IT")) ==> codice[11] == 'Z');
	protected static boolean controllaValidita(char [] codice) {
		if  (codice == null) throw new IllegalArgumentException("Codice fiscale nullo");
		if (codice.length != 16) throw new IllegalArgumentException("Lunghezza errata");
		
		//primi sei caratteri sono lettere 
		for (int i=0; i<6; i++) {
			if (codice[i] > 'Z' || codice[i] < 'A') throw new IllegalArgumentException("Carattere " + i + " non lettera");

		}
		
		//due interi
		for (int i=6; i<8; i++) {
			if (codice[i]>'9' || codice[i] < '0') throw new IllegalArgumentException("Carattere " + i + " non numerico");
		}
		
		//mese nascita
		if ("ABCDEHLMPRST".indexOf(codice[8]) == -1) throw new IllegalArgumentException("Mese non valido");
		
		//giorno nascita
		if (codice[9] > '9' || codice[9]<'0') throw new IllegalArgumentException("Valore non numerico");
		if (codice[10] > '9' || codice[10]<'0') throw new IllegalArgumentException("Valore non numerico");
		if (Integer.valueOf(codice[9] - '0')*10 + Integer.valueOf(codice[10] - '0') > 31) throw new IllegalArgumentException("Giorno non valido");
		
		//ulitmi 5 caratteri
		if (
			!(codice[11] >= 'A' && codice[11] <= 'Z') || 
			!(codice[12] >= '0' && codice[12] <= '9') || 
			!(codice[13] >= '0' && codice[13] <= '9') ||
			!(codice[14] >= '0' && codice[14] <= '9') ||
			!(codice[15] >= 'A' && codice[15] <= 'Z')) throw new IllegalArgumentException("Ultimi 5 caratteri non validi");
		
		return true;
					
	}
	
	/*
	private static String toCodice(String nome) {
		String s = "";
		String n = nome.toUpperCase();
		for (int i=0; i<n.length(); i++) {
			if (s.length() == 3) break;
			if ("AEIOU".indexOf(n.charAt(i)) == -1) {
				s+=n.charAt(i);
			}
		}
		
		if (s.length()<3) {
			for (int i=0; i<n.length(); i++) {
				if (s.length()==3) break;
				if ("AEIOU".indexOf(n.charAt(i)) != -1) {
					s+=n.charAt(i);
				}
			}
		}
		
		if (s.length() < 3) {
			while (!s.length() == 3) {
				s+="X";
			}
		}
		
		return s;
	}*/
	
}
