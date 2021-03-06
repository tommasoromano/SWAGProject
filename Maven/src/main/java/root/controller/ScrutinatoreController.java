package root.controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import root.App;
import root.DBManager;
import root.LogManager;
import root.util.Scheda;

public class ScrutinatoreController extends Controller {

	private Scheda[] schede;
	
    @FXML
    private Button buttonCrea;
    
    @FXML
    private VBox parent;
    
    @FXML
    private Button logoutButton;
	
	@Override
    public void init() {
		this.schede = DBManager.getInstance().getSchede();
		
		if (this.schede == null || this.schede.length == 0) {
			
			Label l = new Label("Nessuna scheda");
			parent.getChildren().add(l);
			
		} else {
			
			for (int i = 0; i < schede.length; i++) {
				creaElementoScheda(schede[i]);
			}
		}
    }
	
	private void creaElementoScheda(Scheda s) {
		
		GridPane gb = new GridPane();
		
		Label n = new Label(s.getNome());
		Label i = new Label(s.getInizio().toString());
		Label f = new Label(s.getFine().toString());
		Button b = null;
		int t = s.timeState();
		if (t < 0) {
			b = new Button("Scrutina");
			b.setDisable(true);
		}
		else if (t == 0) {
			b = new Button("Scrutina");
			b.setDisable(true);
		}
		else if (t > 0) {
			if (s.getScrutinata()) {
				b = new Button("Esito");
				b.setOnAction((e)->{
					App.navigate("EsitoView", s);
				});
			} else {
				b = new Button("Scrutina");
				b.setOnAction(e -> {
					DBManager.getInstance().scrutinaScheda(s);
					App.navigate("ScrutinatoreView");
				});
			}
		}
		
		gb.add(n, 0, 0);
		gb.add(i, 1, 0);
		gb.add(f, 2, 0);
		gb.add(b, 3, 0);
		
		parent.getChildren().add(gb);
		gb.setAlignment(Pos.CENTER);
		gb.setMinWidth(400);
		gb.setMaxWidth(600);
		for (int j = 0; j < gb.getColumnCount(); j++) {
			ColumnConstraints cc = new ColumnConstraints();
			cc.setPrefWidth(600/gb.getColumnCount());
			gb.getColumnConstraints().add(cc);
		}
		
	}

    @FXML
    void onActionCrea() {
    	App.navigate("CreaSchedaView");
    }
    
    @FXML
    void onActionLogout() {
    	LogManager.getInstance().logScrutinatoreLogout(App.getInstance().getScrutinatore());
    	App.getInstance().setScrutinatore(null);
    	App.navigate("HomeView");
    }

}
