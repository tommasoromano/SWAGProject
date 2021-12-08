package root.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import root.App;

public class ElettoreRegistrazioneController {

    @FXML
    private Button buttonIndietro;
	
	@FXML
    private Button buttonRegistrati;

    @FXML
    void onActionIndietro() {
    	App.navigate("HomeView");
    }
    
    @FXML
    void onActionRegistrati() {
    	App.navigate("ElettoreLoginView");
    }

}
