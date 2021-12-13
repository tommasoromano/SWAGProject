package root.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import root.App;
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
		tipoVoto.getSelectionModel().selectFirst();
		
		String[] mc = ModalitaConteggio.getAllTipi();
		for (int i = 0; i < mc.length; i++) {
			tipoVincitore.getItems().add(mc[i]);
		}
		tipoVincitore.getSelectionModel().selectFirst();
		
		/**
		 * TO-DO: descrizione.setText() in base a modalita di voto
		 */
		String desc = "- Voto ordinale e categorico: scrivere i partiti o candidati separandoli con :\n"
				+ "Esempio1 Partito1:Partito2:Partito3 Esempio2 Candidato1:Candidato2:Candidato3\n"
				+ "- Voto categorico con preferenze: scrivere i partiti separandoli con : "
				+ "e i candidati di ogni partito all'interno di () separandoli con :\n"
				+ "Esempio: Partito1(Candidato1:Candidato2):Partito2(Candidato3:Candidato4)\n";
		descrizione.setText(desc);
	}
	
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
    private ChoiceBox tipoVincitore;

    @FXML
    private ChoiceBox tipoVoto;

    @FXML
    void onActionCrea() {
    	if (checkCreaScheda()) {
    		App.navigate("ScrutinatoreView");
    	} 
    }
    
    void onActionModifica() {
    	if (checkCreaScheda()) {
    		App.navigate("ScrutinatoreView");
    	} 
    }
    
    private boolean checkCreaScheda() {
    	
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
		if (DBController.getInstance().creaScheda(
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

