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
import root.DBManager;
import root.LogManager;
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

    	setVotazioneCorretta(false, "", "Selezionare voto");
    	
    	boxes = new ArrayList<>();

    	if (param.getClass().equals(Scheda.class)) {
    		
    		this.scheda = ((Scheda) param);
    		
    		nomeScheda.setText(this.scheda.getNome());
    		
    		// crea header
    		Label labelHeader = new Label(creaHeader(this.scheda));
    		parent.getChildren().add(labelHeader);
    		
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
    					box.setOnAction((event) -> {
    						votoOrdinale();
    					});
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
    				votoOrdinale();
    				break;
    			case VotoCategorico:
    				votabile = this.scheda.getDatiVoto().getCandidati();
    				hbox = null;
    				for (int i = 0; i < votabile.length; i+=1) {
    					if (i%2==0) {
    						hbox = new HBox();
    						hbox.setAlignment(Pos.CENTER);
    						hbox.setSpacing(20);
    					}
    					Button b = new Button(votabile[i]);
    					b.setOnAction((event) -> {
    						this.voto = b.getText();
    						votoCategorico();
    					});
						hbox.getChildren().add(b);
					
						if (i+1 >= votabile.length || i%2!=0) {
    						parent.getChildren().add(hbox);
						} 
    				}
    				//votoCategorico();
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
    						votoCategoricoPreferenze();
    					});
    					// se primo del hbox
    					if (i%2==0) {
    						hboxParent = new HBox();
    						hboxParent.setAlignment(Pos.CENTER);
    						hboxParent.setSpacing(20);
    					}
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
        						votoCategoricoPreferenze();
        					});
    						vbox.getChildren().add(checkp);
    					}
						hbox.getChildren().add(b);
						hbox.getChildren().add(vbox);
						hboxParent.getChildren().add(hbox);
						
						if (i+1 >= votabile.length || i%2!=0) {
    						parent.getChildren().add(hboxParent);
						}
    				}
    				votoCategoricoPreferenze();
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
						referendum();
					});
					CheckBox no = new CheckBox("No");
					boxes.add(no);
					no.setOnAction((event) -> {
						for (Node n : boxes) {
					    	if (!n.equals(no) && (n instanceof CheckBox)) ((CheckBox)n).setSelected(false);
					    }
						referendum();
					});
					hbox.getChildren().add(s);
					hbox.getChildren().add(no);
					parent.getChildren().add(hbox);
					referendum();
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
    
    private String creaHeader(Scheda s) {
		String header = s.getNome()+" del "+s.getInizio().toString()+" - "+s.getFine().toString()+"\n";
		
		// modalita voto
		switch (s.getTipoVoto().getTipo()) {
		case VotoOrdinale:
			header += "Modalita di voto: voto ordinale";
			break;
		case VotoCategorico:
			header += "Modalita di voto: voto categorico";
			break;
		case VotoCategoricoConPreferenze:
			header += "Modalita di voto: voto categorico con preferenze";
			break;
		case Referendum:
			header += "Domanda referendum: " + s.getDatiVoto().getDomanda();
			break;
		}
		
		// modalita conteggio
		header += "\nModalita di conteggio: " + s.getTipoVincitore().toString();
		return header;
	}
   
    
    private void votoOrdinale() {
    	String [] candidati = scheda.getDatiVoto().getCandidati();
    	
    	for (int i=0; i<boxes.size(); i++) {
    		if ( (boxes.get(i) instanceof ChoiceBox<?>) && ( ((ChoiceBox<?>)boxes.get(i)).getValue() == null ) ) {
    			setVotazioneCorretta(false, "", "Selezionare tutti i candidati");
    			return;
    		}
    		
    		for (int j=0; j<boxes.size(); j++) {
    			if (i!=j &&  ((ChoiceBox<?>)boxes.get(i)).getValue().equals(((ChoiceBox<?>)boxes.get(j)).getValue()) ) {
    				setVotazioneCorretta(false, "", "Ordinamento non valido");
    				return;
    			}
    		}
    	}
    	
    	this.voto="";
    	String msg = "";
    	for (int i=0; i<candidati.length; i++) {
    		this.voto+=candidati[i] + "(" + ((ChoiceBox<?>)boxes.get(i)).getValue().toString()+")";
    		if (i<candidati.length-1) this.voto+= ":";
    		msg+=candidati[i] + " (" + ((ChoiceBox<?>)boxes.get(i)).getValue().toString()+")";
    		if (i<candidati.length-1) msg+= ",";
    	}
    	setVotazioneCorretta(true, this.voto, msg);
    }
    
    private void votoCategorico() {
    	setVotazioneCorretta(true, this.voto, this.voto);
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
    	
    	if (gruppo == null) {
    		setVotazioneCorretta(false, "", "Selezionare un gruppo e una preferenza");
    		return;
    	}
    	
    	if (preferenze == null) {
    		setVotazioneCorretta(true, gruppo.getText(),
        			gruppo.getText() + " senza preferenza");
    	} else {

        	if (!gruppo.getId().split(":")[1].equals(preferenze.getId().split(":")[1])) {
        		setVotazioneCorretta(false, "", "Preferenza deve appartenere allo stesso gruppo selezionato");
        		return;
        	}
        	
    	setVotazioneCorretta(true, gruppo.getText() + "(" + preferenze.getText() + ")",
    			gruppo.getText() + " con preferenza: " + preferenze.getText());
    	}
	}
    
    private void referendum() {
    	buttonConferma.setDisable(false);
    	for (Node n : boxes) {
    		if ( (n instanceof CheckBox ) && ((CheckBox)n).isSelected()) {
    			setVotazioneCorretta(true, ((CheckBox)n).getText(), ((CheckBox)n).getText());
    			return;
    		}
    	}
    	setVotazioneCorretta(false, "", "Inserire voto");
    }
    
    private void onActionBianca() {
    	for (Node n : boxes) {
    		if ( (n instanceof CheckBox) ) ((CheckBox)n).setSelected(false);
    	}
    	setVotazioneCorretta(true, "SB", "scheda bianca");
    }

    private void onActionNulla() {
    	for (Node n : boxes) {
    		if ( (n instanceof CheckBox) ) ((CheckBox)n).setSelected(false);
    	}
    	setVotazioneCorretta(true, "SN", "scheda nulla");
    }
    
    private void setVotazioneCorretta(boolean corretta, String nuovoVoto, String msg) {
    	this.votato = corretta;
    	buttonConferma.setDisable(!corretta);
    	if (corretta) {
    		this.voto = nuovoVoto;
    		labelConferma.setText("Hai scelto di votare:\n" + msg);
    		textError.setText("");
    	} else {
    		this.voto = "";
    		labelConferma.setText("");
    		textError.setText(msg);
    	}
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
