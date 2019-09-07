package org.openjfx.spacedrepetition;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class PrimaryController implements Initializable {
	
	@FXML Text welcomeuser;
	UserSession usersession = UserSession.getInstance();
	Db database = new Db();
	@FXML Button homeButton;
	@FXML Button logoutButton;
	@FXML Button aboutButton;
	@FXML Button decksButton;

	//switches to the deck selection page
    @FXML
    private void switchToDecks() throws IOException {
    	System.out.println("\n\nswitchToDecks method in PrimaryController class has been activated...\n\n");
        App.setRoot("secondary2");
    }
    
    //switches to the application information page
    @FXML
    private void switchToInfoPage() throws IOException {
    	System.out.println("\n\nswitchToInfoPage method in PrimaryController class has been activated...\n\n");
        App.setRoot("infopage2");
    }
    
    /*
    @FXML
    private void switchtest() throws IOException {
    	System.out.println("\n\nswitchtest method in PrimaryController class has been activated...\n\n");
    	App.setRoot("secondary");
    }*/
    
    //logs the user out and sends them back to the login page
    @FXML
    private void logout() throws IOException {
    	System.out.println("\n\nlogout method in PrimaryController class has been activated...\n\n");
    	usersession.cleanUserSession();
    	App.setRoot("loginpage");
    }
    

	@Override
    public void initialize(final URL url, final ResourceBundle rb) {
    	System.out.println("initialize method in PrimaryController has been activated...");
    	welcomeuser.setText("Hello, " + database.returnUsername(usersession.getUserId()));
    	setupButtons();
    }
	
	//sets up the custom buttons for the home page
	private void setupButtons() {
		CustomButton cb = new CustomButton(homeButton, "home");
		CustomButton cb2 = new CustomButton(logoutButton, "logout");
		CustomButton cb3 = new CustomButton(aboutButton, "about");
		CustomButton cb4 = new CustomButton(decksButton, "decks");
	}
  
}
