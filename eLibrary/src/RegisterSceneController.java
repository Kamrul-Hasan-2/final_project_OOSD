import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class RegisterSceneController {
	
	@FXML
    private Text msgText;
	
	@FXML 
	private TextField fullName;
	
	@FXML 
	private TextField email;
	
	@FXML 
	private PasswordField password;
	
	
	

	@FXML
	private void handleSubmitAction() {
		msgText.setText("");
		
		String nameText = fullName.getText();
		String emailText = email.getText();
		String passText = password.getText();
		
		if(nameText.isEmpty() || emailText.isEmpty() || passText.isEmpty()) {
			msgText.setText("Error: Fillup all.");
			return;
		}
		
		System.out.println("HELLO "+nameText+" : "+emailText+": "+passText);
		insertUser(nameText, emailText, passText);
	}
	
	// Method to handle the database insertion
    private boolean insertUser(String fullName, String email, String password) {
    	
    	// Load MySQL JDBC driver (optional for newer versions)
        try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.print("COULD NOT LOAD jdbc driver");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        // SQL query for inserting into users table
        String insertSQL = "INSERT INTO users (fullname, email, password) VALUES (?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USER, Constants.DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
        	

            // Set the value for the prepared statement
            pstmt.setString(1, fullName);
            pstmt.setString(2, email);
            pstmt.setString(3, password);

            // Execute the insertion
            int rowsAffected = pstmt.executeUpdate();
            
            Constants.showAlert("SUCCESS");
            // Check if the insertion was successful
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
	@FXML
	public void openLogin() {
		javafx.stage.Stage currentStage = (javafx.stage.Stage) email.getScene().getWindow();
        currentStage.close();
        
		try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("eLibrary2024/LoginScene.fxml"));
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
