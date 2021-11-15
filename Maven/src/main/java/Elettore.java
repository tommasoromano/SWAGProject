
public class Elettore extends Utente {

	private boolean voto;
	
	//@ public invariant tesseraEletorale != null;
	private /*@ spec_public @*/ String tesseraElettorale;
	
	//@ requires tesseraElettorale != null;
	public Elettore(String nome, String cognome, Sesso sesso, 
			Data dataNascita, Comune comuneNascita, Nazione nazioneNascita, 
			CodiceFiscale codiceFiscale, 
			String tesseraElettorale) 
	{
		super(nome, cognome, sesso, dataNascita, comuneNascita, nazioneNascita, codiceFiscale);
		this.tesseraElettorale = tesseraElettorale;
	}
	
	public void EsprimiVoto() {
		
	}

}
