package root.util;

import java.util.ArrayList;
import java.util.List;

public class CodiceFiscale {
	
	//@ public invariant codiceFiscale != null;
	private /*@ spec_public @*/ char[] codiceFiscale;
	
	public CodiceFiscale(char[] codiceFiscale) {
		if (codiceFiscale == null) throw new IllegalArgumentException("Codice nullo");
		if (!controllaStruttura(codiceFiscale)) throw new IllegalArgumentException("Codice fiscale non valido");
		this.codiceFiscale = codiceFiscale;
	}
	
	//@ requires (codiceFiscale != null);
	//@ requires (codiceFiscale.length == 16);
	//@ requires (codiceFiscale[11] >= 'A' && codiceFiscale[11] <= 'Z');
	//@ requires (codiceFiscale[12] >= '0' && codiceFiscale[12] <= '9');
	//@ requires (codiceFiscale[13] >= '0' && codiceFiscale[13] <= '9');
	//@ requires (codiceFiscale[14] >= '0' && codiceFiscale[14] <= '9');
	//@ requires (codiceFiscale[15] >= 'A' && codiceFiscale[15] <= 'Z');
	//@ ensures (\result == true);
	private boolean controllaStruttura(char [] codice) {
		if  (codice == null) throw new IllegalArgumentException("Codice fiscale nullo");
		if (codice.length != 16) throw new IllegalArgumentException("Lunghezza errata");
		//primi sei caratteri sono lettere 
		for (int i=0; i<6; i++) {
			if (codice[i] > 'Z' || codice[i] < 'A') throw new IllegalArgumentException("Carattere " + codice[i] + " non lettera");
		}
		
		
		//due interi
		for (int i=6; i<8; i++) {
			if (codice[i]>'9' || codice[i] < '0') throw new IllegalArgumentException("Carattere " + codice[i] + " non numerico");
		}
		
		//mese nascita
		if ("ABCDEHLMPRST".indexOf(codice[8]) == -1) throw new IllegalArgumentException("Mese non valido");
		
		//giorno nascita
		if (codice[9] > '9' || codice[9]<'0') throw new IllegalArgumentException("Valore non numerico");
		if (codice[10] > '9' || codice[10]<'0') throw new IllegalArgumentException("Valore non numerico");
		//if (Integer.valueOf(codiceFiscale[9] - '0')*10 + Integer.valueOf(codiceFiscale[10] - '0') > 31) throw new IllegalArgumentException("Giorno non valido");
		
		//ulitmi 5 caratteri
		if (
			!(codice[11] >= 'A' && codice[11] <= 'Z') || 
			!(codice[12] >= '0' && codice[12] <= '9') || 
			!(codice[13] >= '0' && codice[13] <= '9') ||
			!(codice[14] >= '0' && codice[14] <= '9') ||
			!(codice[15] >= 'A' && codice[15] <= 'Z')) throw new IllegalArgumentException("Ultimi 5 caratteri non validi");
		
		return true;
					
	}
	
	//@ requires (nome != null && cognome != null && nascita != null && stato != null);
	//@ ensures (!(!stato.getIso3().equals("ITA") ==> codiceFiscale[11] == 'Z') ==> \result == false);
	public boolean isValid(String nome, String cognome, Data nascita, Nazione stato, Comune comune, Sesso sex) {
		if (nome == null || cognome == null || nascita == null || stato == null) throw new IllegalArgumentException("Parametri nulli");
		
		//controllo cognome
		String cod = "";
		cognome = cognome.replace(" ", "");
		for (int i=0; i<3; i++) {
			cod+=codiceFiscale[i];
		}
		if ( !cod.equals(CodiceFiscale.nomeToCodice(cognome, true)) ) return false;
		
		//controllo nome
		nome = nome.replace(" ", "");
		cod = "";
		for (int i=3; i<6; i++) {
			cod+=codiceFiscale[i];
		}
		if ( !cod.equals(CodiceFiscale.nomeToCodice(nome, false))) return false;
		
		//controllo anno nascita
		if (codiceFiscale[6]-'0' != (nascita.getAnno()/10)%10) return false;
		if (codiceFiscale[7]-'0' != nascita.getAnno()%10) return false;
		
		//controllo mese nascita
		String mesi = "ABCDEHLMPRST";
		if (codiceFiscale[8] != mesi.charAt(nascita.getMese()-1)) return false;
		
		//controllo giorno nascita
		int giorno = (codiceFiscale[9] - '0')*10 + codiceFiscale[10] - '0';
		if (sex.equals(Sesso.F)) giorno -= 40;	
		if (giorno != nascita.getGiorno()) return false;
		
		
		//controllo nazione nascita-comune
		if (stato.getIso3().equals("ITA")) {
			String com = String.valueOf(codiceFiscale).substring(11,15);
			if (!com.equals(comune.getComuneCode())) return false;
		} else {
			if (codiceFiscale[11] != 'Z') return false;
			String naz = String.valueOf(codiceFiscale).substring(12, 15);
			if (!naz.equals(stato.getNumeric())) return false;
		}
		
		if (checksum()!=codiceFiscale[15])return false;
		
		return true;
	}
	
	/**
	 *  
	 * @return l'ultimo carattere del codice fiscale computanto il valore dei primi 15 
	 */
	private char checksum() {
		String pari = "";
		String dispari = "";
		
		for (int i=0; i<codiceFiscale.length-1; i++) {
			if (i%2==0) {
				pari+=codiceFiscale[i];
			} else {
				dispari+=codiceFiscale[i];
			}
		}
		
		int p=0;
		for (int i=0; i<dispari.length(); i++) {
			if (dispari.charAt(i) >= '0' && dispari.charAt(i) <= '9') {
				p+=dispari.charAt(i) - '0';
			} else {
				p+=dispari.charAt(i) - 'A';
			}
		}
		
		int[] t_ints = new int[]{1,0,5,7,9,13,15,17,19,21,2,4,18,20,11,3,6,8,12,14,16,10,22,25,24,23};
		List<Integer> ints = new ArrayList<>();
		for (Integer i:t_ints) {
			ints.add(i);
		}
		int d=0;
		for (int i=0; i<pari.length();i++) {
			char ch = pari.charAt(i);
			if (ch >='0' && ch <='9') {
				d+=ints.get(ch -'0');
			} else {
				d+=ints.get(ch-'A');
			}
		}
		
		return (char)((p+d)%26+'A');
	}
	
	/**
	 * Il metodo restituisce una stringa composta dalle consonanti seguite dalle vocali del (seza duplicati), seguite da eventuali 
	 * caratteri 'X' se la lunghezza raggiunta è minore di tre. 
	 * @param nome il nome da cui trarre il codice.
	 * @return il codice di tre lettere.
	 */
	//@ requires (nome != null);
	private static String nomeToCodice(String nome, boolean cognome) {
		if (nome == null) throw new IllegalArgumentException("Nome nullo");
		
		String n = nome.toUpperCase();
	
		String cons = "";
		String voc = "";
		for (int i=0; i<nome.length(); i++) {
			if ("AEIOU".indexOf(n.charAt(i)) == -1) { 
				cons += n.charAt(i);
			} else {
				voc += n.charAt(i);
			}
		}
		if (cons.length()>3 && !cognome) return cons.substring(0,1) + cons.substring(2,4);

		String codice = cons+voc;
		if (codice.length()<3) {
			for (int i=codice.length(); i<3; i++) {
				codice += "X";
			}
		} else if (codice.length()>3) {
			codice = codice.substring(0,3);
		}
		
		return codice;
	}
	
	/**
	 * Crea un'istanza di CodiceFiscale se s corretta
	 * @param s
	 * @return istanza di CodiceFiscale
	 */
	public static CodiceFiscale fromStringToCF(String s) {
		try {
    		String txt = s.toUpperCase();
    		char [] codice = new char[16];
    		for (int i=0; i<codice.length; i++) {
    			codice[i] = txt.charAt(i);
    		}
			return new CodiceFiscale(codice);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}
	
	@Override 
	public String toString() {
		String s = "";
		for (int i=0; i<codiceFiscale.length; i++) {
			s+= codiceFiscale[i];
		}
		return s;
	}
	
}
