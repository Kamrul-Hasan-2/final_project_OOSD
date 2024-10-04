package eLibrary2024;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginScene extends Application {

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
            Parent root = FXMLLoader.load(getClass().getResource("LoginScene.fxml"));

            // Create the scene with the loaded layout
            Scene scene = new Scene(root);

            // Set the title of the window
            primaryStage.setTitle("SMUCT e-Library Login");

            // Set the scene to the stage
            primaryStage.setScene(scene);

            // Show the window
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();	
        }
		
	}

}
