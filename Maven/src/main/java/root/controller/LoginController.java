package root.controller;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

import com.google.common.hash.Hashing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label accesLabel;   

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    void handleAccess(MouseEvent event) {

    }

    @FXML
    void handleLogin(ActionEvent event) {
    	try {
    		DBController db = DBController.getInstance();
    		String psw = db.getPsw(username.getText());
    		String insertpsw = Hashing.sha256().hashString(password.getText(), StandardCharsets.UTF_8).toString();
    		if (psw.equals(insertpsw)) {
    			accesLabel.setText("Welcome");
    		}  else {
    			accesLabel.setText("Wrong password, not registered yet?");
    		}
    	} catch (Exception e) {
    		System.err.println("Could not connect to database");
    		e.printStackTrace();
    	}
    }   

    @FXML
    void handlePassword(ActionEvent event) {

    }

    @FXML
    void handleUsername(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert accesLabel != null : "fx:id=\"accesLabel\" was not injected: check your FXML file 'Login.fxml'.";
        assert loginButton != null : "fx:id=\"loginButton\" was not injected: check your FXML file 'Login.fxml'.";
        assert password != null : "fx:id=\"password\" was not injected: check your FXML file 'Login.fxml'.";
        assert username != null : "fx:id=\"username\" was not injected: check your FXML file 'Login.fxml'.";

    }

}
