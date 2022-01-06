package root.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VotoEspresso implements Comparable<VotoEspresso> {
	
	public static VotoEspresso[] conteggioVoti(Scheda s, String []voti) {
		
		List<VotoEspresso> ve = new ArrayList<>();
		ve.add(new VotoEspresso(s, voti[0]));
		
		for (int i = 1; i < voti.length; i++) {
			
			for (int j = 0; j < ve.size(); j++) {
				if (ve.get(j).aggiungi(voti[i])) {
					break;
				} else {
					if (j+1 >= ve.size()) {
						ve.add(new VotoEspresso(s, voti[i]));
						break;
					}
				}
			}
		}
		
		return ve.toArray(new VotoEspresso[ve.size()]);
	}

	/**
	 * Quando esprimo un voto lo scrivo come
	 * - voto ordinale cx(n):cy(m) ... 
	 * - voto categorico cx
	 * - voto categorico con preferenze cx(pn)
	 * - referendum Si/No
	 * - scheda bianca = SB, scheda nulla = SN
	 */
	private String data;
	private Scheda scheda;
	
	private String votabile;
	private int conteggio;
	private Map<String, Integer> preferenze;
	
	public VotoEspresso(Scheda scheda, String data) {
		this.data = data;
		this.scheda = scheda;
		
		switch(scheda.getTipoVoto().getTipo()) {
		case VotoOrdinale:
			creaOrdinale();
			break;
		case VotoCategorico:
			creaCategorico();
			break;
		case VotoCategoricoConPreferenze:
			creaCategoricoPreferenze();
			break;
		case Referendum:
			creaReferendum();
			break;
	}
	}
	
	private void creaOrdinale() {
		if (this.data.equals("SB")) {
			this.votabile = this.data;
			this.conteggio = 1;
			return;
		} else if (this.data.equals("SN")) {
			this.votabile = this.data;
			this.conteggio = 1;
			return;
		}
		String[] vs = this.data.split(":");
		for (int i = 0; i < vs.length; i++) {
			if (vs[i].contains("1")) {
				this.votabile = vs[i].split("\\(")[0];
				this.conteggio = 1;
			}
		}
	}
	
	private void creaCategorico() {
		this.votabile = this.data;
		this.conteggio = 1;
	}
	private void creaCategoricoPreferenze() {
		if (this.data.equals("SB")) {
			this.votabile = this.data;
			this.conteggio = 1;
			return;
		} else if (this.data.equals("SN")) {
			this.votabile = this.data;
			this.conteggio = 1;
			return;
		}
		if (this.data.contains("(")) {
			this.votabile = this.data.split("\\(")[0];
			this.conteggio = 1;
			preferenze = new HashMap<>();
			preferenze.put(this.data.split("\\(")[1].split("\\)")[0], 1);
		} else {
			this.votabile = this.data;
			this.conteggio = 1;
		}
	}
	private void creaReferendum() {
		this.votabile = this.data;
		this.conteggio = 1;
	}
	
	/**
	 * Confronta con voto: se stesso votabile, aggiunge e true, altrimenti false
	 * @param voto
	 * @return
	 */
	public boolean aggiungi(String voto) {
		switch(this.scheda.getTipoVoto().getTipo()) {
		case VotoOrdinale:
			return aggiungiOrdinale(voto);
		case VotoCategorico:
			return aggiungiCategorico(voto);
		case VotoCategoricoConPreferenze:
			return aggiungiCategoricoPreferenze(voto);
		case Referendum:
			return aggiungiReferendum(voto);
		}
		return false;
	}	
		
	private boolean aggiungiOrdinale(String voto) {
		if (this.votabile.equals(voto)) {
			return true;
		} else {
			String[] vs = voto.split(":");
			for (int i = 0; i < vs.length; i++) {
				if (vs[i].contains("1")) {
					if (this.votabile.equals(vs[i].split("\\(")[0])) {
						this.conteggio++;
						return true;
					}
				}
			}
		}
		return false;
	}
	private boolean aggiungiCategorico(String voto) {
		if (this.votabile.equals(voto)) {
			this.conteggio++;
			return true;
		}
		return false;
	}
	private boolean aggiungiCategoricoPreferenze(String voto) {
		if (this.votabile.equals(voto)) {
			this.conteggio++;
			return true;
		} else {
			if (this.data.contains("(")) {
				if (this.votabile.equals(voto.split("\\(")[0])) {
					this.conteggio++;
					String pref = voto.split("\\(")[1].split("\\)")[0];
					if (this.preferenze.containsKey(pref)) {
						this.preferenze.put(pref, this.preferenze.get(pref) + 1);
					} else {
						this.preferenze.put(pref, 1);
					}
					return true;
				}
			}
		}
		return false;
	}
	private boolean aggiungiReferendum(String voto) {
		if (this.votabile.equals(voto)) {
			this.conteggio++;
			return true;
		}
		return false;
	}
	
	public static VotoEspresso getVotabile(String votabile, VotoEspresso[] voti) {
		for (int i = 0; i < voti.length; i++) {
			if (votabile.toLowerCase().equals(voti[i].getVotabile().toLowerCase())) {
				return voti[i];
			}
		}
		return null;
	}
	
	public String getVotabile() { return this.votabile; }
	public int getConteggio() { return this.conteggio; }
	public Map<String, Integer> getPreferenze() { return this.preferenze; }

	@Override
	  public int compareTo(VotoEspresso ve) {
	    return ((Integer)this.getConteggio()).compareTo(((Integer)ve.getConteggio()));
	  }
}
