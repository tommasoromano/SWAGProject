package root.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import root.App;

public class ElettoreSchedaController extends Controller {

	@Override
    public void init() {
    	
    }

    @FXML
    private Button buttonBianca;

    @FXML
    private Button buttonIndietro;

    @FXML
    private Button buttonNulla;

    @FXML
    private Button buttonVota;

    @FXML
    void onActionBianca() {
    	App.navigate("ElettoreSchedaConfermaView");
    }

    @FXML
    void onActionIndietro() {
    	App.navigate("ElettoreView");
    }

    @FXML
    void onActionNulla() {
    	App.navigate("ElettoreSchedaConfermaView");
    }

    @FXML
    void onActionVota() {
    	App.navigate("ElettoreSchedaConfermaView");
    }

}
