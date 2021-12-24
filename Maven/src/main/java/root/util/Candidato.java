package root.util;

public class Candidato implements Votabile {

	private String nome;
	private String cognome;
	private int voti;
	
	public Candidato(String nome, String cognome) {
		this.nome = nome;
		this.cognome = cognome;
		voti = 0;
	}
	
	public void vota() {
		voti+=1;
	}
	
	@Override
	public String toString() {
		return nome + " " + cognome;
	}
}
