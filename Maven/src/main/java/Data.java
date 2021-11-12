
public class Data {
	
	private int giorno;
	private int mese;
	private int anno;
	
	public Data(int giorno, int mese, int anno) {
		
		//to-do: 	controllare non negativo
		//			controllare mese < 12
		//			controllare giorno < 31 e relativo a mese
		//			throw error
		this.giorno = giorno;
		this.mese = mese;
		this.anno = anno;
		
	}
 }
