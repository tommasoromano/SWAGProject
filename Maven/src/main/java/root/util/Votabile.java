package root.util;

public interface Votabile {
	public void esprimiVotoOrdinale(int posizione);
	public void esprimiVotoCategorico();
	public void esprimiVotoCategoricoConPreferenze(Votabile v, Candidato ...c);
}	
