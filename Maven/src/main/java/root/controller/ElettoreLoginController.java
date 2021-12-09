package root.controller;

import root.controller.DBController;

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
    private TextField email;

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
    	
    	if (email.getText().length() == 0) {
    		textError.setText("Compila il campo Email");
			return false;
    	}
    	if (password.getText().length() == 0) {
			textError.setText("Compila il campo Password");
			return false;
    	}
    	
    	try {
    		DBController db = DBController.getInstance();
    		String sha256hex = Hashing.sha256()
					  .hashString(password.getText(), StandardCharsets.UTF_8)
					  .toString();
    		if (! db.getPsw(email.getText()).equals(sha256hex)) {
    			textError.setText("Password errata");
    			return false;
    		} 
    		return true;
    	} catch (Exception e) {
    		textError.setText("Errore");
    		return false;
    	}

    }

}
