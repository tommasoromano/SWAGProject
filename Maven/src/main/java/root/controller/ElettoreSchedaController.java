package root.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import root.App;
import root.util.LogManager;
import root.util.Scheda;

public class ElettoreSchedaController extends Controller {
	
	private Scheda scheda;

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
    	LogManager.getInstance().logVotazione(App.getInstance().getElettore(), scheda.getNome());
    	App.navigate("ElettoreSchedaConfermaView");
    }

    @FXML
    void onActionIndietro() {
    	App.navigate("ElettoreView");
    }

    @FXML
    void onActionNulla() {
    	LogManager.getInstance().logVotazione(App.getInstance().getElettore(), scheda.getNome());
    	App.navigate("ElettoreSchedaConfermaView");
    }

    @FXML
    void onActionVota() {
    	LogManager.getInstance().logVotazione(App.getInstance().getElettore(), scheda.getNome());
    	App.navigate("ElettoreSchedaConfermaView");
    }
    
    @Override
    public void init(Object param) {
    	
    	if (param.getClass().equals(Scheda.class)) {
    		
    		scheda = ((Scheda) param);
    		
    		nomeScheda.setText(scheda.getNome());
    		String [] candidati = scheda.getDatiVoto().getCandidati();
    		for (String str : candidati) {
    			Button butt = new Button("Vota: " + str);
    			parent.getChildren().add(butt);
    		}
    		
    		
    	} else {
    		
    	}
    	
    }

}
