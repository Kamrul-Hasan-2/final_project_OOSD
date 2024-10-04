import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Constants {
	public static final String DB_URL = "jdbc:mysql://localhost:3306/lib";
	public static final String DB_USER = "root";
	public static final String DB_PASSWORD = "";
	
    public static void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Button Pressed");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
