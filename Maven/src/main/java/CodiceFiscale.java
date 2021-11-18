public class CodiceFiscale {
	
	//@ ensures (codiceFiscale != null);
	private /*@ spec_public @*/ char[] codiceFiscale;
	
	public CodiceFiscale(char[] codiceFiscale) {
		if (!controllaStruttura(codiceFiscale)) throw new IllegalArgumentException("Codice fiscale non valido");
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
	protected boolean controllaStruttura(char [] codiceFiscale) {
		if  (codiceFiscale == null) throw new IllegalArgumentException("Codice fiscale nullo");
		if (codiceFiscale.length != 16) throw new IllegalArgumentException("Lunghezza errata");
		
		//primi sei caratteri sono lettere 
		for (int i=0; i<6; i++) {
			if (codiceFiscale[i] > 'Z' || codiceFiscale[i] < 'A') throw new IllegalArgumentException("Carattere " + i + " non lettera");
		}
		
		
		//due interi
		for (int i=6; i<8; i++) {
			if (codiceFiscale[i]>'9' || codiceFiscale[i] < '0') throw new IllegalArgumentException("Carattere " + i + " non numerico");
		}
		
		//mese nascita
		if ("ABCDEHLMPRST".indexOf(codiceFiscale[8]) == -1) throw new IllegalArgumentException("Mese non valido");
		
		//giorno nascita
		if (codiceFiscale[9] > '9' || codiceFiscale[9]<'0') throw new IllegalArgumentException("Valore non numerico");
		if (codiceFiscale[10] > '9' || codiceFiscale[10]<'0') throw new IllegalArgumentException("Valore non numerico");
		if (Integer.valueOf(codiceFiscale[9] - '0')*10 + Integer.valueOf(codiceFiscale[10] - '0') > 31) throw new IllegalArgumentException("Giorno non valido");
		
		//ulitmi 5 caratteri
		if (
			!(codiceFiscale[11] >= 'A' && codiceFiscale[11] <= 'Z') || 
			!(codiceFiscale[12] >= '0' && codiceFiscale[12] <= '9') || 
			!(codiceFiscale[13] >= '0' && codiceFiscale[13] <= '9') ||
			!(codiceFiscale[14] >= '0' && codiceFiscale[14] <= '9') ||
			!(codiceFiscale[15] >= 'A' && codiceFiscale[15] <= 'Z')) throw new IllegalArgumentException("Ultimi 5 caratteri non validi");
		
		return true;
					
	}
	
	//@ requires (nome != null && cognome != null && nascita != null);
	public boolean isValid(String nome, String cognome, Data nascita, Nazione stato, Comune comune) {
		if (nome == null || cognome == null || nascita == null) throw new IllegalArgumentException("Parametri nulli");
		
		//controllo cognome
		String cod = "";
		for (int i=0; i<3; i++) {
			cod+=codiceFiscale[i];
		}
		if ( !cod.equals(CodiceFiscale.nomeToCodice(cognome)) ) return false;
		
		//controllo nome
		cod = "";
		for (int i=3; i<6; i++) {
			cod+=codiceFiscale[i];
		}
		if ( !cod.equals(CodiceFiscale.nomeToCodice(nome))) return false;
		
		//controllo anno nascita
		if (codiceFiscale[6]-'0' != (nascita.getAnno()/10)%10) return false;
		if (codiceFiscale[7]-'0' != nascita.getAnno()%10) return false;
		
		//controllo mese nascita
		String mesi = "ABCDEHLMPRST";
		if (codiceFiscale[8] != mesi.charAt(nascita.getMese()-1)) return false;
		
		//controllo giorno nascita
		int giorno = (codiceFiscale[9] - '0')*10 + codiceFiscale[10] - '0';
		if (giorno != nascita.getGiorno()) return false;
		
		
		//controllo nazione nascita-comune
		if (stato.GetIso3().equals("ITA")) {
			String com = String.valueOf(codiceFiscale).substring(11,15);
			if (!com.equals(comune.getComuneCode())) return false;
		} else {
			if (codiceFiscale[11] != 'Z') return false;
			String naz = String.valueOf(codiceFiscale).substring(12, 15);
			if (!naz.equals(stato.GetNumeric())) return false;
		}
		
		if (checksum()==0)return false;
		
		return true;
	}
	
	private int checksum() {
		String pari = "";
		String dispari = "";
		
		//TODO
		return 1;
	}
	
	//@ requires (nome != null);
	protected static String nomeToCodice(String nome) {
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
	
}
