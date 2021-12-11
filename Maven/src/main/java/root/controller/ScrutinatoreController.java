package root.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import root.App;
import root.util.Scheda;
import root.util.Scrutinatore;

public class ScrutinatoreController extends Controller {

	private Scheda[] schede;
	
	@Override
    public void init() {
		this.schede = DBController.getInstance().getSchede();
		if (this.schede == null) {
			test.setText("Nessuna scheda");
		} else {
			String t = "";
			for (int i = 0; i < schede.length; i++) {
				t += schede[i].toString() + "\n";
			}
			test.setText(t);
		}
    }

    @FXML
    private Button buttonCrea;
    
    @FXML
    private Label test;

    @FXML
    void onActionCrea() {
    	App.navigate("CreaSchedaView");
    }

}
