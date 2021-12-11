package root.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import root.App;
import root.util.Elettore;

public class ElettoreController extends Controller {

    @Override
    public void init() {
    	Elettore e = App.getInstance().getElettore();
    	String h = e.getNome() + " " + e.getCognome();
    	textHead.setText(h);
    }
    
    @FXML
    private Button buttonScheda1;

    @FXML
    private Button buttonScheda2;

    @FXML
    private Label textHead;

    @FXML
    void onActionScheda() {
    	App.navigate("ElettoreSchedaView");
    	
    }

}
