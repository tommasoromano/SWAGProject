package root.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import root.App;

public class ElettoreLoginController {

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
    	App.navigate("ElettoreView");
    }

}
