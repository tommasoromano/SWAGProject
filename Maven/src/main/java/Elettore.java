
public class Elettore extends Utente {

	private String tesseraElettorale;
	
	public Elettore(String nome, String cognome, String codifeFiscale, Documento documentoIdentificativo,
			String tesseraElettorale) 
	{
		super(nome, cognome, codifeFiscale, documentoIdentificativo);
		this.tesseraElettorale = tesseraElettorale;
	}

}
