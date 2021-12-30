package root.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import root.App;
import java.util.List;
import java.util.ArrayList;
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
	
	private List<Node> removeNode;
	
	@FXML
	private FlowPane container;
	
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
    
    private boolean aggiungiNuovoVotabile(ComboBox<String> candidati, String tipo, String nome, String cognome, String partito) {
    	String item;
    	
    	if (tipo.equals("Gruppo")) {
    		if (nome == null || nome.isBlank()) {
    			textError.setText("Inserire nome del gruppo");
    			return false;
    		}
    		
    		item = nome; 		
    	} else if (tipo.equals("Candidato")) {
    		if (nome == null || nome.isBlank()) {
    			textError.setText("Compilare campo nome");
    			return false;
    		}
    		
    		if (cognome == null || cognome.isBlank()) {
    			textError.setText("Compilare campo cognome");
    			return false;
    		}
    		
    		if (partito==null || partito.isBlank()) {
    			textError.setText("Compilare campo partito");
    			return false;
    		}
    		
    		item = nome + " " + cognome + "; " + partito;
    	} else {
    		return false;
    	}
    	candidati.getItems().add(item);
    	
    	//formato dati per il DB TODO
    	// data += item;
    	
    	return true;
    }
    
    private void cambiaModalitaVoto(String n) {
    	modalitaVoto = new ModalitaVoto(n);
    	container.getChildren().clear();
    	switch(modalitaVoto.getTipo()) {
    	 	case VotoOrdinale:
    	 		votoOrdinaleCategoricoUI();
    	 		break;
    	 	case VotoCategorico:
    	 		votoOrdinaleCategoricoUI();
    	 		break;
    	 	case VotoCategoricoConPreferenze:
    	 		votoCategoricoPreferenzeUI();
    	 		break;
    	 	case Referendum:
    	 		Label txt = new Label("Domanda: ");
    	 		txt.setMinWidth(100);
    	 		txt.setMaxWidth(100);
    	 		
    	 		TextField domanda = new TextField();
    	 		domanda.setMinWidth(300);
    	 		domanda.setMinWidth(300);
    	 		domanda.textProperty().addListener((obs, oldval, newval)->{
    	 			data = newval;
    	 		});
    	 		
    	 		container.getChildren().add(txt);
    	 		container.getChildren().add(domanda);
    	}
    		
    }
    
    /**
     * Crea e gestisce la UI per il voto categorico con preferenze
     */
    private void votoCategoricoPreferenzeUI() {
    	ComboBox<String> gruppi = new ComboBox<>();  
    	gruppi.setPromptText("Elenco dei gruppi");
    	gruppi.setMaxWidth(300);
 		gruppi.setMinWidth(300);
 		container.getChildren().add(gruppi);
 		
 		TextField nomegruppo = new TextField();
 		nomegruppo.setPromptText("Nome gruppo: ");
 		nomegruppo.setMinWidth(100);
 		nomegruppo.setMaxWidth(100);
 		container.getChildren().add(nomegruppo);
 		
 		ComboBox<String> preferenze = new ComboBox<>();
 		preferenze.setPromptText("Elenco preferenze");
 		preferenze.setMinWidth(200);
 		preferenze.setMaxWidth(200);
 		container.getChildren().add(preferenze);
 		
 		TextField nomepref = new TextField();
 		nomepref.setPromptText("Nome:");
 		nomepref.setMinWidth(300);
 		nomepref.setMaxWidth(300);
 		container.getChildren().add(nomepref);
 		
 		TextField cognomepref = new TextField();
 		cognomepref.setPromptText("Cognome:");
 		cognomepref.setMinWidth(300);
 		cognomepref.setMaxWidth(300);
 		container.getChildren().add(cognomepref);
 		
 		Button addpref = new Button("Aggiungi preferenza");
 		addpref.setMinWidth(150);
 		addpref.setMaxWidth(150);
 		addpref.setOnAction((e)->{
 			if (nomepref == null || nomepref.getText().isBlank()) {
 				textError.setText("Inserire nome della preferenza");
 				return;
 			}
 			
 			if (cognomepref == null || cognomepref.getText().isBlank()) {
 				textError.setText("Inserire cognome della prefereza");
 				return;
 			}
 			preferenze.getItems().add(nomepref.getText() + " " + cognomepref.getText());
 			nomepref.setText("");
 			cognomepref.setText("");
 		});
 		container.getChildren().add(addpref);
 		
 		Button addgruppo = new Button("Aggiungi gruppo");
 		addgruppo.setMinWidth(150);
 		addgruppo.setMaxWidth(150);
 		container.getChildren().add(addgruppo);
 		addgruppo.setOnAction((e)->{
 			if (nomegruppo == null || nomegruppo.getText().isBlank()) {
 				textError.setText("Inserire nome del gruppo");
 				return;
 			}
 			
 			if (preferenze.getItems().size() == 0) {
 				textError.setText("Inserire almeno una preferenza");
 				return;
 			}
 			
 			String str = nomegruppo.getText() + "(";
 			
 			for (int i=0; i< preferenze.getItems().size();i++) {
 				str+= preferenze.getItems().get(i);
 				if (i<preferenze.getItems().size()-1) str+=",";
 			}
 			
 			str += ")";
 			
 			nomegruppo.setText("");
 			gruppi.getItems().add(str);
 			//formato dati per il db TODO
 			//data += str; .....
 		});
    }
    
    private void votoOrdinaleCategoricoUI() {
    	removeNode = new ArrayList<>();
    	
    	ComboBox<String> candidati = new ComboBox<>();  
    	candidati.setPromptText("Elenco candidati");
    	candidati.setMaxWidth(300);
 		candidati.setMinWidth(300);
    	
 		ComboBox<String> addnew = new ComboBox<>();
 		addnew.setPromptText("Tipo candidato");
 		addnew.setOnAction((e) -> {
 			for (Node m : removeNode) {
 				container.getChildren().remove(m);
 			}
 			removeNode = new ArrayList<>();
 			
 			TextField nome = new TextField();
 			nome.setPromptText("Nome: ");
 			nome.setMaxWidth(300);
 	 		nome.setMinWidth(300);
 			
 	 		TextField cognome = new TextField();
 			cognome.setMaxWidth(300);
 	 		cognome.setMinWidth(300);
 			cognome.setPromptText("Cognome: ");
			
 			TextField partito = new TextField();
 			partito.setMaxWidth(300);
 	 		partito.setMinWidth(300);
			partito.setPromptText("Partito: ");
			
			container.getChildren().add(nome);
			removeNode.add(nome);
 			if (addnew.getValue().equals("Candidato")) {
 				container.getChildren().add(cognome);
 				container.getChildren().add(partito);
 				removeNode.add(cognome);
 				removeNode.add(partito);
 			}
 			Button ok = new Button("Inserisci");
 			ok.setId("b");
 			ok.setOnAction((ev)-> {
 				boolean res = aggiungiNuovoVotabile(candidati, addnew.getValue(), nome.getText(), cognome.getText(), partito.getText());
 				if (res) {
	 				nome.setText("");
	 				cognome.setText("");
	 				partito.setText("");
 				}
 			});
 			container.getChildren().add(ok);
 			removeNode.add(ok);
 		});
 		
 		addnew.getItems().add("Gruppo");
 		addnew.getItems().add("Candidato");
 		container.getChildren().add(addnew);
 		container.getChildren().add(0,candidati);
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

