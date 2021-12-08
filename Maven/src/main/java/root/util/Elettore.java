package root.util;
public class Elettore extends Utente {

	private /*@ spec_public @*/ boolean voto;
	
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
	
	//@ requires getDataNascita().isMaggiorenne();
	//@ requires \old(voto) == false;
	//@ ensures voto == true;
	public void esprimiVoto() {
		
		// controllo sia maggiorenne
		if (this.getDataNascita().isMaggiorenne()) return;
		
		// controllo se ha gia votato
		if (this.voto) return;
		
		// azione di voto
		this.voto = true;
	}

}
