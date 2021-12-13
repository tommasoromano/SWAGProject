package root.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import root.App;
import root.util.Scheda;

public class ElettoreSchedaController extends Controller {

	@FXML
	private Label nomeScheda;
	
	@FXML
	private VBox parent;
	
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
    
    @Override
    public void init(Object param) {
    	
    	if (param.getClass().equals(Scheda.class)) {
    		
    		Scheda s = ((Scheda) param);
    		
    		nomeScheda.setText(s.getNome());
    		
    		parent.getChildren().add(new Label(s.toString()));
    		
    	} else {
    		
    	}
    	
    }

}
