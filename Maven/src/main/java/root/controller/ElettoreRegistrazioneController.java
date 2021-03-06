package root.controller;

import root.util.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import root.App;
import root.DBManager;
import root.LogManager;

public class ElettoreRegistrazioneController extends Controller {

	@Override
    public void init() {
    	
    }
	
	@FXML
	private CheckBox sessoMaschio;
	
	@FXML
	private CheckBox sessoFemmina;
	
	@FXML
    private Button buttonIndietro;

    @FXML
    private Button buttonRegistrati;

    @FXML
    private TextField codiceFiscale;

    @FXML
    private TextField cognome;

    @FXML
    private DatePicker data;

    @FXML
    private TextField email;

    @FXML
    private TextField luogo;

    @FXML
    private TextField nome;

    @FXML
    private PasswordField password;

    @FXML
    private TextField tesseraElettorale;

    @FXML
    private Label textError;

    @FXML
    void onActionIndietro() {
    	App.navigate("HomeView");
    }
    
    @FXML
    void onActionRegistrati() {
    	if (checkRegistrazione()) {
    		LogManager.getInstance().logElettoreRegistrazione(App.getInstance().getElettore());
    		App.navigate("ElettoreView");
    	}
    	
	}
    
    @FXML
    void checkboxMaschio() {
    	sessoFemmina.setSelected(false);
    }
    
    @FXML
    void checkboxFemmina() {
    	sessoMaschio.setSelected(false);
    }
    
    /**
     * 
     * @return true se tutti i campi della registrazione sono completati correttamente, false altrimenti
     */
    private boolean checkRegistrazione() {
    	//nome
    	if (nome.getText().length() == 0) {
    		textError.setText("Compila il campo Nome");
			return false;
    	} else {
    		String cog = nome.getText().toLowerCase();
    		for (int i=0; i<cog.length(); i++) {
    			if ((cog.charAt(i) < 'a' || cog.charAt(i) > 'z') && cog.charAt(i) != 39 && cog.charAt(i) != 32) {
    				 textError.setText("Carattere " +cog.charAt(i) + " non valido");
    				 return false;
    			}
    		}
    	}
    	//cognome
    	if (cognome.getText().length() == 0) {
    		textError.setText("Compila il campo Cognome");
			return false;
    	} else {
    		String cog = cognome.getText().toLowerCase();
    		for (int i=0; i<cog.length(); i++) {
    			if ((cog.charAt(i) < 'a' || cog.charAt(i) > 'z') && cog.charAt(i) != 39 && cog.charAt(i) != 32) {
    				 textError.setText("Carattere " +cog.charAt(i) + " non valido");
    				 return false;
    			}
    		}
    	}
    	//luogo nascita
    	if (luogo.getText().length() == 0) {
    		textError.setText("Compila il campo Luogo");
			return false;
    	} else {
    		String cog = luogo.getText().toLowerCase();
    		for (int i=0; i<cog.length(); i++) {
    			if ((cog.charAt(i) < 'a' || cog.charAt(i) > 'z') && cog.charAt(i) != 39 && cog.charAt(i) != 32) {
    				 textError.setText("Carattere " +cog.charAt(i) + " non valido");
    				 return false;
    			}
    		}
    	}
    	//data nascita
    	if (data == null) {
    		textError.setText("Compila il campo Data");
			return false;
    	}
    	Data d = null;
		try {
			d = new Data(data.getValue().getDayOfMonth(),data.getValue().getMonthValue(), data.getValue().getYear());
			if (!d.isMaggiorenne()) {
				textError.setText("Elettore non maggiorenne");
				return false;
			}
		} catch (Exception e) {
			textError.setText("Data non valida");
			return false;
		}
		
		//codice fiscale
    	if (codiceFiscale.getText().length() == 0) {
    		textError.setText("Compila il campo Codice Fiscale");
    		return false;
    	}
    	
    	//sesso
    	Sesso s;
    	if (sessoMaschio.isSelected()) {
    		s = Sesso.M;
    	} else {
    		s = Sesso.F;
    	}
    	
    	CodiceFiscale CF = null;
    	try {
    		CF = CodiceFiscale.fromStringToCF(codiceFiscale.getText());
    		//if (!CF.isValid(nome.getText(), cognome.getText(), d,new Nazione("ITA"), new Comune(luogo.getText()), s)) throw new IllegalArgumentException();
    	} catch (Exception e) {
    		textError.setText("Codice Fiscale errato");
    		return false;
    	}
    	
		//tessera elettorale
    	if (tesseraElettorale.getText().length() == 0) {
    		textError.setText("Compila il campo Tessera Elettorale");
			return false;
    	} else {
    		String tes = tesseraElettorale.getText();
    		for (int i=0; i<tes.length(); i++) {
    			if (tes.charAt(i) < '0' || tes.charAt(i) > '9') {
    				textError.setText("Tessera elettorale non valida");
    				return false;
    			}
    		}
    	}
    	//email
    	if (email.getText().length() == 0) {
    		textError.setText("Compila il campo Email");
			return false;
    	} else {
    		String cog = email.getText().toLowerCase();
    		for (int i=0; i<cog.length(); i++) {
    			if ((cog.charAt(i) < 'a' || cog.charAt(i) > 'z') && cog.charAt(i) != 64 && cog.charAt(i) != 46 && (cog.charAt(i) < '0'|| cog.charAt(i) > '9')) {
    				 textError.setText("Carattere " +cog.charAt(i) + " non valido");
    				 return false;
    			}
    		}
    	}
    	//password
    	if (password.getText().length() == 0) {
			textError.setText("Compila il campo Password");
			return false;
    	}
    	
    	boolean res = DBManager.getInstance().registerElettore(
    			email.getText(), 
    			password.getText(), 
    			nome.getText(), 
    			cognome.getText(), 
    			d, 
    			luogo.getText(), 
    			CF, 
    			tesseraElettorale.getText(), 
    			s);
		if (!res) {
			textError.setText("Codice fiscale gi?? in uso");
			return false;
		}
		
		return true;
    }
  
}
