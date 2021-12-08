package root.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import root.App;

public class ScrutinatoreLoginController {

	@FXML
    private Button buttonIndietro;
	
	@FXML
    private Button buttonLogin;
	
	@FXML
    private TextField codice;

    @FXML
    private TextField email;

    @FXML
    private Label textError;

    @FXML
    void onActionIndietro() {
    	App.navigate("HomeView");
    }
    
    @FXML
    void onActionLogin() {
    	if (checkLogin()) {
    		App.navigate("ScrutinatoreView");
    	}
    }
    
    private boolean checkLogin() {
    	
    	if (email.getText().length() == 0) {
    		textError.setText("Compila il campo Email");
			return false;
    	}
    	if (codice.getText().length() == 0) {
			textError.setText("Compila il campo Codice");
			return false;
    	}
    	
    	return true;
    }

}