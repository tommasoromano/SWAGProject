package root.util;

import java.util.ArrayList;
import java.util.List;

public class DatiVoto {

	/**
	 * Per creazione voto:
	 * c = candidati/gruppi/partiti
	 * p = preferenze (candidati di gruppi/partiti)
	 * Formato dati voto in base a modalita:
	 * - voto ordinale e categorico c1:c2:c3:c4 ...
	 * - voto categorico con preferenze c1(p1:p2):c2(p3:p4) ...
	 * - referendum domanda
	 */
	private String dati;
	
	/**
	 * Devono essere nella forma sopra indicata
	 * @param dati
	 */
	public DatiVoto(String dati) {
		this.dati = dati;
	}
	
	/**
	 * Ritorna l'elenco dei candidati, per creazione voto
	 * @return
	 */
	public String [] getCandidati() {
		if (this.dati.contains("(")) {
			// con preferenze
			String[] all = this.dati.split(":");
			List<String> candidati = new ArrayList<>();
			for (int i = 0, j = 0; i < all.length; i++) {
				// candidto1(preferenza1:preferenza2):candidato2 ...
				if (all[i].contains("(")) {
					candidati.add(all[i].split("\\(")[0]);
				}
			}
			String[] c = new String[candidati.size()];
			candidati.toArray(c);
			return c;
		} else {
			return this.dati.split(":");
		}
	}
	
	/**
	 * Ritorna l'elenco delle preferenze, per creazione voto
	 * @param candidato
	 * @return
	 */
	public String [] getPreferenze(String candidato) {
		if (this.dati.contains("(")) {
			String[] all = this.dati.split(":");
			List<String> pref = new ArrayList<>();
			boolean p = false;
			for (int i = 0, j = 0; i < all.length; i++) {
				// candidto1(preferenza1:preferenza2):candidato2 ...
				if (all[i].contains("(")) {
					if (all[i].split("\\(")[0].equals(candidato)) {
						pref.add(all[i].split("\\(")[1]);
						p = true;
					}
				} else {
					if (p) {
						if (all[i].contains(")")) {
							pref.add(all[i].split("\\)")[0]);
							p = false;
						} else {
							pref.add(all[i]);
						}
					}
				}
				
			}
			String[] c = new String[pref.size()];
			pref.toArray(c);
			return c;
		} else {
			return null;
		}
	}
	
	public String getDomanda() {
		return this.dati;
	}
	
	@Override
	public String toString() { return this.dati; }

}
