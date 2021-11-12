
public class Elettore extends Utente {

	private boolean voto;
	
	//@ public invariant tesseraEletorale != null;
	private /*@ spec_public @*/ String tesseraElettorale;
	
	//@ requires tesseraElettorale != null;
	public Elettore(String nome, String cognome, String codiceFiscale, Documento documentoIdentificativo,
			String tesseraElettorale) 
	{
		super(nome, cognome, codiceFiscale, documentoIdentificativo);
		this.tesseraElettorale = tesseraElettorale;
	}
	
	public void EsprimiVoto() {
		
	}

}
