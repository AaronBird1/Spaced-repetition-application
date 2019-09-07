package org.openjfx.spacedrepetition;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;

public class EditDeckDeadlinePageController implements Initializable {
	
	static int deckId;
	@FXML DatePicker deadlinePicker;
	@FXML Button homeButton;
	@FXML Button backButton;

	//switches to the home page
    @FXML
    private void switchToHome() throws IOException {
    	System.out.println("\n\nswitchToHome method in EditDeckDeadlinePageController class has been activated...\n\n");
        App.setRoot("primary");
    }
    
    //switches back to the deck page
    @FXML
    private void switchBack() throws IOException {
    	System.out.println("\n\nswitchBack method in EditDeckDeadlinePageController class has been activated...\n\n");
        App.setRoot("deckpage");
    }
    
    //sets the deadline of this deck in the database to the new deadline entered by the user in the application (can be NULL)
    @FXML
    private void setDeckDeadline() throws IOException {
    	System.out.println("\n\nsetDeckDeadline method in EditDeckDeadlinePageController class has been activated...");
    	
    	Db database = new Db();
    	
    	database.updateDeckDeadline(deckId, java.sql.Date.valueOf(deadlinePicker.getValue()));
    }
    
    //removes the deadline from the deck with id 'deckId'
    @FXML
    private void removeDeadline() throws IOException {
    	System.out.println("\n\nremoveDeadline method in EditDeckDeadlinePageController class has been activated...");
    	
    	Db database = new Db();
    	
    	database.updateDeckDeadline(deckId, null);
    	deadlinePicker.setValue(null);
    }
    
    @Override
    public void initialize(final URL url, final ResourceBundle rb) {
    	setupButtons();
    	
    	Db database = new Db();
    	java.sql.Date deadline = database.returnDeckDeadline(deckId);
    	
    	if(deadline != null) {
    		deadlinePicker.setValue(deadline.toLocalDate());
    	}
    	
    	System.out.println("initialize method completed...");
    }
    
    //sets up the custom buttons for the edit deck deadline page
    public void setupButtons() {
		CustomButton cb1 = new CustomButton(homeButton, "home");
		CustomButton cb2 = new CustomButton(backButton, "back");
	}
  
    //sets the deck id variable to a new inputted int
    public void setDeckId(int newDeckId) {
    	deckId = newDeckId;
    }
}