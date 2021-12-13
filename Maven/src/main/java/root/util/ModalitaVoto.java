package root.util;

public class ModalitaVoto {

	public enum Tipo {
		VotoOrdinale,
		VotoCategorico,
		VotoCategoricoConPreferenze,
		Referendum
	}
	
	private Tipo tipo;
	
	public ModalitaVoto(String s) {
		switch(s) {
			case "Voto ordinale":
				this.tipo = Tipo.VotoOrdinale;
				break;
			case "Voto categorico":
				this.tipo = Tipo.VotoCategorico;
				break;
			case "Voto categorico con preferenze":
				this.tipo = Tipo.VotoCategoricoConPreferenze;
				break;
			case "Referendum":
				this.tipo = Tipo.Referendum;
				break;
			default:
				this.tipo = null;
				break;
		}
	}
	
	public Tipo getTipo() { return this.tipo; }
	
	public static String[] getAllTipi() {
		String[] s = new String[4];
		s[0] = "Voto ordinale";
		s[1] = "Voto categorico";
		s[2] = "Voto categorico con preferenze";
		s[3] = "Referendum";
		return s;
	}
	
	@Override
	public String toString() { 
		String s = "";
		switch(this.tipo) {
			case VotoOrdinale:
				s = "Voto ordinale";
				break;
			case VotoCategorico:
				s = "Voto categorico";
				break;
			case VotoCategoricoConPreferenze:
				s = "Voto categorico con preferenze";
				break;
			case Referendum:
				s = "Referendum";
				break;
			default:
				s = "";
				break;
		}
		return s; 
	}

}
