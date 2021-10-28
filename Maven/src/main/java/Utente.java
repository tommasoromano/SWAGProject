
public abstract class Utente {
	
	private String nome;
	private String cognome;
	private String codiceFiscale;
	private Documento documentoIdentificativo;
	
	public Utente(String nome, String cognome, String codifeFiscale, Documento documentoIdentificativo) 
	{
		this.nome = nome;
		this.cognome = cognome;
		this.codiceFiscale = codiceFiscale;
		this.documentoIdentificativo = documentoIdentificativo;
	}
	
}
