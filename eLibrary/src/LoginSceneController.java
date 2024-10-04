import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;



public class LoginSceneController {
	
	@FXML 
	private TextField userName;
	
	@FXML 
	private PasswordField password;
	
	@FXML
	private void handleSubmitAction() {
		String emailText = userName.getText();
		String passText = password.getText();
		
		if (loginUser(emailText, passText)) {
	        // Login successful, perform necessary actions
	        System.out.println("User logged in successfully");
	        // Add code here to navigate to the next screen or update UI
	    } else {
	        // Login failed
	        System.out.println("Login failed");
	        // You might want to clear the password field or show an error message
	    }
	}
	


	private boolean loginUser(String email, String password) {
		
	    // Load MySQL JDBC driver (optional for newer versions)
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	    } catch (ClassNotFoundException e) {
	        System.out.print("COULD NOT LOAD jdbc driver");
	        e.printStackTrace();
	        return false;
	    }
	    
	    // SQL query for selecting user from users table
	    String selectSQL = "SELECT * FROM users WHERE email = ? AND password = ?";
	    
	    try (Connection conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USER, Constants.DB_PASSWORD);
	         PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
	        
	        // Set the values for the prepared statement
	        pstmt.setString(1, email);
	        pstmt.setString(2, password);
	        
	        // Execute the query
	        ResultSet rs = pstmt.executeQuery();
	        
	        // Check if a user was found
	        if (rs.next()) {
	        	Constants.showAlert("LOGIN SUCCESS");
	        	openDashboard();
	            return true;
	        } else {
	        	Constants.showAlert("LOGIN FAILED");
	            return false;
	        }
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        Constants.showAlert("LOGIN ERROR");
	        return false;
	    }
	}

	private void openDashboard() {
		javafx.stage.Stage currentStage = (javafx.stage.Stage) userName.getScene().getWindow();
        currentStage.close();
        
		try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("eLibrary2024/Dashboard.fxml"));
            Parent root = loader.load();

            // Get the controller and set details
        

            Stage dashboardStage = new Stage();
            dashboardStage.setTitle("Dashboard");
            dashboardStage.setScene(new Scene(root));

            // Show the new stage
            dashboardStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@FXML
	public void openRegister() {
		javafx.stage.Stage currentStage = (javafx.stage.Stage) userName.getScene().getWindow();
        currentStage.close();
        
		try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("eLibrary2024/RegisterScene.fxml"));
            Parent root = loader.load();

            // Get the controller and set details
        

            Stage dashboardStage = new Stage();
            dashboardStage.setTitle("Dashboard");
            dashboardStage.setScene(new Scene(root));

            // Show the new stage
            dashboardStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
