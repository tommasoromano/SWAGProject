package root.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import javafx.geometry.Insets;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
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
		domanda.setFont(Font.font(20));
		container.getChildren().add(domanda);
		
		if (voti.length == 0) {
			Label err = new Label("Scheda senza voti");
			err.setFont(Font.font(25));
			container.getChildren().add(err);
			return;
		}
		
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
			int dim = voti.length % 2 == 0 ? (voti.length-nulle)/2 : (voti.length-nulle)/2 + 1;
			if (si+no+bianche < dim) {
				Label label = new Label("Quorum non raggiunto");
				label.setFont(Font.font(35));
				container.getChildren().add(label);
				return;
			}	
		}
		
		Label esito = null;
		if (si == 0 && no == 0) {
			esito = new Label("Scheda senza voti validi: " + nulle + " schede nulle e " + bianche + " schede bianche");
			esito.setFont(Font.font(25));
			container.getChildren().add(esito);
		}
			
		if (si == no)  {
			esito = new Label("Parità: "+ si + " sì, " + no + " no");
		} else {			
			esito = si > no ? new Label("Maggioranza di sì: " + si + " a " + no) : new Label("Maggioranza di no: "+ no +" a " + si);
		}
		esito.setFont(Font.font(25));
		
		Label schnulle = new Label("Con " + nulle + " schede nulle e " + bianche +" schede bianche");
		schnulle.setFont(Font.font(25));
		
		container.getChildren().addAll(esito, schnulle);
	}
	
	/**
	 * Calcola la UI di una votazione a seconda del tipo di voto e della modalità del conteggio
	 * @param s, la scheda della votazione
	 * @param voti, i voti espressi sulla scheda
	 * @param assoluta, true se modalità di calcolo del vincitore è maggioranza assoluta, false altrimenti
	 */
	private void calcolaMaggioranzaUI(Scheda s, String []voti, boolean assoluta) {
		String dativoto = Arrays.toString(s.getDatiVoto().getCandidati());
		Label lab = new Label("Candidati: " + dativoto.substring(1,dativoto.length()-1));
		lab.setFont(Font.font(30));
		container.getChildren().add(lab);
		
		if (voti == null || voti.length == 0) {
			Label noVotes = (new Label("Scheda senza voti"));
			noVotes.setFont(Font.font(30));
			container.getChildren().add(noVotes);
			return;
		}
		
		List<Pair> candidati = new ArrayList<>();
		int sn = 0;
		int sb = 0;
		System.out.println(Arrays.toString(voti));
		
		switch (s.getTipoVoto().getTipo()) {
		case VotoOrdinale:

			for (String voto : voti) {
				if (voto.equals("SN")) {
					sn++;
				} else if (voto.equals("SB")) {
					sb++;
				} else {
					String [] cand = voto.split(":");
					for (String candidato_singolo : cand) {
						String [] cand_voto = candidato_singolo.split("\\("); 
						String nome = cand_voto[0];
						int ordine = Integer.valueOf(cand_voto[1].substring(0, cand_voto[1].length()-1));
						
						boolean found = false;
						for (int i=0; i<candidati.size(); i++) {
							if (candidati.get(i).getNome().equals(nome)) {
								candidati.get(i).aggiungi(ordine);
								found = true;
								break;
							}
						}
						
						if (!found) candidati.add(new Pair(nome, ordine));
					}
				}
			}
			
			if (candidati.size() == 0) {
				Label noValid = new Label("Scheda senza voti validi: " + sn + " schede nulle e " + sb + " schede bianche");
				noValid.setFont(Font.font(20));
				container.getChildren().add(noValid);
				return;
			}
			
			Collections.sort(candidati);
			
			if (assoluta) {
				//TODO
			}
			
			Label w = new Label("Vincitore/i:");
			w.setFont(Font.font(25));
			container.getChildren().add(w);
			
			//controllo ordine dei vincitori
			for (int i=0; i<candidati.size(); i++) {			
				Label winner = new Label(candidati.get(i).getNome() + " in posizione " + (i+1));
				winner.setFont(Font.font(25));
				winner.setPadding(new Insets(0,0,0,10));
				container.getChildren().add(winner);
			}
			
			//schede bianche-nulle
			Label valid = new Label("Con " + sn + " schede nulle e " + sb + " schede bianche");
 			valid.setFont(Font.font(20));
 			container.getChildren().add(valid);
 			
			break;
		case VotoCategorico:
			
			for (String voto : voti) {
				if (voto.equals("SN")) {
					sn++;
				} else if (voto.equals("SB")) {
					sb++;
				} else  {
					boolean found = false;
					for (int i=0; i<candidati.size(); i++) {
						if (candidati.get(i).getNome().equals(voto)) {
							candidati.get(i).aggiungi(1);
							found = true;
							break;
						}
					}
					if (!found) candidati.add(new Pair(voto));
				}
			}
			
			if (candidati.size() == 0) {
				Label noValid = new Label("Scheda senza voti validi: " + sn + " schede nulle e " + sb + " schede bianche");
				noValid.setFont(Font.font(20));
				container.getChildren().add(noValid);
				return;
			}
			
			Collections.sort(candidati);
			
			if (assoluta) {
				int dim = (voti.length-sn)/2 +1;
				Label maggAss=null;
				if (candidati.get(candidati.size()-1).getVoti() >= dim) {
					maggAss = new Label("Vinciore/i: " + candidati.get(candidati.size()-1).getNome());
					maggAss.setFont(Font.font(25));
					container.getChildren().add(maggAss);
				} else {
					maggAss = new Label("Maggioranza assoluta non raggiunta");
					maggAss.setFont(Font.font(25));
					container.getChildren().add(maggAss);
				}
				return;
			}
			
			//controllo che ci sia uno o più vincitori			
			container.getChildren().add(new Label("Vincitore/i: "));
			for (int i=candidati.size()-1; i>=0; i--) {
				
				//se voti attuali sono diversi dal massimo stop
				if (candidati.get(candidati.size()-1).getVoti() != candidati.get(i).getVoti()) break;
				
				Label winner = new Label(candidati.get(i).getNome() + " con " + candidati.get(i).getVoti() + " voti");
				winner.setFont(Font.font(25));
				
				container.getChildren().add(winner);
			}
			
			Label nulle = new Label("Con " + sn + " schede nulle e " + sb + " schede bianche su " + voti.length + " voti totali");
			nulle.setFont(Font.font(25));
			container.getChildren().add(nulle);
			
			break;
		case VotoCategoricoConPreferenze:
			List<Pair> preferenza = new ArrayList<>();
			
			for (String voto : voti) {
				
				if (voto.equals("SN")) {
					sn++;
				} else if (voto.equals("SB")) {
					sb++;
				} else {
					String [] gruppo_pref = voto.split("\\(");
					String gruppo = gruppo_pref[0];
					String preferenzaGruppo = gruppo_pref[1].substring(0, gruppo_pref[1].length()-1);
					
					//aggiungo gruppo a lista gruppi
					boolean found = false;
					for (int i=0; i<candidati.size(); i++) {
						if (candidati.get(i).getNome().equals(gruppo)) {
							candidati.get(i).aggiungi(1);
							found = true;
							break;
						}
					}
					if (!found) candidati.add(new Pair(gruppo));
					
					 //aggiungo preferenza a lista preferenza
					found = false;
					for (int i=0; i<preferenza.size(); i++) {
						if (preferenza.get(i).getNome().equals(gruppo+":"+preferenzaGruppo)) {
							preferenza.get(i).aggiungi(1);
							found = true;
							break;
						}
					}			
					if (!found) preferenza.add(new Pair(gruppo+":"+preferenzaGruppo));
				}				
			}
			
			if (candidati.size() == 0) {
				Label noValid = new Label("Scheda senza voti validi: " + sn + " schede nulle e " + sb + " schede bianche");
				noValid.setFont(Font.font(20));
				container.getChildren().add(noValid);
				return;
			}
			
			Collections.sort(candidati);
			Collections.sort(preferenza);
			
			if (assoluta) {
				int dim = (voti.length-sn)/2 +1;
				if (candidati.get(candidati.size()-1).getVoti() >= dim) {
					Label l = new Label("Vincitore: " + candidati.get(candidati.size()-1).getNome());
					l.setFont(Font.font(25));
					container.getChildren().add(l);
					//trova preferenza e aggiungi a UI
					for (Pair p : preferenza) {
						if (p.getNome().contains(candidati.get(candidati.size()-1).getNome())) {
							Label pref= new Label(p.getNome().split(":")[1] + " con " + p.getVoti() + " voti");
							pref.setPadding(new Insets(0,0,0,10));
							pref.setFont(Font.font(20));
							container.getChildren().add(pref);
						}
					}
				} else {
					Label magg = new Label("Maggioranza assoluta non raggiunta");
					magg.setFont(Font.font(25));
					container.getChildren().add(magg);
				}
				return;
			}
			
			//vincitori multipli
			Label wi = new Label("Vincitore/i: ");
			wi.setFont(Font.font(25));
			container.getChildren().add(wi);
			for (int i=candidati.size()-1; i>=0; i--) {
				
				//se voti attuali sono diversi dal massimo stop
				if (candidati.get(candidati.size()-1).getVoti() != candidati.get(i).getVoti()) break;
				
				Label winner = new Label(candidati.get(i).getNome() + " con " + candidati.get(i).getVoti() + " voto/i:");
				winner.setFont(Font.font(25));
				container.getChildren().add(winner);
				
				for (Pair p : preferenza) {
					if (p.getNome().contains(candidati.get(i).getNome())) {
						Label pref= new Label(p.getNome().split(":")[1] + " con " + p.getVoti() + " voto/i");
						pref.setPadding(new Insets(0,0,0,10));
						pref.setFont(Font.font(20));
						container.getChildren().add(pref);
					}
				}
			}
			
			Label scnulle = new Label("Con " + sn + " schede nulle e " + sb + " schede bianche su " + voti.length + " voti totali");
			scnulle.setFont(Font.font(25));
			container.getChildren().add(scnulle);
			
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
			this.voti = 1;
		}
		
		public Pair(String nome, int voti) {
			this.nome = nome;
			this.voti = voti;
		}
		
		@Override
		public int compareTo(Pair p) {
			return Integer.compare(voti, p.voti);
		}
		
		public void aggiungi(int n) {
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