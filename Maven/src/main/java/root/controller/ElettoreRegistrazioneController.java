package root.controller;

import root.util.CodiceFiscale;
import root.util.Data;
import root.util.Sesso;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import root.App;

public class ElettoreRegistrazioneController {
	
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
    		App.navigate("ElettoreLoginView");
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
    	}
    	//cognome
    	if (cognome.getText().length() == 0) {
    		textError.setText("Compila il campo Cognome");
			return false;
    	}
    	//luogo nascita
    	if (luogo.getText().length() == 0) {
    		textError.setText("Compila il campo Luogo");
			return false;
    	}
    	//data nascita
    	if (data == null) {
    		textError.setText("Compila il campo Data");
			return false;
    	}
    	Data d =null;
		try {
			d = new Data(data.getValue().getDayOfMonth(),data.getValue().getMonthValue(), data.getValue().getYear());
		} catch (IllegalArgumentException e) {
			textError.setText("Data non valida");
			return false;
		}
    	//codice fiscale
    	CodiceFiscale CF  = null;
    	if (codiceFiscale.getText().length() == 0) {
    		textError.setText("Compila il campo Codice Fiscale");
    	}
    	try {
    		String txt = codiceFiscale.getText().toUpperCase();
    		char [] codice = new char[16];
    		for (int i=0; i<codice.length; i++) {
    			codice[i] = txt.charAt(i);
    		}
			CF = new CodiceFiscale(codice);
		} catch (IllegalArgumentException e) {
			textError.setText("Codice fiscale non valido");
			System.err.println(e.getMessage());
			return false;
		}
		//tessera elettorale
    	if (tesseraElettorale.getText().length() == 0) {
    		textError.setText("Compila il campo Tessera Elettorale");
			return false;
    	}
    	//email
    	if (email.getText().length() == 0) {
    		textError.setText("Compila il campo Email");
			return false;
    	}
    	//password
    	if (password.getText().length() == 0) {
			textError.setText("Compila il campo Password");
			return false;
    	}
    	
    	//sesso
    	Sesso s;
    	if (sessoMaschio.isSelected()) {
    		s = Sesso.M;
    	} else {
    		s = Sesso.F;
    	}
    	
    	try { 
    		boolean res = DBController.getInstance().registerElettore(email.getText(), password.getText(), nome.getText(), cognome.getText(), d, luogo.getText(), CF, tesseraElettorale.getText(), s);
    		if (!res) {
    			textError.setText("Codice fiscale già in uso");
    		}
    		return res;
    	} catch (Exception e) {
    		textError.setText(e.getMessage());
    		e.printStackTrace();
    	}
    	
    	return false;
    }

}
