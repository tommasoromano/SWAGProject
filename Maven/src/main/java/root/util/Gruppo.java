package root.util;

public class Gruppo implements Votabile {
	
	private String nome;
	private Candidato[] candidati;
	private int voti;
	private int posizione;
	
	public Gruppo(String data, String nome) {
		if (data == null || !data.contains(":") || nome == null) throw new IllegalArgumentException();
		this.nome = nome;
		voti = 0;
		posizione = -1;
		String [] tmp = data.split(":");
		candidati = new Candidato [tmp.length];
		for (int i=0; i<tmp.length; i++) {
			String [] split = tmp[i].split(" ");
			candidati[i] = new Candidato(split[0], split[1]);
		}
	}
	
	public void esprimiVotoOrdinale(int posizione) {
		if (posizione < 0) throw new IllegalArgumentException("Posizione non valida");
		this.posizione = posizione;
	}
	
	public void esprimiVotoCategorico() {
		voti += 1;
	}
	
	public void esprimiVotoCategoricoConPreferenze(Votabile v, Candidato ...c) {
		for (Candidato x : c) {
			x.esprimiVotoCategorico();
		}
		voti += 1;
	}
	
	@Override 
	public String toString() {
		return nome;
	}
	
	public int getVoti() {
		return voti;
	}
	
	public int getPosizione() {
		return posizione;
	}
	
}
