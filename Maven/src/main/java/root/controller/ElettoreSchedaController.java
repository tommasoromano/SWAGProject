package root.controller;

import java.util.List;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.CheckBox;
import javafx.scene.Node;
import root.App;
import root.util.LogManager;
import root.util.Scheda;

public class ElettoreSchedaController extends Controller {
	
	private boolean votato;
	private Scheda scheda;
	private String voto;
	
	private List<Node> boxes;

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
    private Label textError;
    
    
    @FXML
    void onActionIndietro() {
    	App.navigate("ElettoreView");
    }
    
    @Override
    public void init(Object param) {
    	this.votato = false;
    	this.voto = "";
    	boxes = new ArrayList<>();

    	if (param.getClass().equals(Scheda.class)) {
    		
    		this.scheda = ((Scheda) param);
    		
    		nomeScheda.setText(this.scheda.getNome());
    		HBox hbox = null;
    		
    		// crea la UI in base a ModalitaVoto della scheda
    		switch(this.scheda.getTipoVoto().getTipo()) {
    			case VotoOrdinale:
    				Label desc = new Label("Ordinare in modo crescente i candidati, dal più favorito al meno");
    				parent.getChildren().add(desc);
    				
    				String[] votabile = this.scheda.getDatiVoto().getCandidati();

    				hbox = null;
    				for (int i = 0; i < votabile.length; i+=1) {
    					Label b = new Label(votabile[i]);
    					ChoiceBox<Integer> box = new ChoiceBox<>();
    					boxes.add(box);
    					box.setId("" + i);
    					for (int j=0; j<votabile.length; j++) {
    						box.getItems().add(j+1);
    					}
    					if (i%2==0) {
    						hbox = new HBox();
    						hbox.setAlignment(Pos.CENTER);
    						hbox.setSpacing(20);
    						hbox.getChildren().add(b);
    						hbox.getChildren().add(box);
    						if (i==votabile.length-1) parent.getChildren().add(hbox);
    					} else {
    						hbox.getChildren().add(b);
    						hbox.getChildren().add(box);
    						parent.getChildren().add(hbox);
    						
    					}
    				}
    				Button button = new Button("Vota");
    				button.setOnAction((event) -> {
    					votoOrdinale();
    				});
    				parent.getChildren().add(button);
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
        						this.voto = b.getText();
        			    		this.votato = true;
        			    		this.labelConferma.setText("Hai votato: " + this.voto);
        					});
    						hbox.getChildren().add(b);
    					} else {
        					Button b = new Button(votabile[i]);
        					b.setOnAction((event) -> {
        						this.voto = b.getText();
        			    		this.votato = true;
        			    		this.labelConferma.setText("Hai votato: " + this.voto);
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
    					
    					//crea checkbox per il gruppo
    					CheckBox b = new CheckBox(votabile[i]);
    					boxes.add(b);
    					b.setId("gruppo:"+i);
    					b.setOnAction((event) -> {
    					    for (Node n : boxes) {
    					    	if ( !b.equals(n) && (n instanceof CheckBox) && n.getId() != null && n.getId().contains("gruppo") ) {
    					    		((CheckBox)n).setSelected(false);
    					    	}
    					    }
    					});
    					
    					if (i%2==0) {
    						hboxParent = new HBox();
    						hboxParent.setAlignment(Pos.CENTER);
    						hboxParent.setSpacing(20);
    						// crea button gruppo
    						hbox = new HBox();
    						hbox.setAlignment(Pos.CENTER);
    						hbox.setSpacing(5);
        					
        					// cre button preferenze del gruppo
        					VBox vbox = new VBox();
    						vbox.setAlignment(Pos.CENTER);
    						vbox.setSpacing(5);
        					for (int j = 0; j < pref.length; j++) {
        						CheckBox checkp = new CheckBox(pref[j]);
        						checkp.setId("g:"+i+":p:" + j );
        						boxes.add(checkp);
        						checkp.setOnAction((event) -> {
        							checkp.setSelected(true);
            					    for (Node n : boxes) {
            					    	if (!n.equals(checkp) && (n instanceof CheckBox) && !n.getId().contains("gruppo")) {
            					    		((CheckBox)n).setSelected(false);
            					    	}
            					    }
            					});
        						vbox.getChildren().add(checkp);
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
        					// crea button preferenze
        					VBox vbox = new VBox();
    						vbox.setAlignment(Pos.CENTER);
    						vbox.setSpacing(5);
        					for (int j = 0; j < pref.length; j++) {
        						CheckBox checkp = new CheckBox(pref[j]);
        						boxes.add(checkp);
        						checkp.setId("g:"+i+":p:" + j );
        						checkp.setOnAction((event) -> {
        							checkp.setSelected(true);
            					    for (Node n : boxes) {
            					    	if (!n.equals(checkp) && (n instanceof CheckBox) && !n.getId().contains("gruppo")) {
            					    		((CheckBox)n).setSelected(false);
            					    	}
            					    }
            					});
        						vbox.getChildren().add(checkp);
        					}
    						hbox.getChildren().add(b);
    						hbox.getChildren().add(vbox);
    						hboxParent.getChildren().add(hbox);
    						parent.getChildren().add(hboxParent);

    					}
    				}
    				Button vota = new Button("Vota");
					vota.setOnAction((e)-> {
						votoCategoricoPreferenze();
					});
					parent.getChildren().add(vota);
    				break;
    			case Referendum:
    				Label domanda = new Label(scheda.getDatiVoto().getDomanda());
    				parent.getChildren().add(domanda);
    				hbox = new HBox();
					hbox.setAlignment(Pos.CENTER);
					hbox.setSpacing(20);
					CheckBox s = new CheckBox("Si");
					boxes.add(s);
					s.setOnAction((event) -> {
					    for (Node n : boxes) {
					    	if (!n.equals(s) && (n instanceof CheckBox)) ((CheckBox)n).setSelected(false);
					    }
					});
					CheckBox no = new CheckBox("No");
					boxes.add(no);
					no.setOnAction((event) -> {
						for (Node n : boxes) {
					    	if (!n.equals(no) && (n instanceof CheckBox)) ((CheckBox)n).setSelected(false);
					    }
					});
					hbox.getChildren().add(s);
					hbox.getChildren().add(no);
					parent.getChildren().add(hbox);
					Button v = new Button("Vota");
					v.setOnAction((e)-> {
						referendum();
					});
					parent.getChildren().add(v);
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
   
    
    private void votoOrdinale() {
    	String [] candidati = scheda.getDatiVoto().getCandidati();
    	
    	for (int i=0; i<boxes.size(); i++) {
    		if ( (boxes.get(i) instanceof ChoiceBox<?>) && ( ((ChoiceBox<?>)boxes.get(i)).getValue() == null ) ) {
    			textError.setText("Selezionare tutti i candidati");
    			return;
    		}
    		
    		for (int j=0; j<boxes.size(); j++) {
    			if (i!=j &&  ((ChoiceBox<?>)boxes.get(i)).getValue().equals(((ChoiceBox<?>)boxes.get(j)).getValue()) ) {
    				textError.setText("Ordinamento non valido");
    				return;
    			}
    		}
    	}
    	
    	this.voto="";
    	for (int i=0; i<candidati.length; i++) {
    		this.voto+=candidati[i] + "-" + ((ChoiceBox<?>)boxes.get(i)).getValue().toString();
    		if (i<candidati.length-1) this.voto+= ":";
    	}
    	textError.setText("");
    	this.votato = true;
    	this.labelConferma.setText("Dati votazione: " + this.voto);
    }
    
        
    private void votoCategoricoPreferenze() {
    	CheckBox gruppo = null;
    	CheckBox preferenze = null;
    	for (Node n : boxes) {
    		if ( (n instanceof CheckBox) && ((CheckBox)n).isSelected() ) {
    			if ( ((CheckBox)n).getId().contains("gruppo") ) {
    				gruppo = ((CheckBox)n);
    			} else {
    				preferenze = ((CheckBox)n);
    			}
    		}
    	}
    	
    	if (gruppo == null || preferenze == null) {
    		textError.setText("Selezionare un gruppo e una preferenza");
    		return;
    	}
    	
    	if (!gruppo.getId().split(":")[1].equals(preferenze.getId().split(":")[1])) {
    		textError.setText("Preferenza deve appartenere allo stesso gruppo selezionato");
    		return;
    	}
    	this.voto = "gruppo: " + gruppo.getText() + " con preferenza: " + preferenze.getText(); 
    	this.votato = true;
    	textError.setText("");
    	this.labelConferma.setText("Hai votato: "+ this.voto);
    }
    
    private void referendum() {
    	for (Node n : boxes) {
    		if ( (n instanceof CheckBox ) && ((CheckBox)n).isSelected()) {
    			this.votato = true;
    			this.voto = ((CheckBox)n).getText();
    			textError.setText("");
    			this.labelConferma.setText("Hai votato: " + this.voto);
    			return;
    		}
    	}
    	
    	textError.setText("Inserire preferenza");
    }
    
    private void onActionBianca() {
    	for (Node n : boxes) {
    		if ( (n instanceof CheckBox) ) ((CheckBox)n).setSelected(false);
    	}
    	this.voto = "scheda bianca";
    	this.labelConferma.setText("Hai scelto di votare: " + this.voto);
    }

    private void onActionNulla() {
    	for (Node n : boxes) {
    		if ( (n instanceof CheckBox) ) ((CheckBox)n).setSelected(false);
    	}
    	this.voto = "scheda nulla";
    	this.labelConferma.setText("Hai scelto di votare: " + this.voto);
    }
    
    @FXML
    void onActionConferma() {
    	if (!votato) {
    		textError.setText("Votazione non valida");
    		return;
    	}
    	LogManager.getInstance().logVotazione(App.getInstance().getElettore(), scheda.getNome());
    	DBManager.getInstance().votaScheda(this.scheda, this.voto);
    	App.navigate("ElettoreView");
    }

}
