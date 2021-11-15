import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Scanner;

public class Nazione {
	
	//@ public invariant nazione != null
	private /*@ spec_public @*/ String iso3;
	
	private String numeric;
	
	public Nazione(String iso3) {
		
		this.iso3 = iso3;
		this.numeric = GetNumericFrmoIso3();
	}
	
	public static String[] GetAllIso3() {
		
		File myObj = new File(FilePath());
		Scanner myReader = null;
		try {
			myReader = new Scanner(myObj);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String iso3 = "";
		while (myReader.hasNextLine()) {
			String line = myReader.nextLine();
			String[] attr = line.split(",");
			iso3 = iso3 + "," + attr[2];
		}
		
		return iso3.split(",");
	}
	
	public String GetIso3() {
		return this.iso3;
	}
	
	private String GetNumericFrmoIso3() {
		
		File myObj = new File(FilePath());
		Scanner myReader = null;
		try {
			myReader = new Scanner(myObj);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (myReader.hasNextLine()) {
			String line = myReader.nextLine();
			String[] attr = line.split(",");
			if (attr[2].equals(this.iso3)) {
				return attr[3];
			}
		}
		
		return null;
	}
	
	public String GetNumeric() {
		return this.numeric;
	}
	
	private static String FilePath() {
		return FileSystems.getDefault().getPath("").toAbsolutePath().toString()
				+ "/src/main/java/iso-country-codes.csv";
	}
	
}
