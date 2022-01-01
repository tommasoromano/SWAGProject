package root.controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import root.App;
import root.util.Elettore;
import root.util.LogManager;
import root.util.Scheda;

public class ElettoreController extends Controller {

	private Scheda[] schede;
    
    @FXML
    private VBox parent;

    @FXML
    private Label textHead;
    
    @FXML
    private Button logoutButton;
    
    @Override
    public void init() {
    	Elettore e = App.getInstance().getElettore();
    	String h = e.getNome() + " " + e.getCognome();
    	textHead.setText(h);
    	
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
			b = new Button("Vota");
			b.setDisable(true);
		}
		else if (t == 0) {
			if (DBManager.getInstance().hasElettoreVotoScheda(s)) {
				b = new Button("Hai votato");
				b.setDisable(true);
			} else {
				b = new Button("Vota");
				b.setOnAction(e -> {
					App.navigate("ElettoreSchedaView", s);
				});
			}
		}
		else if (t > 0) {
			if (s.getScrutinata()) {
				b = new Button("Esito");
				b.setOnAction((e)->{
					App.navigate("EsitoView", s);
				});
			} else {
				b = new Button("In scrutinio");
				//b.setDisable(true);
				b.setOnAction(e -> {
					App.navigate("ElettoreSchedaView", s);
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
    void onActionLogout() {
    	LogManager.getInstance().logElettoreLogout(App.getInstance().getElettore());
    	App.getInstance().setElettore(null);
    	App.navigate("HomeView");
    }
}
