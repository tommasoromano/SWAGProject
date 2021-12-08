package root;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadView("HomeView"), 1280, 720);
        navigate("HomeView");
        stage.setScene(scene);
        stage.setTitle("Vota Online");
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.setResizable(false);   
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
}
