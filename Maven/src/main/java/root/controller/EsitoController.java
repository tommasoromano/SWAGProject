package root.controller;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;
import javafx.geometry.Pos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import root.util.ModalitaConteggio;
import root.util.ModalitaVoto;
import root.util.Scheda;
import root.util.VotoEspresso;
import root.App;
import root.DBManager;
import root.LogManager;

public class EsitoController extends Controller {
	
	@FXML
	private VBox container;
	
	@FXML
	private Button buttonIndietro;
	
	@Override
	public void init(Object param) {
		if (param instanceof Scheda) {
			String [] voti = DBManager.getInstance().getVotiOfScheda((Scheda)param);
			calcolaVincitoreUI((Scheda)param, voti);
		} else {
			LogManager.getInstance().logException(new IllegalArgumentException("Parametro non valido"));
		}
	}
	
	private int totVoti;
	
	/**
	 * Calcola la UI dell'esito della scheda
	 * @param s, la scheda
	 * @param voti, i voti espressi su essa
	 */
	private void calcolaVincitoreUI(Scheda s, String []voti) {
		this.totVoti = voti.length;
		// crea header
		String header = creaHeader(s);
		header += "\nNumero di voti: " + totVoti;
		Label labelHeader = new Label(header);
		container.getChildren().add(labelHeader);
		
		// calcolo esito
		String esito = "\nEsito votazione: ";
		
		VotoEspresso[] conteggio = VotoEspresso.conteggioVoti(s, voti);
		
		esito += calcoloEsito(s, conteggio);
		
		labelHeader.setText(header+esito);
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
	
	private String calcoloEsito(Scheda s, VotoEspresso[] voti) {
		
		// get data and create chart
		ObservableList<PieChart.Data> data =
                FXCollections.observableArrayList();
		
		int sb = 0;
		int sn = 0;
		List<VotoEspresso> votiSort = new ArrayList<>();
		String preferenze = "";
		for (int i = 0; i < voti.length; i++) {
			int v = voti[i].getConteggio();
			float p = (float) (((float)(v)/(float)(totVoti))*100.0);
			String n = voti[i].getVotabile();
			if (n.equals("SB")) {
				n = "Scheda bianca";
				sb = voti[i].getConteggio();
			} else if (n.equals("SN")) {
				n = "Scheda nulla";
				sn = voti[i].getConteggio();
			} else {
				votiSort.add(voti[i]);
				if (s.getTipoVoto().getTipo().equals(ModalitaVoto.Tipo.VotoCategoricoConPreferenze)) {
	        		// creo dati preferenze
					Map<String, Integer> pref = voti[i].getPreferenze();
	        		preferenze += "\n- Preferenze per " + n + " ("+v+"):";
	        		for (String key: pref.keySet()) {
	        			int vp = pref.get(key);
	        			float pp = (float) (((float)(vp)/(float)(v))*100.0);
	        			preferenze += "\n"+key+": "+String.format("%.2f", pp)+"% ("+vp+")";
	        		}
	        	}
			}
			data.add(new PieChart.Data(
					n+" "+String.format("%.2f", p)+"% ("+v+")", 
					v));
		}
		HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER);
		hbox.setSpacing(20);
        PieChart chart = new PieChart(data);
        hbox.getChildren().add(chart);
        if (!preferenze.equals("")) hbox.getChildren().add(new Label(preferenze));
        container.getChildren().add(hbox);
        
        // calcola esito
        Collections.sort(votiSort);
        Collections.reverse(votiSort);
        if (s.getTipoVoto().getTipo().equals(ModalitaVoto.Tipo.Referendum)) {
        	
        	// calcolo per referendum
        	boolean quorum = s.getTipoVincitore().getTipo().equals(ModalitaConteggio.Tipo.ReferendumConQuorum);
        	
        	if (quorum) {
    			int dim = voti.length % 2 == 0 ? (voti.length-sn)/2 : (voti.length-sn)/2 + 1;
    			if (voti.length-sn < dim) {
    				return "Quorum non raggiunto";
    			}	
    		}
    		
    		if (votiSort.size() == 0) {
    			return "Scheda senza voti validi";
    		} else if (votiSort.size() == 2)  {
    			if (votiSort.get(0).getConteggio() == votiSort.get(1).getConteggio()) {
    				return "Parità";
    			}else {			
        			return "Maggioranza di " + votiSort.get(0).getVotabile();
        		}
    		} else {			
    			return "Maggioranza di " + votiSort.get(0).getVotabile();
    		}
        } else {
        	
        	// calcolo per categorico e ordinale
        	
        	boolean assoluta = s.getTipoVincitore().getTipo().equals(ModalitaConteggio.Tipo.MaggioranzaAssoluta);
        	
        	if (assoluta) {
	        	int dim = (voti.length-sn)/2 +1;
				if (votiSort.get(0).getConteggio() >= dim) {
					return "Vincitore assoluto: " + votiSort.get(0).getVotabile();
				} else {
					return "Maggioranza assoluta non raggiunta";
				}
			}
        	
        	if (votiSort.size() == 0) {
    			return "Scheda senza voti validi";
    		} else if (votiSort.size() >= 2)  {
    			String vincitori = "" + votiSort.get(0).getVotabile();
    			for (int i = 1; i < votiSort.size(); i++) {
    				// guarda se ci sono parita
    				if (votiSort.get(i-1).getConteggio() == votiSort.get(i).getConteggio()) {
    					vincitori += ", " + votiSort.get(i).getVotabile();
    				} else {
    					return vincitori;
    				}
    			}
    			return vincitori;
    		} else {			
    			return "Vincitore: " + votiSort.get(0).getVotabile();
    		}
        	
        }
	}
	
	@FXML
	void onActionIndietro() {
		 if (App.getInstance().getScrutinatore() == null && App.getInstance().getElettore() != null) {
			 App.navigate("ElettoreView");
		 } else if (App.getInstance().getScrutinatore() != null) {
			 App.navigate("ScrutinatoreView");
		 }
	}
 }