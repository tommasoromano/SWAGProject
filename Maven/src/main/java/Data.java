
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
	public Data(int giorno, int mese, int anno) {
		
		if (anno < 1800) throw new IllegalArgumentException("Anno non valido");
		if (mese < 1 || mese > 12) throw new IllegalArgumentException("Mese non valido");
		if ( (mese == 1 || mese == 3 || mese == 5 || mese == 7 || mese == 8 || mese == 10 || mese == 12 ) && giorno > 31) throw new IllegalArgumentException("Giorno non valido per mese " + mese); 
		if ( mese == 2 && giorno > 29) throw new IllegalArgumentException("Giorno non valido per mese " + mese);
		if ( (mese == 4 || mese == 6 || mese == 9 || mese == 11) && giorno > 30) throw new IllegalArgumentException("Giorno non valido per mese "+ mese);
	
		this.giorno = giorno;
		this.mese = mese;
		this.anno = anno;
		
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
	
	public int compareTo(Data d) {
		if (Integer.compare(this.anno, d.anno) != 0) return Integer.compare(this.anno, d.anno);	
		if (Integer.compare(this.mese, d.mese) != 0) return Integer.compare(this.mese, d.mese);
		
		return Integer.compare(this.giorno, d.giorno);
	}
	
 }
