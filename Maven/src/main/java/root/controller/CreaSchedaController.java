package root.controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import root.App;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import root.util.LogManager;
import root.util.Data;
import root.util.DatiVoto;
import root.util.ModalitaConteggio;
import root.util.ModalitaVoto;

public class CreaSchedaController extends Controller {
	
	@Override
	public void init() {
		data = null;
		String[] mv = ModalitaVoto.getAllTipi();
		for (int i = 0; i < mv.length; i++) {
			tipoVoto.getItems().add(mv[i]);
		}
		tipoVoto.setOnAction((event) -> {
		    cambiaModalitaVoto(tipoVoto.getValue().toString());
		});
		tipoVoto.getSelectionModel().selectFirst();
		
		String[] mc = ModalitaConteggio.getAllTipi();
		for (int i = 0; i < mc.length; i++) {
			tipoVincitore.getItems().add(mc[i]);
		}
		tipoVincitore.setOnAction((event) -> {
		    
		});
		tipoVincitore.getSelectionModel().selectFirst();
		
		
	}
	
	private String data;
	private List<String> votabili;
	private List<String> preferenze;
	private ChoiceBox cb;
	
	@FXML
	private GridPane gridPane;
	
    @FXML
    private Button buttonCrea;

    @FXML
    private Button buttonIndietro;

    @FXML
    private DatePicker dataFine;

    @FXML
    private DatePicker dataInizio;

    @FXML
    private TextField nomeScheda;
    
    @FXML
    private Label textError;

    @FXML
    private ChoiceBox<String> tipoVincitore;
    
    private ModalitaVoto modalitaVoto;

    @FXML
    private ChoiceBox<String> tipoVoto;

    @FXML
    void onActionCrea() {
    	if (checkCreaScheda()) {
    		LogManager.getInstance().logCreaScheda(App.getInstance().getScrutinatore(), nomeScheda.getText());
    		App.navigate("ScrutinatoreView");
    	} 
    }
    
    private void cambiaModalitaVoto(String mv) {
    	modalitaVoto = new ModalitaVoto(mv);
    	try { 
    		while(gridPane.getChildren().size() > 10){
    			gridPane.getChildren().remove(10);
    		}
    	} catch (Exception e) {
    		System.err.println(e.toString());
    	}
    	this.data = "";
    	switch(modalitaVoto.getTipo()) {
    	 	case VotoOrdinale:
    	 		votoCategoricoUI();
    	 		break;
    	 	case VotoCategorico:
    	 		votoCategoricoUI();
    	 		break;
    	 	case VotoCategoricoConPreferenze:
    	 		votoCategoricoPreferenzeUI();
    	 		break;
    	 	case Referendum:
    	 		votoReferendumUI();
    	 		break;
    	}
    		
    }
    
    private void votoCategoricoUI() {
    	
    	Label l = new Label("Aggiungi votabili");
    	TextField tf = new TextField();
    	Button b = new Button("Aggiungi");
    	b.setOnAction((event) -> {
    		changeVotabili(tf.getText());
    	});
    	
    	gridPane.add(l, 0, 3);
    	gridPane.add(tf, 1, 3);
    	gridPane.add(b, 2, 3);
		
		votabili = new ArrayList<>();
    }
    
	private void votoCategoricoPreferenzeUI() {
	    	
	    	Label l = new Label("Aggiungi gruppi");
	    	TextField tf = new TextField();
	    	Button b = new Button("Aggiungi");
	    	b.setOnAction((event) -> {
	    		changeVotabiliPreferenze(tf.getText(), "");
	    	});
	    	
	    	gridPane.add(l, 0, 3);
	    	gridPane.add(tf, 1, 3);
	    	gridPane.add(b, 2, 3);
	    	
	    	Label lp = new Label("Aggiungi preferenze");
	    	cb = new ChoiceBox();
	    	for (int i = 0; i < votabili.size(); i++) {
				cb.getItems().add(votabili.get(i));
			}
	    	TextField tfp = new TextField();
	    	Button bp = new Button("Aggiungi");
	    	bp.setOnAction((event) -> {
	    		if (cb.getSelectionModel().isEmpty()) {
	    			textError.setText("Selezionare gruppo per aggiungere la preferenza");
	    		} else { 
	    			changeVotabiliPreferenze(cb.getValue().toString(), tfp.getText());
	    		}
	    	});
	    	
	    	gridPane.add(lp, 0, 4);
	    	gridPane.add(cb, 1, 4);
	    	gridPane.add(tfp, 2, 4);
	    	gridPane.add(bp, 3, 4);
			
			votabili = new ArrayList<>();
			preferenze = new ArrayList<>();
    }
    
    private void changeVotabili(String c) {
    	
    	// controllo nuovo votabile
    	if (c == null || c == "") {
    		return;
    	}
    	boolean exist = votabili.contains(c);
    	if (!exist) {
    		votabili.add(c);
    	}
    	else {
    		votabili.remove(c);
    	}
    	
    	// creo griglia
    	try { 
    		while(gridPane.getChildren().size() > 13){
    			gridPane.getChildren().remove(13);
    		}
    	} catch (Exception e) {
    		System.err.println(e.toString());
    	}
    	
    	// creo bottone per ogni votabile
    	this.data = String.join(":", votabili);
    	for (int i = 0, j = 4; i < votabili.size(); i++) {
    		
    		if (i != 0 && i%4==0) j++;
    		
    		HBox hb = new HBox();
    		hb.setAlignment(Pos.CENTER_LEFT);
			hb.setSpacing(10);
        	Label l = new Label(votabili.get(i));
        	Button b = new Button("X");
        	b.setOnAction((event) -> {
        		changeVotabili(l.getText());
        	});
        	hb.getChildren().add(b);
        	hb.getChildren().add(l);
        	gridPane.add(hb, i%4, j);
    	}
    	
    }
    
    /**
     * Se p is null or empty, crea o rimuove votabile,
     * altrimenti aggiunge o rimuove preferenza al gruppo 
     * @param v
     * @param p
     */
    private void changeVotabiliPreferenze(String v, String p) {
    	
    	// controllo nuovo votabile
    	if (v == null || v.equals("")) {
    		return;
    	}
    	int exist = votabili.indexOf(v);
    	if (exist == -1) {
    		votabili.add(v);
    		preferenze.add("");
    	} else {
    		if (p == null || p.equals("")) {
        		votabili.remove(exist);
        		preferenze.remove(exist);
    		} else {
    			List<String> pref = new ArrayList<>();
    			if (preferenze.get(exist).equals("")) {
    				pref.add(p);
    				preferenze.set(exist, p);
    			} else {
    				pref = new ArrayList<>(Arrays.asList(preferenze.get(exist).split(":")));
    				int e = pref.indexOf(p);
        			if (e == -1) {
        				pref.add(p);
        				preferenze.set(exist, String.join(":", pref));
        			} else {
        				pref.remove(e);
        				preferenze.set(exist, String.join(":", pref));
        			}
    			}
    			
    		}
    	}
    	
    	// modify choiche box
    	cb.getItems().clear();
    	for (int i = 0; i < votabili.size(); i++) {
			cb.getItems().add(votabili.get(i));
		}
    	
    	// creo griglia
    	try { 
    		while(gridPane.getChildren().size() > 17){
    			gridPane.getChildren().remove(17);
    		}
    	} catch (Exception e) {
    		System.err.println(e.toString());
    	}
    	
    	// creo bottone per ogni votabile
    	this.data = "";
    	for (int i = 0, j = 5; i < votabili.size(); i++) {
    		
    		if (i != 0 && i%2==0) j++;
    		
    		HBox hb = new HBox();
    		hb.setAlignment(Pos.TOP_LEFT);
			hb.setSpacing(10);
        	Label l = new Label(votabili.get(i));
        	Button b = new Button("X");
        	b.setOnAction((event) -> {
        		changeVotabiliPreferenze(l.getText(), "");
        	});
        	hb.getChildren().add(b);
        	hb.getChildren().add(l);
        	
        	// creo preferenze
        	VBox vb = new VBox();
        	vb.setAlignment(Pos.CENTER_LEFT);
			vb.setSpacing(10);
        	List<String> pref = new ArrayList<>
				(Arrays.asList(preferenze.get(i).split(":")));
        	for (int k = 0; k < pref.size(); k++) {
        		if (!pref.get(k).equals("")) {
        			HBox hbp = new HBox();
            		hbp.setAlignment(Pos.CENTER_LEFT);
        			hbp.setSpacing(10);
	        		Label lp = new Label(pref.get(k));
	            	Button bp = new Button("X");
	            	bp.setOnAction((event) -> {
	            		changeVotabiliPreferenze(l.getText(), lp.getText());
	            	});
	            	hbp.getChildren().add(bp);
	            	hbp.getChildren().add(lp);
	            	vb.getChildren().add(hbp);
        		}
        	}
        	
        	gridPane.add(hb, (i%2 == 0) ? 0 : 2, j);
        	gridPane.add(vb, (i%2 == 0) ? 1 : 3, j);
        	
        	this.data += votabili.get(i) + "(" + preferenze.get(i) + ")";
        	if (i+1 < votabili.size()) this.data += ":";
    	}
    }
    
    private void votoReferendumUI() {
    	Label txt = new Label("Domanda referendum");
 		
 		TextField domanda = new TextField();
 		domanda.textProperty().addListener((obs, oldval, newval)->{
 			data = newval;
 		});
 		
 		gridPane.add(txt, 0, 3);
 		gridPane.add(domanda, 1, 3);
    }
    
    private boolean checkVotoScrutinio() {
    	if (tipoVoto.getValue().toString().equals("Referendum")) {
    		if (tipoVincitore.getValue().toString().contains("Referendum")) return true;
    	} else if (tipoVoto.getValue().toString().contains("Voto")) {
    		if (tipoVincitore.getValue().toString().contains("Maggioranza")) return true;
    	}
    	return false;
    }
    
    private boolean checkCreaScheda() {
    	if (!checkVotoScrutinio()) {
    		textError.setText("Modalita' di voto e di scrutinio non compatibili");
    		return false;
    	}
    	
    	//nome
    	if (nomeScheda.getText().length() == 0) {
    		textError.setText("Compila il campo Nome scheda");
			return false;
    	}
    	
    	//data inizio
    	if (dataInizio == null) {
    		textError.setText("Compila il campo Data di inizio");
			return false;
    	}
    	Data di =null;
		try {
			di = new Data(dataInizio.getValue().getDayOfMonth(),
					dataInizio.getValue().getMonthValue(), 
					dataInizio.getValue().getYear());
		} catch (IllegalArgumentException e) {
			textError.setText("Data non valida");
			return false;
		}
		
		//data fine
    	if (dataInizio == null) {
    		textError.setText("Compila il campo Data di fine");
			return false;
    	}
    	Data df =null;
		try {
			df = new Data(dataFine.getValue().getDayOfMonth(),
					dataFine.getValue().getMonthValue(), 
					dataFine.getValue().getYear());
		} catch (IllegalArgumentException e) {
			textError.setText("Data non valida");
			return false;
		}
		
		//dati presenti
		if (data==null || data.isBlank())  {
			textError.setText("Missing data");
			return false;
		}
		
		// controlla preferenze
		if (tipoVoto.getValue().toString()
				.equals("Voto categorico con preferenze")) {
			for (int i = 0; i < preferenze.size(); i++) {
				if (preferenze.get(i).equals("")) {
					textError.setText("Inserire almeno una preferenza per gruppo");
					return false;
				}
			}
		}
		
		// cerca di creare scheda su DB
		if (DBManager.getInstance().creaScheda(
				nomeScheda.getText(), 
				di, 
				df, 
				new ModalitaVoto(tipoVoto.getValue().toString()),
				new DatiVoto(data), 
				new ModalitaConteggio(tipoVincitore.getValue().toString()))) {
			
    		return true;
    	} else {
    		textError.setText("Errore durante la creazione della scheda, riprovare");
    		return false;
    	}
		
    }

    @FXML
    void onActionIndietro() {
    	App.navigate("ScrutinatoreView");
    }

}

