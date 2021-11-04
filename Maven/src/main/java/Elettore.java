
public class Elettore extends Utente {

	//@ public invariant tesseraEletorale != null;
	private /*@ spec_public @*/ String tesseraElettorale;
	
	//@ requires tesseraElettorale != null;
	public Elettore(String nome, String cognome, String codiceFiscale, Documento documentoIdentificativo,
			String tesseraElettorale) 
	{
		super(nome, cognome, codiceFiscale, documentoIdentificativo);
		this.tesseraElettorale = tesseraElettorale;
	}

}
