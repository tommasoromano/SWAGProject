package root.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import root.*;

public class ScrutinatoreLoginController extends Controller {

	@Override
    public void init() {
    	
    }

	@FXML
    private Button buttonIndietro;
	
	@FXML
    private Button buttonLogin;
	
	@FXML
    private PasswordField codice;

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
    	
    	// crea istanza di scrutinatore
    	boolean res = DBManager.getInstance().scrutinatoreLogin(email.getText(), codice.getText());
    	
    	if (!res) {
    		textError.setText("Email o Codice errati");
    		return false;
    	} 
    	
    	return true;
    }

}