package root.controller;


import root.util.CodiceFiscale;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import root.App;
import root.DBManager;
import root.LogManager;

public class ElettoreLoginController extends Controller {

	@Override
    public void init() {
    	
    }
	
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
    		LogManager.getInstance().logElettoreLogin(App.getInstance().getElettore());
    		App.navigate("ElettoreView");
    	}
    }
    
    private boolean checkLogin() {
    	
    	//codice fiscale
    	if (codiceFiscale.getText().length() == 0) {
    		textError.setText("Compila il campo Codice Fiscale");
    		return false;
    	}
    	CodiceFiscale CF = null;
    	try
    	{
    		CF  = CodiceFiscale.fromStringToCF(codiceFiscale.getText());
        	if (CF == null) {
        		textError.setText("Codice Fiscale errato");
        		return false;
        	}
    	} catch(Exception e) {
    		textError.setText("Codice Fiscale errato");
    		return false;
    	}
    	
    	
    	// password
    	if (password.getText().length() == 0) {
			textError.setText("Compila il campo Password");
			return false;
    	}
    	
    	// crea istanza di elettore
    	boolean res = DBManager.getInstance().elettoreLogin(CF, password.getText());
    	
    	if (!res) {
    		textError.setText("Codice Fiscale o Password errati");
    		return false;
    	} 
    	
		return true;


    }

}
