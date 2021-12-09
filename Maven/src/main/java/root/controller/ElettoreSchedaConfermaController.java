package root.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import root.App;

public class ElettoreSchedaConfermaController extends Controller {

	@Override
    public void init() {
    	
    }

    @FXML
    private Button buttonAnnulla;

    @FXML
    private Button buttonConferma;

    @FXML
    void onActionAnnulla() {
    	App.navigate("ElettoreSchedaView");
    }

    @FXML
    void onActionConferma() {
    	App.navigate("ElettoreView");
    }

}
