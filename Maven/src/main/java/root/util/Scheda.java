package root.util;

public class Scheda {
	
	private int id;
	private String nome;
	private Data inizio;
	private Data fine;
	private ModalitaVoto modVoto;
	private DatiVoto datiVoto;
	private ModalitaConteggio modConteggio;
	private boolean scrutinata;
	
	public Scheda(int id, String nome, Data inizio, Data fine, ModalitaVoto modVoto, DatiVoto datiVoto, ModalitaConteggio modConteggio, boolean scrutinata) {
		this.id = id;
		this.nome = nome;
		this.inizio = inizio;
		this.fine = fine;
		this.modVoto = modVoto;
		this.modConteggio = modConteggio;
		this.datiVoto = datiVoto;
		this.scrutinata = scrutinata;
	}
	
	/**
	 * less than zero se futura, equals zero se in progress, more than zero se conclusa
	 * @return
	 */
	public int timeState() {
		if (fine.compareTo(Data.getTodayData()) < 0) {
			// scheda conclusa
			return 1;
		} else {
			if (inizio.compareTo(Data.getTodayData()) > 0) {
				// scheda futura
				return -1;
			} else {
				// scheda in progresso
				return 0;
			}
		}
		
	}
	
	public int getId() { return this.id; }
	public String getNome() { return this.nome; };
	public Data getInizio() { return inizio; };
	public Data getFine() { return fine; }	
	public ModalitaVoto getTipoVoto() { return this.modVoto; }
	public DatiVoto getDatiVoto() { return this.datiVoto; }
	public ModalitaConteggio getTipoVincitore() { return this.modConteggio; }
	public boolean getScrutinata() { return this.scrutinata; }
	
	
	@Override
	public String toString() {
		return this.id+":"
				+this.nome+":"
				+this.inizio.toString()+":"
				+this.fine.toString()+":"
				+this.modVoto.toString()+":"
				+this.datiVoto+":"
				+this.modConteggio.toString()+":"
				+this.scrutinata;
	}

}
