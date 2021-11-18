import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.FileSystems;
import java.util.Scanner;

public class Comune {

	private String comune;
	private String code;
	
	public Comune(String comune) {
		
		this.comune = comune;
		
		// associa comune con suo codice
		File myObj = new File(FilePath());
		Scanner myReader = null;
		try {
			myReader = new Scanner(myObj);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String line = myReader.nextLine();
		while (myReader.hasNextLine()) {
			line = myReader.nextLine();
			String[] attr = line.split(",");
			String c = attr[5].split("/")[0];
			if (comune.equals(c)) {
				this.code = attr[4];
			}
			
		}
		
	}
	
	public static String[] GetAllRegion() {
		
		File myObj = new File(FilePath());
		Scanner myReader = null;
		try {
			myReader = new Scanner(myObj);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String regione = "";
		int regCode = 0;
		String line = myReader.nextLine();
		while (myReader.hasNextLine()) {
			line = myReader.nextLine();
			String[] attr = line.split(",");
			if (Integer.parseInt(attr[1]) != regCode) {
				regione = regione + "," + attr[2].split("/")[0];
				regCode++;
			}
		}
		
		return regione.split(",");
		
	}
	
	public static String[] GetAllProvince(String regione) {
		
		File myObj = new File(FilePath());
		Scanner myReader = null;
		try {
			myReader = new Scanner(myObj);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String provincia = "";
		String last = "";
		String line = myReader.nextLine();
		while (myReader.hasNextLine()) {
			line = myReader.nextLine();
			String[] attr = line.split(",");
			String p = attr[3].split("/")[0];
			if (!last.equals(p)) {
				if (regione.equals(attr[2])) {
					provincia = provincia + "," + p;
					last = p;
				}
			}
		}
		
		return provincia.split(",");
		
	}
	
	public static String[] GetAllComuni(String provincia) {
		
		File myObj = new File(FilePath());
		Scanner myReader = null;
		try {
			myReader = new Scanner(myObj);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String comuni = "";
		String last = "";
		String line = myReader.nextLine();
		while (myReader.hasNextLine()) {
			line = myReader.nextLine();
			String[] attr = line.split(",");
			String c = attr[5].split("/")[0];
			if (!last.equals(c)) {
				if (provincia.equals(attr[3])) {
					comuni = comuni + "," + c;
					last = c;
				}
			}
		}
		
		return comuni.split(",");
		
	}
	
	public String[] searchComune(String search) {
		
		File myObj = new File(FilePath());
		Scanner myReader = null;
		try {
			myReader = new Scanner(myObj);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String comuni = "";
		String line = myReader.nextLine();
		while (myReader.hasNextLine()) {
			line = myReader.nextLine();
			String[] attr = line.split(",");
			String c = attr[5].split("/")[0];
			if (c.contains(search)) {
				comuni = comuni + ",";
			}
		}
		return comuni.split(",");
	}
	

	private static String FilePath() {
		return FileSystems.getDefault().getPath("").toAbsolutePath().toString()
				+ "/src/main/java/comuni-italiani.csv";
	}
	
	public String getComuneNome() {
		return this.comune;
	}
	
	public String getComuneCode() {
		return this.code;
	}
	
}
