
public class Scrutinatore extends Utente {

	private String codiceGestore;
	
	public Scrutinatore(String nome, String cognome, String codifeFiscale, Documento documentoIdentificativo,
			String codiceGestore) 
	{
		super(nome, cognome, codifeFiscale, documentoIdentificativo);
		this.codiceGestore = codiceGestore;
	}
}
