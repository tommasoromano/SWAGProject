package root.util;

public class Scrutinatore {

	//@ public invariant email != null;
	private /*@ spec_public @*/ String email;
	
	//@ requires email != null;
	public Scrutinatore(String email) 
	{
		this.email = email;
	}
	
	public String getEmail() {
		return this.email;
	}
}
