package org.openjfx.spacedrepetition;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class InfoPage2Controller implements Initializable {
	
	@FXML Button backButton;
	@FXML Button homeButton;

	//switches back to the home page
    @FXML
    private void switchToHome() throws IOException {
    	System.out.println("\n\nswitchToHome method in InfoPageController class has been activated...\n\n");
        App.setRoot("primary");
    }
    
    @Override
    public void initialize(final URL url, final ResourceBundle rb) {
    	System.out.println("initialize method in LoginPageController has been activated...");
    	HomeButton hb = new HomeButton(homeButton);
    	//BackButton bb = new BackButton(backButton);
    	CustomButton cb = new CustomButton(backButton, "back");
    	
    }
  
}
