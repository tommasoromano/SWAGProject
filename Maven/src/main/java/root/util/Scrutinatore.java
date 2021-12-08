package root.util;

public class Scrutinatore extends Utente {

	//@ public invariant codiceGestore != null;
	private /*@ spec_public @*/ String codiceGestore;
	
	//@ requires codiceGestore != null;
	public Scrutinatore(String nome, String cognome, Sesso sesso, 
			Data dataNascita, Comune comuneNascita, Nazione nazioneNascita, 
			CodiceFiscale codiceFiscale,
			String codiceGestore) 
	{
		super(nome, cognome, sesso, dataNascita, comuneNascita, nazioneNascita, codiceFiscale);
		this.codiceGestore = codiceGestore;
	}
}
