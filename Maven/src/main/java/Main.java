
public class Main {
	public static void main(String args[]) {
		Data d = new Data(25,11,2000);
		
		//giorno
		System.out.println(d.compareTo(new Data(23, 11, 2000)));
		System.out.println(d.compareTo(new Data(29, 11, 2000)));
		System.out.println(d.compareTo(new Data(25, 11, 2000)));
		
		///mese
		System.out.println(d.compareTo(new Data(23, 12, 2000)));
		System.out.println(d.compareTo(new Data(23, 1, 2000)));
		
		//anno
		System.out.println(d.compareTo(new Data(23, 11, 2001)));
		System.out.println(d.compareTo(new Data(23, 11, 1950)));
		
		
		System.out.println(CodiceFiscale.nomeToCodice("Luca"));
		System.out.println(CodiceFiscale.nomeToCodice("Rompani"));
		System.out.println(CodiceFiscale.nomeToCodice("Re"));
		System.out.println(CodiceFiscale.nomeToCodice("oriali"));
		System.out.println(CodiceFiscale.nomeToCodice("tommaso"));
		System.out.println(CodiceFiscale.nomeToCodice("romano"));
		
		try {
			CodiceFiscale c = new CodiceFiscale("RMPLCUDSDN".toCharArray());			
		} catch (IllegalArgumentException e) {
			System.out.println("ok");
		}
		
		try {
			CodiceFiscale h = new CodiceFiscale("RMPLC#00S30E507G".toCharArray());
		} catch (IllegalArgumentException e) {
			System.out.println("ok");
		}
		
		try {
			CodiceFiscale e = new CodiceFiscale("2MPLCU00S30E507G".toCharArray());
		} catch (IllegalArgumentException e) {
			System.out.println("ok");
		}
		
		try {
			CodiceFiscale f = new CodiceFiscale("RMPLCU43S32E507G".toCharArray());
		}catch (IllegalArgumentException e) {
			System.out.println("ok");
		}
		
		try {
			CodiceFiscale g = new CodiceFiscale("RMPLCU00X30E507G".toCharArray());
		} catch (IllegalArgumentException e) {
			System.out.println("ok");
		}
		
		try {
			CodiceFiscale h = new CodiceFiscale("RMPLCU00S32E507G".toCharArray());
		} catch (IllegalArgumentException e) {
			System.out.println("ok");
		}
		
		try {
			CodiceFiscale l = new CodiceFiscale("RMPLCU00S30@507G".toCharArray());
		}catch (IllegalArgumentException e) {
			System.out.println("ok");
		}
		
		try {
			CodiceFiscale h = new CodiceFiscale("RMPLCU00S30EW07G".toCharArray());
		} catch (IllegalArgumentException e) {
			System.out.println("ok");
		}
		
		try {
			CodiceFiscale h = new CodiceFiscale("RMPLCU00S30E5F7G".toCharArray());
		} catch (IllegalArgumentException e) {
			System.out.println("ok");
		}
		
		try {
			CodiceFiscale h = new CodiceFiscale("RMPLCU00S30E50[G".toCharArray());
		} catch (IllegalArgumentException e) {
			System.out.println("ok");
		}
		
		CodiceFiscale c = new CodiceFiscale("RMPLCU00S30E507G".toCharArray());
		CodiceFiscale tom = new CodiceFiscale("RMNTMM00R03E507G".toCharArray());
		System.out.println(c.isValid("Luca", "Rompani", new Data(30,11,2000), null));
		System.out.println(tom.isValid("Tommaso", "Romano", new Data(3, 10, 2000), null));
	}
}
