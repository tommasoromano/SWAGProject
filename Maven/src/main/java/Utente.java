
public abstract class Utente {
	
	//@ public invariant nome != null;
	private /*@ spec_public @*/ String nome;
	
	//@ public invariant cognome != null;
	private /*@ spec_public @*/ String cognome;

	private Sesso sesso;
	
	//@ public invariant dataNascita != null;
	private /*@ spec_public @*/ Data dataNascite;
	
	//@ public invariant nazioneNascita.getIso3.equals("ITA") ==> comuneNascita != null
	private /*@ spec_public @*/ Comune comuneNascita;
	
	//@ public invariant nazioneNascita != null;
	private /*@ spec_public @*/ Nazione nazioneNascita;
	
	//@ public invariant codiceFiscale != null;
	private /*@ spec_public @*/ CodiceFiscale codiceFiscale;
	
	/*@ requires nome != null && cognome != null && dataNascita != null && 
	@ nazioneNascita != null && codiceFiscale != null
	@*/
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
	
	public /* @ pure @ */ Data getDataNascita() {
		return this.dataNascite;
	}
	
}
