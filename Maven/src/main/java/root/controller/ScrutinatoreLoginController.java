package root.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import root.App;

public class ScrutinatoreLoginController {

	@FXML
    private Button buttonIndietro;
	
	@FXML
    private Button buttonLogin;

    @FXML
    void onActionIndietro() {
    	App.navigate("HomeView");
    }
    
    @FXML
    void onActionLogin() {
    	App.navigate("ScrutinatoreView");
    }

}