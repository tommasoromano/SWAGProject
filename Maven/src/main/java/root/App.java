package root;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import root.controller.DBController;
import root.util.*;

import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * JavaFX App
 */
public class App extends Application {

	public App() {
		
	}
	private static App _instance;
	
	public static App getInstance() {
		if (_instance == null) {
			_instance = new App();
		}
		return _instance;
	}
	
    private static Scene scene;
    
    private Elettore elettore;
    private Scrutinatore scrutinatore;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadView("HomeView"), 1280, 720);
        navigate("HomeView");
        stage.setScene(scene);
        stage.setTitle("Vota Online");
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.setResizable(true);   
        stage.show();
    }

    private static Parent loadView(String view) {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("view/" + view + ".fxml"));
        Parent parent = null;
		try {
			parent = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return parent;
    }

    public static void navigate(String view) {
    	var parent = loadView(view);
        scene.setRoot(parent);
    }

    public static void main(String[] args) {
        launch();
    }
    
    public void setElettore(Elettore e) {
    	this.elettore = e;
    }
    
    public Elettore getElettore() {
    	return this.elettore;
    }
    
    public void setScrutinatore(Scrutinatore s) {
    	this.scrutinatore = s;
    }
    
    public Scrutinatore getScrutinatore() {
    	return this.scrutinatore;
    }
}
