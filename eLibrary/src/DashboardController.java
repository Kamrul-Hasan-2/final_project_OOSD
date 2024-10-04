import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;


public class DashboardController {
	
	@FXML
    private ListView<Book> bookListView;
	
	private ObservableList<Book> bookList = FXCollections.observableArrayList();

	@FXML
    private void initialize() {
		System.out.println("INITILIZED DASHBOARD");
		bookListView.setItems(bookList);
		bookListView.setCellFactory(new Callback<ListView<Book>, ListCell<Book>>() {
            @Override
            public ListCell<Book> call(ListView<Book> listView) {
                return new BookCell();
            }
        });
	}
	
	@FXML
	private void openAddBookPopup() {
		try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("eLibrary2024/AddBookPopup.fxml"));
            Parent root = loader.load();

            // Get the controller of the popup
            AddBookPopupController controller = loader.getController();
            controller.setDashboardController(this);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add Book");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR, "Failed to open the popup.", ButtonType.OK);
            alert.showAndWait();
        }
	}
	
	public void addBook(String bookName, String authorName, String coverPath) {
        bookListView.getItems().add(new Book(bookName, authorName, coverPath));
    }
	
	public class BookCell extends ListCell<Book> {
		private HBox hbox = new HBox();	    
	    private Label bookLabel = new Label(); 
	    private Button deleteButton = new Button("Delete");
	    private Button editButton = new Button("Edit");
	    private Region spacerHorizontal = new Region();
	    private ImageView coverImageView = new ImageView(); // ImageView for the cover photo
	    
	    public BookCell() {
	        super();
	        
	        coverImageView.setFitHeight(125); // Set preferred height for the image
	        coverImageView.setFitWidth(100);
	        
	        
	        editButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
	        editButton.setOnAction(event -> {
	            Book book = getItem();
	            if (book != null) {
	                // Open the popup with pre-filled book details for editing
	                openEditPopup(book);
	            }
	        });
	        
	        deleteButton.setStyle("-fx-background-color: #fc6464; -fx-text-fill: white;");
	        deleteButton.setOnAction(event -> {
	            Book book = getItem();
	            getListView().getItems().remove(book);
	        });

	        // Allow the spacer to grow and fill available space
	        HBox.setHgrow(spacerHorizontal, Priority.ALWAYS);

	        // Add the label, spacer, and button to the HBox
	        hbox.getChildren().addAll(coverImageView, bookLabel, spacerHorizontal, editButton, deleteButton);

	        // Optional: Set some spacing between elements
	        hbox.setSpacing(10);
	    }

	    @Override
	    protected void updateItem(Book book, boolean empty) {
	        super.updateItem(book, empty);
	        if (empty || book == null) {
	            setText(null);
	            setGraphic(null);
	        } else {
	        	// Check if the book has a cover image
	            if (!book.getCoverImage().isEmpty()) {
	                // Load the image from the provided file path
	                Image coverImage = new Image("file:" + book.getCoverImage());
	                coverImageView.setImage(coverImage);
	            } else {
	                // Clear the ImageView or set a placeholder
	                coverImageView.setImage(null); // Alternatively, you can set a default image here
	                
	            }
	            
	            
	            // Update the label text with the book information
	            bookLabel.setText("Book: " + book.getBookName() + "\nAuthor: " + book.getAuthorName());

	            // Set the HBox as the cell's graphic
	            setGraphic(hbox);
	        }
	    }
	    
	    private void openEditPopup(Book book) {
	        try {
	            // Load the AddBookPopup FXML file
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("eLibrary2024/AddBookPopup.fxml"));
	            Parent root = loader.load();

	            // Get the controller and pass the selected book to it
	            AddBookPopupController controller = loader.getController();
	            controller.setDashboardController(DashboardController.this);
	            controller.setBookDetails(book); 

	            // Create a new stage for the popup
	            Stage stage = new Stage();
	            stage.setTitle("Edit Book");
	            stage.initModality(Modality.APPLICATION_MODAL); // Block interactions with other windows
	            stage.setScene(new Scene(root));
	            stage.showAndWait(); // Wait for the popup to close

	            // After popup is closed, refresh the list view
	            getListView().refresh();
	        } catch (Exception e) {
	            e.printStackTrace();
	            showAlert("Error", "Unable to load the edit popup.");
	        }
	    }
	}
	
	public void updateBook(Book book) {
	    // Find the book in the list and update its details
	    for (int i = 0; i < bookList.size(); i++) {
	        if (bookList.get(i).equals(book)) {
	            bookList.set(i, book);
	            break;
	        }
	    }
	    // Refresh the list view to show the updated details
	    bookListView.refresh();
	}
	

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
	
}
