package root.util;

public class DatiVoto {

	private String [] candidati;
	
	public DatiVoto(String data) {
		this.candidati = data.split(":");
	}
	
	public String [] getCandidati() {
		return candidati;
	}
	
	@Override
	public String toString() { return this.candidati.toString(); }

}
