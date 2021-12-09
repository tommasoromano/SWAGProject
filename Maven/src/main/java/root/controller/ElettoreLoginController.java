package root.controller;

import root.controller.DBController;
import root.util.Elettore;

import java.nio.charset.StandardCharsets;

import com.google.common.hash.Hashing;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import root.App;

public class ElettoreLoginController {

	@FXML
    private Button buttonIndietro;
	
	@FXML
    private Button buttonLogin;
	
	@FXML
    private TextField codiceFiscale;

    @FXML
    private TextField password;
    
    @FXML
    private Label textError;

    @FXML
    void onActionIndietro() {
    	App.navigate("HomeView");
    }
    
    @FXML
    void onActionLogin() {
    	if (checkLogin()) {
    		App.navigate("ElettoreView");
    	}
    }
    
    private boolean checkLogin() {
    	
    	if (codiceFiscale.getText().length() == 0) {
    		textError.setText("Compila il campo Codice Fiscale");
			return false;
    	}
    	if (password.getText().length() == 0) {
			textError.setText("Compila il campo Password");
			return false;
    	}
    	
    	Elettore e = null;//DBController.getInstance().elettoreLogin(codiceFiscale.getText(), password.getText());
    	
    	if (e == null ) {
    		textError.setText("Codice Fiscale o Password errati");
    		return false;
    	} else {
    		App.getInstance().setElettore(e);
    		return true;
    	}

    }

}
