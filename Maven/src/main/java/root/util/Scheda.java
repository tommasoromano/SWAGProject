package root.util;

public class Scheda {
	
	private String nome;
	private Data inizio;
	private Data fine;
	private String tipoVoto;
	private String datiVoto;
	private String tipoVincitore;
	
	public Scheda(String nome, Data inizio, Data fine, String tipoVoto, String datiVoto, String tipoVincitore) {
		this.nome = nome;
		this.inizio = inizio;
		this.fine = fine;
		this.tipoVoto = tipoVoto;
		this.tipoVincitore = tipoVincitore;
		this.datiVoto = datiVoto;
	}
	
	@Override
	public String toString() {
		return this.nome+":"
				+inizio.toString()+":"
				+fine.toString()+":"
				+tipoVoto+":"
				+datiVoto+":"
				+tipoVincitore;
	}

}
