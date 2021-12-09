package root.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import root.App;

public class ElettoreController {

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
