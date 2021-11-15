

public class Documento {

	public enum Tipo {
		CartaIdentita,
		Passaporto
	}
	
	private Tipo tipo;
	private String id;
	
	public Documento(Tipo tipo, String id) {
	
		this.tipo = tipo;
		this.id = id;
		
	}

}
