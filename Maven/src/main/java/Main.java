
public class Main {
	public static void main(String args[]) {
		CodiceFiscale l = new CodiceFiscale("RMPLCU00S30E507G".toCharArray());
		System.out.println(l.isValid("Luca", "Rompani", new Data(30,11,2000), new Nazione("ITA"), new Comune("Lecco"), Sesso.M));
		
		CodiceFiscale t = new CodiceFiscale("RMNTMS00R03F205E".toCharArray());
		System.out.println(t.isValid("Tommaso", "Romano", new Data(3,10,2000), new Nazione("ITA"), new Comune("Milano"), Sesso.M));
	}
}
