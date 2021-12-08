package root.util;

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
		File myObj = new File(getFilePath());
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
	
	/**
	 * Crea una {@code String[]} di tutte le regioni d'Italia.
	 * 
	 * <p>Usa il file <i>comuni-italiani.csv</i> per prendere 
	 * tutte le informaziomni riguardante i nomi delle regioni.
	 * 
	 * @return	uno {@code String[]} di tutte le regioni d'Italia
	 */
	public static String[] getAllRegion() {
		
		File myObj = new File(getFilePath());
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
	
	/**
	 * Crea una {@code String[]} di tutte le provincie di una regione.
	 * 
	 * <p>Usa il file <i>comuni-italiani.csv</i> per prendere 
	 * tutte le informaziomni riguardante i nomi delle regioni e provincie.
	 * 
	 * @param regione per ricercare le provincie.
	 * 
	 * @return	uno {@code String[]} di tutte le provincie della regione.
	 */
	public static String[] getAllProvince(String regione) {
		
		File myObj = new File(getFilePath());
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
	
	/**
	 * Crea una {@code String[]} di tutti i comuni di una provincia.
	 * 
	 * <p>Usa il file <i>comuni-italiani.csv</i> per prendere 
	 * tutte le informaziomni riguardante i nomi delle provincie e comuni.
	 * 
	 * @param	ricerca i comuni usando {@code String} provincia.
	 * 
	 * @return	uno {@code String[]} di tutti i comuni della provincia.
	 */
	public static String[] getAllComuni(String provincia) {
		
		File myObj = new File(getFilePath());
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
	
	/**
	 * Ritorna una {@code String[]} di tutti i comuni che iniziano con
	 * le lettere presenti nella {@code String} <b>search</b>.
	 * 
	 * <p>Usa il file <i>comuni-italiani.csv</i> per prendere 
	 * tutte le informaziomni riguardanti i nomi dei comuni.
	 * 
	 * @param	ricerca i comuni usando {@code String} come iniziali.
	 * 
	 * @return	uno {@code String[]} di tutti i comuni trovati.
	 */
	public static String[] searchComuni(String search) {
		
		File myObj = new File(getFilePath());
		Scanner myReader = null;
		try {
			myReader = new Scanner(myObj);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String comuni = "";
		String line = myReader.nextLine();
		search = search.toLowerCase();
		while (myReader.hasNextLine()) {
			line = myReader.nextLine();
			String[] attr = line.split(",");
			String c = attr[5].split("/")[0];
			if (search.length() < c.length() ) {
				String lc = c.substring(0,search.length()).toLowerCase();
				if (lc.contains(search)) {
					comuni = comuni + "," + c;
				}
			}
		}
		
		return comuni.split(",");
	}
	

	private static String getFilePath() {
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
