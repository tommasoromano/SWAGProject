import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Scanner;

public class Nazione {
	
	private String iso3;
	
	private String numeric;
	
	public Nazione(String iso3) {
		
		this.iso3 = iso3;
		this.numeric = getNumericFrmoIso3();
	}
	
	public static String[] getAllIso3() {
		
		File myObj = new File(getFilePath());
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
	
	public /* @ pure @ */ String getIso3() {
		return this.iso3;
	}
	
	private String getNumericFrmoIso3() {
		
		File myObj = new File(getFilePath());
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
	
	public /* @ pure @ */ String getNumeric() {
		return this.numeric;
	}
	
	private static String getFilePath() {
		return FileSystems.getDefault().getPath("").toAbsolutePath().toString()
				+ "/src/main/java/iso-country-codes.csv";
	}
	
}
