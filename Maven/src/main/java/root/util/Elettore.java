package root.util;

public class Elettore {

	//@ public invariant nome != null;
	private /*@ spec_public @*/ String nome;
	
	//@ public invariant cognome != null;
	private /*@ spec_public @*/ String cognome;

	private Sesso sesso;
	
	//@ public invariant dataNascita != null;
	private /*@ spec_public @*/ Data dataNascite;
	
	//@ public invariant nazioneNascita != null
	private /*@ spec_public @*/ String luogoNascita;
	
	//@ public invariant codiceFiscale != null;
	private /*@ spec_public @*/ CodiceFiscale codiceFiscale;
	
	//@ public invariant tesseraEletorale != null;
	private /*@ spec_public @*/ String tesseraElettorale;
	
	/*@ requires nome != null && cognome != null && dataNascita != null && 
	@ nazioneNascita != null && codiceFiscale != null && tesseraElettorale != null;
	@*/
	public Elettore(String nome, String cognome, Sesso sesso, 
			Data dataNascita, String luogoNascita, 
			CodiceFiscale codiceFiscale, 
			String tesseraElettorale) 
	{
		if (nome == null || cognome == null || 
				sesso == null || dataNascita == null || 
				luogoNascita == null || tesseraElettorale == null) throw new IllegalArgumentException("Parametro nullo"); 
		this.nome = nome.substring(0,1).toUpperCase() + nome.substring(1);
		this.cognome = cognome.substring(0,1).toUpperCase() + cognome.substring(1);
		this.sesso = sesso;
		this.dataNascite = dataNascita;
		this.luogoNascita = luogoNascita;
		this.codiceFiscale = codiceFiscale;
		this.tesseraElettorale = tesseraElettorale;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public String getCognome() {
		return this.cognome;
	}
	
	public CodiceFiscale getCF() {
		return this.codiceFiscale;
	}

}
