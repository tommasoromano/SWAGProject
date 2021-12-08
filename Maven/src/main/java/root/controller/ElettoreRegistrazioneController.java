package root.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import root.App;

public class ElettoreRegistrazioneController {

	@FXML
    private Button buttonIndietro;

    @FXML
    private Button buttonRegistrati;

    @FXML
    private TextField codiceFiscale;

    @FXML
    private TextField cognome;

    @FXML
    private TextField data;

    @FXML
    private TextField email;

    @FXML
    private TextField luogo;

    @FXML
    private TextField nome;

    @FXML
    private TextField password;

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
    
    private boolean checkRegistrazione() {
    	
    	if (nome.getText().length() == 0) {
    		textError.setText("Compila il campo Nome");
			return false;
    	}
    	if (cognome.getText().length() == 0) {
    		textError.setText("Compila il campo Cognome");
			return false;
    	}
    	if (luogo.getText().length() == 0) {
    		textError.setText("Compila il campo Luogo");
			return false;
    	}
    	if (data.getText().length() == 0) {
    		textError.setText("Compila il campo Data");
			return false;
    	}
    	if (codiceFiscale.getText().length() == 0) {
    		textError.setText("Compila il campo Codice Fiscale");
			return false;
    	}
    	if (tesseraElettorale.getText().length() == 0) {
    		textError.setText("Compila il campo Tessera Elettorale");
			return false;
    	}
    	if (email.getText().length() == 0) {
    		textError.setText("Compila il campo Email");
			return false;
    	}
    	if (password.getText().length() == 0) {
			textError.setText("Compila il campo Password");
			return false;
    	}
    	
    	return true;
    }

}
