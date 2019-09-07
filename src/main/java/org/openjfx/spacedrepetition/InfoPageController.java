package org.openjfx.spacedrepetition;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

public class InfoPageController implements Initializable {
	
	@FXML Text welcometext;

    @FXML
    private void switchToHome() throws IOException {
    	System.out.println("\n\nswitchToHome method in InfoPageController class has been activated...\n\n");
        App.setRoot("primary");
    }
    
    @Override
    public void initialize(final URL url, final ResourceBundle rb) {
    	System.out.println("initialize method in InfoPageController has been activated...");
    	welcometext.setWrappingWidth(600);
    	
    }
  
}
