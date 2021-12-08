package root.controller;

import root.App;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HomeController {

    @FXML
    private Button buttonElettoreLogin;

    @FXML
    private Button buttonElettoreRegistrazione;

    @FXML
    private Button buttonScrutinatoreLogin;

    @FXML
    void onActionElettoreLogin() {
		App.navigate("ElettoreLoginView");
    }

    @FXML
    void onActionElettoreRegistrazione() {
		App.navigate("ElettoreRegistrazioneView");
    }

    @FXML
    void onActionScrutinatoreLogin() {
		App.navigate("ScrutinatoreLoginView");
    }

}
