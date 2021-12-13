package root.util;
import java.time.LocalDate;
import java.time.LocalDateTime; 

public class Data implements Comparable<Data> {
	
	//@ ensures (giorno > 0 && giorno <= 31);
	private /*@ spec_public @*/ final int giorno;
	//@ ensures (mese > 0 && mese <= 12);
	private /*@ spec_public @*/ final int mese;
	//@ ensures (anno > 1800);
	private /*@ spec_public @*/ final int anno;
	
	//@ requires (giorno <= 31 && giorno >=1);
	//@ requires (mese <= 12 && mese >= 1);
	//@ requires (anno >= 1800);
	//@ requires (compareTo(giorno, mese, anno, getTodayData()) < 0);
	public Data(int giorno, int mese, int anno) {
		
		isValid(giorno, mese, anno);
	
		this.giorno = giorno;
		this.mese = mese;
		this.anno = anno;
		
		/*if (this.compareTo(giorno, mese, anno, getTodayData()) > 0) {
			throw new IllegalArgumentException("Data successiva a data corrente");
		}*/
		
	}
	
	public Data(String data) {
		String[] s = data.split("/");
		if (s.length != 3) throw new IllegalArgumentException("Data non valida: " + data);
		
		int g = Integer.parseInt(s[0]);
		int m = Integer.parseInt(s[1]);
		int a = Integer.parseInt(s[2]);
		
		isValid(g, m, a);
		
		this.giorno = g;
		this.mese = m;
		this.anno = a;
		
		/*if (this.compareTo(g, m, a, getTodayData()) > 0) {
			throw new IllegalArgumentException("Data successiva a data corrente");
		}*/
	}
	
	private void isValid(int giorno, int mese, int anno) {
		
		if (anno < 1800) throw new IllegalArgumentException("Anno non valido");
		if (mese < 1 || mese > 12) throw new IllegalArgumentException("Mese non valido");
		if ( (mese == 1 || mese == 3 || mese == 5 || mese == 7 || mese == 8 || mese == 10 || mese == 12 ) && giorno > 31) throw new IllegalArgumentException("Giorno non valido per mese " + mese); 
		if ( mese == 2 && giorno > 29) throw new IllegalArgumentException("Giorno non valido per mese " + mese);
		if ( (mese == 4 || mese == 6 || mese == 9 || mese == 11) && giorno > 30) throw new IllegalArgumentException("Giorno non valido per mese "+ mese);
		
	}
	
	public int getAnno() {
		return anno;
	}
	
	public int getMese() {
		return mese;
	}
	
	public int getGiorno() {
		return giorno;
	}
	
	/**
	 * return less than zero if this < d, more than zero if this > d, equals zero
	 * @param d
	 */
	public /* @ pure @ */ int compareTo(Data d) {
		if (Integer.compare(this.anno, d.anno) != 0) return Integer.compare(this.anno, d.anno);	
		if (Integer.compare(this.mese, d.mese) != 0) return Integer.compare(this.mese, d.mese);
		
		return Integer.compare(this.giorno, d.giorno);
	}
	
	private /* @ pure @ */ int compareTo(int g, int m, int a, int[] d) {
		if (Integer.compare(a, d[2]) != 0) return Integer.compare(a, d[2]);	
		if (Integer.compare(m, d[1]) != 0) return Integer.compare(m, d[1]);
		
		return Integer.compare(g, d[0]);
	}
	
	public /* @ pure @ */ boolean isMaggiorenne() {
		Data today = getTodayData();
		int y = today.getAnno() - this.anno;
		
		if (y > 18) {			
			return true;			
		} else if (y == 18) {
			int m = today.getMese() - this.mese;
			if (m > 0) {
				return true;
			} else if (m == 0) {
				int g = today.getGiorno() - this.giorno;
				if (g >= 0) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
			
		} else {			
			return false;			
		}
	}
	
	public static Data getTodayData() {
		LocalDate today = LocalDate.now();
		return new Data(
				today.getDayOfMonth(), 
				today.getMonthValue(), 
				today.getYear()
				);
	}
	
	@Override 
	public String toString() {
		return giorno + "/" + mese + "/" + anno;
	}
	
 }
