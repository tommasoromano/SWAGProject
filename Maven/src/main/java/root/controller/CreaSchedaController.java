package root.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import root.App;
import java.util.List;
import java.util.ArrayList;
import root.util.LogManager;
import root.util.Data;
import root.util.DatiVoto;
import root.util.ModalitaConteggio;
import root.util.ModalitaVoto;
import root.util.Scheda;

public class CreaSchedaController extends Controller {
	
	@Override
	public void init() {
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
	
	private List<Node> removeNode;
	
	@FXML
	private VBox parent;
	
    @FXML
    private Button buttonCrea;

    @FXML
    private Button buttonIndietro;

    @FXML
    private DatePicker dataFine;

    @FXML
    private DatePicker dataInizio;

    @FXML
    private TextArea datiVoto;

    @FXML
    private TextField nomeScheda;

    @FXML
    private Label descrizione;
    
    @FXML
    private Label textError;

    @FXML
    private ChoiceBox<String> tipoVincitore;
    
    private ModalitaVoto modalitaVoto;

    @FXML
    private ChoiceBox<String> tipoVoto;
    
    private ModalitaConteggio modalitaConteggio;

    @FXML
    void onActionCrea() {
    	if (checkCreaScheda()) {
    		LogManager.getInstance().logCreaScheda(App.getInstance().getScrutinatore(), nomeScheda.getText());
    		App.navigate("ScrutinatoreView");
    	} 
    }
    
    private void aggiungiNuovo(String tipo, TextField nome, TextField cognome, TextField partito) {
    	boolean res = false;
    	if (tipo.equals("Gruppo")) {
    		if (nome == null || nome.getText().isBlank()) {
    			textError.setText("Inserire nome del gruppo");
    			return;
    		}
    		res = DBManager.getInstance().insertPartito(nome.getText());
    		
    	} else if (tipo.equals("Candidato")) {
    		if (nome == null || nome.getText().isBlank()) {
    			textError.setText("Compilare campo nome");
    			return;
    		}
    		
    		if (cognome == null || cognome.getText().isBlank()) {
    			textError.setText("Compilare campo cognome");
    			return;
    		}
    		
    		if (partito==null || partito.getText().isBlank()) {
    			textError.setText("Compilare campo partito");
    			return;
    		}
    		
    		res = DBManager.getInstance().insertCandidato(nome.getText(), cognome.getText(), partito.getText());
    	}
    	if (!res) textError.setText("Inserimento non riuscito");
    	if (res) textError.setText("Inserito");
    }
    
    private void cambiaModalitaVoto(String n) {
    	modalitaVoto = new ModalitaVoto(n);
    	datiVoto.clear();
 		String text = "Istruzioni:\n";
 		ChoiceBox<String> candidati=null;
 		Label addnuovo=null;
    	switch(modalitaVoto.getTipo()) {
    	 	case VotoOrdinale:
    	 		removeNode = new ArrayList<>();
    	 		candidati = new ChoiceBox<>();  	 		
    	 		addnuovo = new Label("Aggiungi nuovo candidato o gruppo");
    	 		ChoiceBox<String> addnew = new ChoiceBox<>();
    	 		addnew.setOnAction((e) -> {
    	 			for (Node m : removeNode) {
    	 				parent.getChildren().remove(m);
    	 			}
    	 			removeNode = new ArrayList<>();
    	 			TextField nome = new TextField();
    	 			nome.setPromptText("Nome: ");
    	 			TextField cognome = new TextField();
    	 			cognome.setPromptText("Cognome: ");
	 				TextField partito = new TextField();
	 				partito.setPromptText("Partito: ");
	 				parent.getChildren().add(3, nome);
	 				removeNode.add(nome);
	 				int pos = 4;
    	 			if (addnew.getValue().equals("Candidato")) {
    	 				parent.getChildren().add(4, cognome);
    	 				parent.getChildren().add(5, partito);
    	 				removeNode.add(cognome);
    	 				removeNode.add(partito);
    	 				pos = 6;
    	 			}
    	 			Button ok = new Button("Inserisci");
    	 			ok.setId("b");
    	 			ok.setOnAction((ev)-> {
    	 				aggiungiNuovo(addnew.getValue(), nome, cognome, partito);
    	 			});
    	 			parent.getChildren().add(pos, ok);
    	 			removeNode.add(ok);
    	 		});
    	 		addnew.getItems().add("Gruppo");
    	 		addnew.getItems().add("Candidato");
    	 		parent.getChildren().add(1, addnuovo);
    	 		parent.getChildren().add(2, addnew);
    	 		
    	 		String [] cand = DBManager.getInstance().getCandidati();
    	 		
    	 		if (cand == null || cand.length == 0) return;
    	 		for (String c : cand) {
    	 			candidati.getItems().add(c);
    	 		}
   
    	 		parent.getChildren().add(1,candidati);
    	 		
    	 		text += "Inserire condidatio o gruppo o partito separandoli con \":\" esempio:\n"
    	 				+"Candidato1:Candidato2:Candidato3:Candidato4 etc";
    	 		descrizione.setText(text);
    	 		break;
    	 	case VotoCategorico:
    	 		for (Node m : removeNode) {
	 				parent.getChildren().remove(m);
	 			}
    	 		removeNode = new ArrayList<>();
    	 		text += "Inserire condidatio o gruppo o partito separandoli con \":\" esempio:\n"
    	 				+"Candidato1:Candidato2:Candidato3:Candidato4 etc";
    	 		descrizione.setText(text);
    	 		parent.getChildren().remove(addnuovo);
    	 		parent.getChildren().remove(candidati);
    	 		break;
    	 	case VotoCategoricoConPreferenze:
    	 		for (Node m : removeNode) {
	 				parent.getChildren().remove(m);
	 			}
    	 		parent.getChildren().remove(addnuovo);
    	 		parent.getChildren().remove(candidati);
    	 		removeNode = new ArrayList<>();
    	 		text += "Inserire gruppo o partito separandoli con \":\""
    	 		+"le preferenze dentro () separandole con \":\", esempio:\n"
    	 				+"Candidato1(Preferenza1:Preferenza2):Candidato2(Preferenza3:Preferenza4) etc";
    	 		descrizione.setText(text);
    	 		break;
    	 	case Referendum:
    	 		for (Node m : removeNode) {
	 				parent.getChildren().remove(m);
	 			}
    	 		parent.getChildren().remove(addnuovo);
    	 		parent.getChildren().remove(candidati);
    	 		removeNode = new ArrayList<>();
    	 		text += "Inserire domanda del Referendum";
    	 		descrizione.setText(text);
    	 		break;
    	}
    		
    }
    
    private void cambiaModalitaConteggio(String n) {
    	modalitaConteggio = new ModalitaConteggio(n);
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
		
		//nome
    	if (datiVoto.getText().length() == 0) {
    		textError.setText("Compila il campo Dati voto");
			return false;
    	}
		
		// cerca di creare scheda su DB
		if (DBManager.getInstance().creaScheda(
				nomeScheda.getText(), 
				di, 
				df, 
				new ModalitaVoto(tipoVoto.getValue().toString()),
				new DatiVoto(datiVoto.getText()), 
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

