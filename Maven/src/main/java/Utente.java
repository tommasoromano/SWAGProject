
public abstract class Utente {
	
	//@ public invariant nome != null;
	private /*@ spec_public @*/ String nome;
	
	//@ public invariant cognome != null;
	private /*@ spec_public @*/ String cognome;

	private Sesso sesso;
	
	private Data dataNascite;
	
	private Comune comuneNascita;
	
	private Nazione nazioneNascita;
	
	//@ public invariant codiceFiscale != null;
	private /*@ spec_public @*/ CodiceFiscale codiceFiscale;
	
	//@ requires nome != null && cognome != null && codiceFiscale != null
	public Utente(String nome, String cognome, Sesso sesso, 
			Data dataNascita, Comune comuneNascita, Nazione nazioneNascita, 
			CodiceFiscale codiceFiscale) 
	{
		this.nome = nome;
		this.cognome = cognome;
		this.sesso = sesso;
		this.dataNascite = dataNascita;
		this.comuneNascita = comuneNascita;
		this.nazioneNascita = nazioneNascita;
		this.codiceFiscale = codiceFiscale;
	}
	
}
