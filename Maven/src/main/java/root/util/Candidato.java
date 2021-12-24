package root.util;

public class Candidato implements Votabile {

	private String nome;
	private String cognome;
	private int voti;
	private int posizione;
	
	public Candidato(String nome, String cognome) {
		this.nome = nome;
		this.cognome = cognome;
		voti = 0;
	}
	
	public void esprimiVotoOrdinale(int posizione) {
		if (posizione < 0) throw new IllegalArgumentException();
		this.posizione = posizione;
	}
	
	public void esprimiVotoCategorico() {
		voti+=1;
	}
	
	@Override
	public String toString() {
		return nome + " " + cognome;
	}
	
	public int getVoti() {
		return voti;
	}
	
	public int getPosizione() {
		return posizione;
	}
}
