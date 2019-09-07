package org.openjfx.spacedrepetition;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class AddCardPageController implements Initializable {
	
	private static int deckId;
	@FXML private TextField newQuestion;
	@FXML private TextField newAnswer;
	@FXML private Text status;
	@FXML Button homeButton;
	@FXML Button backButton;

	//switches to the home page
    @FXML
    private void switchToHome() throws IOException {
    	System.out.println("\n\nswitchToHome method in InfoPageController class has been activated...\n\n");
        App.setRoot("primary");
    }
    
    //switches back to the the deck page
    @FXML
    private void switchBack() throws IOException {
    	System.out.println("\n\nswitchBack method in InfoPageController class has been activated...\n\n");
        App.setRoot("deckpage");
    }
    
    //calls the createNewCard method in the Db class to create a new row in the card table with the info submitted by the user
    @FXML
    private void submitCardInfo() throws IOException {
    	System.out.println("\n\nsubmitCardInfo method in InfoPageController class has been activated...\n\n");
    	
    	if(!newQuestion.getText().isBlank() && !newAnswer.getText().isBlank()) {
    		String Question = newQuestion.getText();
        	String Answer = newAnswer.getText();
        	
        	Db database = new Db();
        	System.out.println("Trying to create a new card with id: " + deckId);
        	database.createNewCard(Question, Answer, deckId);
    	}
    	else {
    		status.setText("Error: at least one of the fields is blank");
    	}
    	
    }
    
    //runs setup methods when the page is loaded
    @Override
    public void initialize(final URL url, final ResourceBundle rb) {
		setupButtons();
    }
    
    //sets up the custom buttons for the add new card page page
    public void setupButtons() {
		CustomButton cb1 = new CustomButton(homeButton, "home");
		CustomButton cb2 = new CustomButton(backButton, "back");
	}
  
    //sets the deck id value for this class
    public void setDeckId(int newDeckId) {
    	deckId = newDeckId;
    }
}
