
public abstract class Utente {
	
	//@ public invariant nome != null;
	private /*@ spec_public @*/ String nome;
	
	//@ public invariant cognome != null;
	private /*@ spec_public @*/ String cognome;
	
	//@ public invariant codiceFiscale != null;
	private /*@ spec_public @*/ String codiceFiscale;
	
	//@ public invariant documentoIdentificativo != null;
	private /*@ spec_public @*/ Documento documentoIdentificativo;
	
	//@ requires nome != null && cognome != null && codiceFiscale != null && documentoIdentificativo != null;
	public Utente(String nome, String cognome, String codifeFiscale, Documento documentoIdentificativo) 
	{
		this.nome = nome;
		this.cognome = cognome;
		this.codiceFiscale = codiceFiscale;
		this.documentoIdentificativo = documentoIdentificativo;
	}
	
}
