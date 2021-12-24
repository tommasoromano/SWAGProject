package root.util;

public class DatiVoto {

	/**
	 * c = candidati/gruppi/partiti
	 * p = preferenze (candidati di gruppi/partiti)
	 * Formato dati voto in base a modalita:
	 * - voto ordinale e categorico c1:c2:c3:c4 ...
	 * - voto categorico con preferenze c1(p1:p2):c2(p3:p4) ...
	 * - referendum domanda
	 * 
	 * Quando esprimo un voto lo scrivo come
	 * - voto ordinale cx(n):cx+1(n+1) ... 
	 * - voto categorico cx
	 * - voto categorico con preferenze cx(pn:pn+1)
	 * - referendum si/no
	 */
	private String data;
	
	private String [] candidati;
	private String [] preferenze;
	private String domanda;
	
	public DatiVoto(String data) {
		
		
		this.candidati = data.split(":");
	}
	
	public String [] getCandidati() {
		return candidati;
	}
	
	@Override
	public String toString() { return this.candidati.toString(); }

}
