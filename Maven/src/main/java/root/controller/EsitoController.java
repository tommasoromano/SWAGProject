package root.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.text.Font;
import javafx.scene.control.Label;
import root.util.Scheda;
import root.util.LogManager;
import root.App;
import root.controller.DBManager;

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
	
	/**
	 * Calcola la UI dell'esito della scheda
	 * @param s, la scheda
	 * @param voti, i voti espressi su essa
	 */
	private void calcolaVincitoreUI(Scheda s, String []voti) {
		switch (s.getTipoVincitore().getTipo()) {
		case Maggioranza:
			calcolaMaggioranzaUI(s, voti, false);
			break;
		case MaggioranzaAssoluta:
			calcolaMaggioranzaUI(s, voti, true);
			break;
		case ReferendumSenzaQuorum:
			calcoloReferendumUI(s, voti, false);
			break;
		case ReferendumConQuorum:
			calcoloReferendumUI(s, voti, true);
			break;
		default:
			break;
		}
	}
	
	/**
	 * Genera la ui conforme al referendum
	 * @param s, la scheda su cui calcolare la maggioranza
	 * @param voti, i voti espressi sulla scheda
	 * @param quorum, se true indica referendum con quorum, altrimenti senza
	 */
	private void calcoloReferendumUI(Scheda s, String [] voti, boolean quorum) {
		Label domanda = new Label(s.getDatiVoto().getDomanda());
		container.getChildren().add(domanda);
		
		int si=0;
		int no=0;
		int bianche=0;
		int nulle=0;
		for (String str : voti) {
			if (str.equals("Si")) {
				si++;
			} else if (str.equals("No")) {
				no++;
			} else if (str.equals("SB")) {
				bianche++;
			} else if (str.equals("SN")) {
				nulle++;
			}
		}
		
		if (quorum) {
			int dim = voti.length % 2 == 0 ? voti.length/2 : voti.length/2 + 1;
			if (si+no+bianche < dim) {
				Label label = new Label("Quorum non raggiunto");
				label.setFont(Font.font(35));
				label.setMinWidth(550);
				label.setMaxWidth(550);
				container.getChildren().add(label);
				return;
			}	
		}
		
		Label esito = null;
		if (si == no)  {
			esito = new Label("Parità: "+ si + " sì, " + no + " no");
		} else {			
			esito = si > no ? new Label("Maggioranza di sì: " + si + " a " + no) : new Label("Maggioranza di no: "+ no +" a " + si);
		}
		esito.setFont(Font.font(25));
		esito.setMinWidth(600);
		esito.setMaxWidth(600);
		
		Label schnulle = new Label("Schede nulle: " + nulle);
		schnulle.setFont(Font.font(25));
		schnulle.setMinWidth(600);
		schnulle.setMaxWidth(600);
		
		Label schbianche = new Label("Schede bianche: " + bianche);
		schbianche.setFont(Font.font(25));
		schbianche.setMinWidth(600);
		schbianche.setMaxWidth(600);
		
		container.getChildren().addAll(esito, schnulle, schbianche);
	}
	
	/**
	 * Calcola la UI di una votazione a seconda del tipo di voto e della modalità del conteggio
	 * @param s, la scheda della votazione
	 * @param voti, i voti espressi sulla scheda
	 * @param assoluta, true se modalità di calcolo del vincitore è maggioranza assoluta, false altrimenti
	 */
	private void calcolaMaggioranzaUI(Scheda s, String []voti, boolean assoluta) {
		switch (s.getTipoVoto().getTipo()) {
		case VotoOrdinale:
			break;
		case VotoCategorico:
			List<Pair> candidati = new ArrayList<>();
			int sn = 0;
			for (String voto : voti) {
				if (voto.equals("SN")) {
					sn++;
					break;
				}
				boolean found = false;
				for (int i=0; i<candidati.size(); i++) {
					if (candidati.get(i).getNome().equals(voto)) {
						candidati.get(i).add(1);
						found = true;
					}
					if (!found) candidati.add(new Pair(voto));
				}
			}
			
			Collections.sort(candidati);
			
			if (candidati.size()==0) {
				container.getChildren().add(new Label("Scheda senza elementi"));
				return;
			}
			
			if (assoluta) {
				int dim = (voti.length-sn)/2 +1;
				if (candidati.get(candidati.size()-1).getVoti() >= dim) {
					container.getChildren().add(new Label("Vinciore: " + candidati.get(candidati.size()-1).getNome() + " con " + candidati.get(candidati.size()-1).getVoti()));
					return;
				} else {
					container.getChildren().add(new Label("Maggioranza assoluta non raggiunta"));
					return;
				}
			}
			
			//controllo che ci sia uno o più vincitori
			int old = -1;
			container.getChildren().add(new Label("Vincitore/i: "));
			for (int i=candidati.size()-1; i>=0; i--) {
				int act = candidati.get(i).getVoti();
				Label winner = new Label(candidati.get(i).getNome() + " con " + candidati.get(i).getVoti());
				winner.setMinWidth(300);
				winner.setMaxWidth(300);
				container.getChildren().add(winner);
				if (old == act) break;
				old = act;
			}
			break;
		case VotoCategoricoConPreferenze:
			for (String voto : voti) {
				List<Pair> vincitori = new ArrayList<>();
				
				String [] cands = voto.split(":");
				for (String str : cands) {
					String [] singolovoto = str.split("\\(");
					String candidato = singolovoto[0];
					String ordine = singolovoto[1].substring(0, singolovoto[1].length()-1);
					System.out.println(candidato + " " + ordine);
					//TODO
				}
			}
			break;
		default :
			break;
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
	
	/*
	 * Classe di supporto per gestire i candidati come coppia nome-voto 
	 */
	private class Pair implements Comparable<Pair> {
		private String nome;
		private int voti;
		
		public Pair(String nome) {
			this.nome = nome;
			this.voti = 0;
		}
		
		@Override
		public int compareTo(Pair p) {
			return Integer.compare(voti, p.voti);
		}
		
		public void add(int n) {
			voti += n;
		}
		
		public String getNome() {
			return nome;
		}
		
		public int getVoti() {
			return voti;
		}
	}
 }