import java.io.File;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class AddBookPopupController {

	@FXML 
	private TextField authorNameField;
	
	@FXML 
	private TextField bookNameField;
	
	@FXML
    private ImageView thumbnailImageView;
	
	private File thumbnailFile;
	
	private DashboardController dashboardController;
	
	private Book currentBook;
	
	@FXML
	private void handleSubmit() {
		String bookName = bookNameField.getText();
        String authorName = authorNameField.getText();
        
        String bookCoverPath = "";
        if (thumbnailFile != null) {
        	bookCoverPath = thumbnailFile.getPath();
        }        
        
        if (currentBook == null) {
            // Create a new book and add it to the list
            dashboardController.addBook(bookName, authorName, bookCoverPath);
        } else {
            // Update the existing book's details
            currentBook.setBookName(bookName);
            currentBook.setAuthorName(authorName);
            currentBook.setCoverImage(bookCoverPath);

            // Refresh the list view to reflect changes
            dashboardController.updateBook(currentBook);
        }

        // Close the popup
        Stage stage = (Stage) bookNameField.getScene().getWindow();
        stage.close();
        
	}
	
	@FXML
	private void handleCancel() {
		 Stage stage = (Stage) bookNameField.getScene().getWindow();
	     stage.close();
	}
    
	
	@FXML
	private void chooseThumbnail() {
		 FileChooser fileChooser = new FileChooser();
	        fileChooser.setTitle("Select Book Thumbnail");
	        fileChooser.getExtensionFilters().addAll(
	                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
	        );

	        // Show open file dialog
	        File file = fileChooser.showOpenDialog(thumbnailImageView.getScene().getWindow());
	        if (file != null) {
	            // Set the selected file to the thumbnailFile
	            thumbnailFile = file;

	            // Display the selected image in the ImageView
	            Image image = new Image(file.toURI().toString());
	            thumbnailImageView.setImage(image);
	        }
	}
    
	// Set the main controller
    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }
    
 // Method to set book details in the popup
    public void setBookDetails(Book book) {
        currentBook = book;
        bookNameField.setText(book.getBookName());
        authorNameField.setText(book.getAuthorName());
        
        // Load the cover image if it exists
        if (!book.getCoverImage().isEmpty()) {
        	thumbnailFile = new File(book.getCoverImage());
            Image coverImage = new Image("file:" + book.getCoverImage());
            thumbnailImageView.setImage(coverImage);
        } else {
            thumbnailImageView.setImage(null); // Clear the ImageView
        }
    }

}
