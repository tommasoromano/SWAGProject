
public class Main {
	public static void main(String args[]) {
		CodiceFiscale c = new CodiceFiscale("RMNTMS00R03F205E".toCharArray());
		System.out.println(CodiceFiscale.controllaValidita("RMNTMS00R03F205E".toCharArray()));
	}
}
