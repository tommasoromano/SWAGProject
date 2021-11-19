
public class Main {
	public static void main(String args[]) {
		CodiceFiscale c = new CodiceFiscale("RMPLCU00S30E507G".toCharArray());
		System.out.println(c.isValid("Luca", "Rompani", new Data(30,11,2000), new Nazione("ITA"), new Comune("Mandello del Lario")));
		
	}
}
