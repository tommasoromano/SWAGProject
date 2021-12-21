package root.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import root.*;
import root.util.Elettore;
import root.util.Scheda;
import root.util.Sesso;

public class ElettoreController extends Controller {
    
    @FXML
    private VBox parent;

    @FXML
    private Label textHead;
    
    @Override
    public void init() {
    	Elettore e = App.getInstance().getElettore();
    	String h = e.getNome() + " " + e.getCognome();
    	textHead.setText(h);
    	
    }

}
