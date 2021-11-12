
public class Scrutinatore extends Utente {

	//@ public invariant codiceGestore != null;
	private /*@ spec_public @*/ String codiceGestore;
	
	//@ requires codiceGestore != null;
	public Scrutinatore(String nome, String cognome, String codifeFiscale,
			String codiceGestore) 
	{
		super(nome, cognome, codifeFiscale);
		this.codiceGestore = codiceGestore;
	}
}
