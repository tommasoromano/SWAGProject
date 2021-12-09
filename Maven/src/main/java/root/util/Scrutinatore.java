package root.util;

public class Scrutinatore {

	//@ public invariant email != null;
	private /*@ spec_public @*/ String email;
		
	//@ public invariant codice != null;
	private /*@ spec_public @*/ String codice;
	
	//@ requires email != null && codice != null;
	public Scrutinatore(String email, String codice) 
	{
		this.email = email;
		this.codice = codice;
	}
}
