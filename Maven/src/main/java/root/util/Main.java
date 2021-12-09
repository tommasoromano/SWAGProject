package root.util;
import root.controller.DBController;

public class Main {
	public static void main(String args[]) {
		CodiceFiscale l = new CodiceFiscale("RMPLCU00S30E507G".toCharArray());
		System.out.println(l.isValid("Luca", "Rompani", new Data(30,11,2000), new Nazione("ITA"), new Comune("Lecco"), Sesso.M));
		
		CodiceFiscale t = new CodiceFiscale("RMNTMS00R03F205E".toCharArray());
		System.out.println(t.isValid("Tommaso", "Romano", new Data(3,10,2000), new Nazione("ITA"), new Comune("Milano"), Sesso.M));
	
		CodiceFiscale m = new CodiceFiscale("MLLLNN00P70Z126B".toCharArray());
		System.out.println(m.isValid("Lucia Anna", "Mellini", new Data(30,9,2000), new Nazione("NLD"), new Comune("Milano"), Sesso.F));
		
		try {
			DBController d = DBController.getInstance();
			d.registerElettore("luca@gmail.com", "12345", "luca", "rompani", new Data(30,11,2000), "Lecco", l, "0000");
			System.out.println(d.getPsw("luca"));
		} catch (Exception e) { 
			throw new IllegalArgumentException(e.getMessage());
		}
	}
}
