package root.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import root.App;
import root.util.Data;

public class CreaSchedaController extends Controller {
	
	@Override
	public void init() {
		tipoVoto.getItems().add("Voto ordinale");
		tipoVoto.getItems().add("Voto categorico");
		tipoVoto.getItems().add("Voto categorico con preferenze");
		tipoVoto.getItems().add("Referendum");
		tipoVoto.getSelectionModel().selectFirst();
		
		tipoVincitore.getItems().add("Maggioranza");
		tipoVincitore.getItems().add("Maggioranza assoluta");
		tipoVincitore.getItems().add("Referendum senza quorum");
		tipoVincitore.getItems().add("Referendum con quorum");
		tipoVincitore.getSelectionModel().selectFirst();
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
    private TextField datiVoto;

    @FXML
    private TextField nomeScheda;

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
				tipoVoto.getValue().toString(),
				datiVoto.getText(), 
				tipoVincitore.getValue().toString())) {
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

