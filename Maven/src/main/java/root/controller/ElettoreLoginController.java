package root.controller;

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
    	
    	return true;
    }

}
