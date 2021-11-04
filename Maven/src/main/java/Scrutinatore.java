
public class Scrutinatore extends Utente {

	//@ public invariant codiceGestore != null;
	private /*@ spec_public @*/ String codiceGestore;
	
	//@ requires codiceGestore != null;
	public Scrutinatore(String nome, String cognome, String codifeFiscale, Documento documentoIdentificativo,
			String codiceGestore) 
	{
		super(nome, cognome, codifeFiscale, documentoIdentificativo);
		this.codiceGestore = codiceGestore;
	}
}
