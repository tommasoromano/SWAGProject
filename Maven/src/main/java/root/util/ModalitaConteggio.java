package root.util;

import root.util.ModalitaVoto.Tipo;

public class ModalitaConteggio {

	public enum Tipo {
		Maggioranza,
		MaggioranzaAssoluta,
		ReferendumSenzaQuorum,
		ReferendumConQuorum
	}
	
	private Tipo tipo;
	
	public ModalitaConteggio(String s) {
		switch(s) {
			case "Maggioranza":
				this.tipo = Tipo.Maggioranza;
				break;
			case "Maggioranza assoluta":
				this.tipo = Tipo.MaggioranzaAssoluta;
				break;
			case "Referendum senza quorum":
				this.tipo = Tipo.ReferendumSenzaQuorum;
				break;
			case "Referendum con quorum":
				this.tipo = Tipo.ReferendumConQuorum;
				break;
			default:
				this.tipo = null;
				break;
		}
	}
	
	public Tipo getTipo() { return this.tipo; }
	
	public static String[] getAllTipi() {
		String[] s = new String[4];
		s[0] = "Maggioranza";
		s[1] = "Maggioranza assoluta";
		s[2] = "Referendum senza quorum";
		s[3] = "Referendum con quorum";
		return s;
	}
	
	@Override
	public String toString() { 
		String s = "";
		switch(this.tipo) {
			case Maggioranza:
				s = "Maggioranza";
				break;
			case MaggioranzaAssoluta:
				s = "Maggioranza assoluta";
				break;
			case ReferendumSenzaQuorum:
				s = "Referendum senza quorum";
				break;
			case ReferendumConQuorum:
				s = "Referendum con quorum";
				break;
			default:
				s = "";
				break;
		}
		return s; 
	}

}
