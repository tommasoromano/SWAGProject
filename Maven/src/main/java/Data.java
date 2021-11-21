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
	//@ requires (compareTo(getTodayData()) > 0);
	public Data(int giorno, int mese, int anno) {
		
		if (anno < 1800) throw new IllegalArgumentException("Anno non valido");
		if (mese < 1 || mese > 12) throw new IllegalArgumentException("Mese non valido");
		if ( (mese == 1 || mese == 3 || mese == 5 || mese == 7 || mese == 8 || mese == 10 || mese == 12 ) && giorno > 31) throw new IllegalArgumentException("Giorno non valido per mese " + mese); 
		if ( mese == 2 && giorno > 29) throw new IllegalArgumentException("Giorno non valido per mese " + mese);
		if ( (mese == 4 || mese == 6 || mese == 9 || mese == 11) && giorno > 30) throw new IllegalArgumentException("Giorno non valido per mese "+ mese);
	
		this.giorno = giorno;
		this.mese = mese;
		this.anno = anno;
		
		int[] today = getTodayData();
		if (compareTo(today[0], today[1], today[2], giorno, mese, anno) < 0) {
			throw new IllegalArgumentException("Data successiva a data corrente");
		}
		
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
	
	public /* @ pure @ */ int compareTo(Data d) {
		if (Integer.compare(this.anno, d.anno) != 0) return Integer.compare(this.anno, d.anno);	
		if (Integer.compare(this.mese, d.mese) != 0) return Integer.compare(this.mese, d.mese);
		
		return Integer.compare(this.giorno, d.giorno);
	}
	
	public /* @ pure @ */ int compareTo(int g, int m, int a) {
		if (Integer.compare(this.anno, a) != 0) return Integer.compare(this.anno, a);	
		if (Integer.compare(this.mese, m) != 0) return Integer.compare(this.mese, m);
		
		return Integer.compare(this.giorno, g);
	}
	
	public /* @ pure @ */ int compareTo(int g0, int m0, int a0, int g1, int m1, int a1) {
		if (Integer.compare(a0, a1) != 0) return Integer.compare(a0, a1);	
		if (Integer.compare(m0, m1) != 0) return Integer.compare(m0, m1);
		
		return Integer.compare(g0, g1);
	}
	
	
	public /* @ pure @ */ boolean isMaggiorenne() {
		int[] today = getTodayData();
		int y = today[2] - this.anno;
		
		if (y > 18) {			
			return true;			
		} else if (y == 18) {
			int m = today[1] - this.mese;
			if (m > 0) {
				return true;
			} else if (m == 0) {
				int g = today[0] - this.giorno;
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
	
	public /* @ pure @ */ static int[] getTodayData() {
		return new int[] {20, 11, 2021};
	}
	
 }
