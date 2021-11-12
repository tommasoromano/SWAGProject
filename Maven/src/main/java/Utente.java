
public abstract class Utente {
	
	//@ public invariant nome != null;
	private /*@ spec_public @*/ String nome;
	
	//@ public invariant cognome != null;
	private /*@ spec_public @*/ String cognome;
	
	private Data dataNascite;
	
	private Comune comuneNascita;
	
	private Nazione nazioneNascita;
	
	private Sesso sesso;
	
	//@ public invariant codiceFiscale != null;
	private /*@ spec_public @*/ CodiceFiscale codiceFiscale;
	
	//@ requires nome != null && cognome != null && codiceFiscale != null
	public Utente(String nome, String cognome, CodiceFiscale codifeFiscale) 
	{
		this.nome = nome;
		this.cognome = cognome;
		this.codiceFiscale = codiceFiscale;
	}
	
}
