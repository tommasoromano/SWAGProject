package root.controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
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
    private Label labelConferma;

    @FXML
    private Button buttonIndietro;

    @FXML
    private Button buttonConferma;

    @FXML
    void onActionIndietro() {
    	App.navigate("ElettoreView");
    }
    
    @Override
    public void init(Object param) {

    	if (param.getClass().equals(Scheda.class)) {
    		
    		this.scheda = ((Scheda) param);
    		
    		nomeScheda.setText(this.scheda.getNome());
    		HBox hbox = null;
    		
    		// crea la UI in base a ModalitaVoto della scheda
    		switch(this.scheda.getTipoVoto().getTipo()) {
    			case VotoOrdinale:
    				String[] votabile = this.scheda.getDatiVoto().getCandidati();
    				hbox = null;
    				for (int i = 0; i < votabile.length; i+=1) {
    					if (i%2==0) {
    						hbox = new HBox();
    						hbox.setAlignment(Pos.CENTER);
    						hbox.setSpacing(20);
        					Button b = new Button(votabile[i]);
        					b.setOnAction((event) -> {
        					    onActionVota(b.getText());
        					});
    						hbox.getChildren().add(b);
    					} else {
        					Button b = new Button(votabile[i]);
        					b.setOnAction((event) -> {
        					    onActionVota(b.getText());
        					});
    						hbox.getChildren().add(b);
    						parent.getChildren().add(hbox);
    					}
    				}
    				break;
    			case VotoCategorico:
    				votabile = this.scheda.getDatiVoto().getCandidati();
    				hbox = null;
    				for (int i = 0; i < votabile.length; i+=1) {
    					if (i%2==0) {
    						hbox = new HBox();
    						hbox.setAlignment(Pos.CENTER);
    						hbox.setSpacing(20);
        					Button b = new Button(votabile[i]);
        					b.setOnAction((event) -> {
        					    onActionVota(b.getText());
        					});
    						hbox.getChildren().add(b);
    					} else {
        					Button b = new Button(votabile[i]);
        					b.setOnAction((event) -> {
        					    onActionVota(b.getText());
        					});
    						hbox.getChildren().add(b);
    						parent.getChildren().add(hbox);
    					}
    				}
    				break;
    			case VotoCategoricoConPreferenze:
    				votabile = this.scheda.getDatiVoto().getCandidati();
    				HBox hboxParent = null;
    				hbox = null;
    				for (int i = 0; i < votabile.length; i+=1) {
    					String[] pref = this.scheda.getDatiVoto().getPreferenze(votabile[i]);
    					if (i%2==0) {
    						hboxParent = new HBox();
    						hboxParent.setAlignment(Pos.CENTER);
    						hboxParent.setSpacing(20);
    						// crea button gruppo
    						hbox = new HBox();
    						hbox.setAlignment(Pos.CENTER);
    						hbox.setSpacing(5);
        					Button b = new Button(votabile[i]);
        					b.setOnAction((event) -> {
        					    onActionVota(b.getText());
        					});
        					// cre button preferenze del gruppo
        					VBox vbox = new VBox();
    						vbox.setAlignment(Pos.CENTER);
    						vbox.setSpacing(5);
        					for (int j = 0; j < pref.length; j++) {
        						Button p = new Button(pref[j]);
        						p.setOnAction((event) -> {
            					    onActionVota(b.getText()+"("+p.getText()+")");
            					});
        						vbox.getChildren().add(p);
        					}
    						hbox.getChildren().add(b);
    						hbox.getChildren().add(vbox);
    						hboxParent.getChildren().add(hbox);
    						if (i+1 >= votabile.length) {
        						parent.getChildren().add(hboxParent);
    						}
    					} else {
    						hbox = new HBox();
    						hbox.setAlignment(Pos.CENTER);
    						hbox.setSpacing(5);
    						// crea button gruppo
        					Button b = new Button(votabile[i]);
        					b.setOnAction((event) -> {
        					    onActionVota(b.getText());
        					});
        					// crea button preferenze
        					VBox vbox = new VBox();
    						vbox.setAlignment(Pos.CENTER);
    						vbox.setSpacing(5);
        					for (int j = 0; j < pref.length; j++) {
        						Button p = new Button(pref[j]);
        						p.setOnAction((event) -> {
            					    onActionVota(b.getText()+"("+p.getText()+")");
            					});
        						vbox.getChildren().add(p);
        					}
    						hbox.getChildren().add(b);
    						hbox.getChildren().add(vbox);
    						hboxParent.getChildren().add(hbox);
    						parent.getChildren().add(hboxParent);
    					}
    				}
    				break;
    			case Referendum:
    				hbox = new HBox();
					hbox.setAlignment(Pos.CENTER);
					hbox.setSpacing(20);
					Button s = new Button("Si");
					s.setOnAction((event) -> {
					    onActionVota("si");
					});
					Button n = new Button("No");
					n.setOnAction((event) -> {
					    onActionVota("no");
					});
					hbox.getChildren().add(s);
					hbox.getChildren().add(n);
					parent.getChildren().add(hbox);
    				break;
    			default:
    				
    				break;
    		}
    		
    		// crea scheda bianca e nulla
    		hbox = new HBox();
			hbox.setAlignment(Pos.CENTER);
			hbox.setSpacing(20);
			Button b = new Button("Scheda bianca");
			b.setOnAction((event) -> {
			    onActionBianca();
			});
			Button n = new Button("Scheda nulla");
			n.setOnAction((event) -> {
			    onActionNulla();
			});
			hbox.getChildren().add(b);
			hbox.getChildren().add(n);
			parent.getChildren().add(hbox);
    		
    	} else {
    		
    	}
    	
    }
    
    private void onActionVota(String dati) {
    	this.voto = dati;
    	this.labelConferma.setText("Hai scelto di votare: " + this.voto);
    }
    
    private void onActionBianca() {
    	this.voto = "scheda bianca";
    	this.labelConferma.setText("Hai scelto di votare: " + this.voto);
    }

    private void onActionNulla() {
    	this.voto = "scheda nulla";
    	this.labelConferma.setText("Hai scelto di votare: " + this.voto);
    }
    
    private String voto;
    
    @FXML
    void onActionConferma() {
    	LogManager.getInstance().logVotazione(App.getInstance().getElettore(), scheda.getNome());
    	DBManager.getInstance().votaScheda(this.scheda, this.voto);
    	App.navigate("ElettoreView");
    }

}
