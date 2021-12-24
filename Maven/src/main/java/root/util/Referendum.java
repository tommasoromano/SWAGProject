package root.util;

public class Referendum implements Votabile {
	private String domanda;
	private int si;
	private int no;
	
	public Referendum(String s) {
		if (s == null) throw new IllegalArgumentException("Stringa nulla");
		domanda = s;
		si = 0;
		no = 0;
	}
	
	public void esprimiVoto(boolean res) {
		if (res) si += 1;
		else no += 1;
	}
	
	public int getSi()  {
		return si;
	}
	
	public int getNo() {
		return no;
	}
	
	@Override
	public String toString() {
		return domanda;
	}
}
