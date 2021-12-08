package root.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class RegistrationController {
	private Scene scene;
	private Stage stage;
	private Parent root;
	
	@FXML
	private Button button;
	
	@FXML
	private PasswordField password;
	
	@FXML
	private PasswordField confirm_psw;
	
	@FXML
	private TextField username;
	
	@FXML
	private Label registrationLabel;
	
	@FXML
	void handleRegistration(ActionEvent action) {
		if (!password.getText().equals(confirm_psw.getText())) {
			registrationLabel.setText("Inserted passwords are different");
			return;
		}
		try {
			DBController db = DBController.getInstance();
			boolean res = db.insertUserPsw(username.getText(), password.getText());
			if (res) {
				registrationLabel.setText("Successful registration");
				//switchToLogin(action);
			} else {
				registrationLabel.setText("Registration failure: username invalid");
			}
		} catch (Exception e) {
			registrationLabel.setText("An internal error has occurred, please retry");
			e.printStackTrace();
		}
	}
	
	public void switchToLogin(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
	@FXML
	void keyPressed(KeyEvent key) {
		if (key.getCode().equals(KeyCode.ENTER)) {
			handleRegistration(null);
		}
	}
	
	@FXML
    void initialize() {
        assert registrationLabel != null : "fx:id=\"registrationLabel\" was not injected: check your FXML file 'RegElettore.fxml'.";
        assert button != null : "fx:id=\"button\" was not injected: check your FXML file 'RegElettore.fxml'.";
        assert password != null : "fx:id=\"password\" was not injected: check your FXML file 'RegElettore.fxml'.";
        assert username != null : "fx:id=\"username\" was not injected: check your FXML file 'RegElettore.fxml'.";
        assert confirm_psw != null : "fx:id=\"confirm_psw\" was not injected: check your FXML file 'RegElettore.fxml'.";

    }
}
