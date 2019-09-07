package org.openjfx.spacedrepetition;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.mindrot.jbcrypt.BCrypt;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LoginPageController implements Initializable {
	
	@FXML TextField usernamefield;
	@FXML TextField passwordfield;
	@FXML Text loginStatus;
	@FXML Button loginButton;
	@FXML Button newAccountButton;
	@FXML Button newAccountPageButton;
	@FXML Button loginPageButton;

	//checks the database to see if the username and password entered by the user match a row in the User table and if so logs them in
    @FXML
    private void attemptLogin() throws IOException {
    	System.out.println("\n\nattemptLogin method in LoginPageController class has been activated...\n\n");
    	Db database = new Db();
        if(database.checkLoginInfo(usernamefield.getText(), passwordfield.getText())) {
        	UserSession.setInstance(database.returnUserId(usernamefield.getText()));
        	App.setRoot("primary");
        }
        else {
        	usernamefield.setText("");
        	passwordfield.setText("");
        	loginStatus.setStyle("-fx-text-inner-color: red;");
        	loginStatus.setText("Incorrect password or username");
        }
    }
    
    //sets up the page so the user can create a new user account
    @FXML
    private void setupCreateAccount() throws IOException {
    	System.out.println("\n\nsetupCreateAccount method in LoginPageController class has been activated...\n\n");
    	usernamefield.setPromptText("new username");
    	passwordfield.setPromptText("new password");
    	loginStatus.setText("");
    	usernamefield.setText("");
    	passwordfield.setText("");
    	loginButton.setVisible(false);
    	newAccountButton.setVisible(true);
    	newAccountPageButton.setVisible(false);
    	loginPageButton.setVisible(true);
    }
    
    //sets up the page so the user can attempt to login
    @FXML
    private void setupLogin() throws IOException {
    	System.out.println("\n\nsetupLogin method in LoginPageController class has been activated...\n\n");
    	usernamefield.setPromptText("Username");
    	passwordfield.setPromptText("Password");
    	loginStatus.setText("");
    	usernamefield.setText("");
    	passwordfield.setText("");
    	loginButton.setVisible(true);
    	newAccountButton.setVisible(false);
    	newAccountPageButton.setVisible(true);
    	loginPageButton.setVisible(false);
    }
    
    //creates a new row in the User table in the database with the information entered by the user
    @FXML
    private void submitNewAccountInfo() throws IOException {
    	System.out.println("\n\nsubmitNewAccountInfo method in LoginPageController class has been activated...\n\n");
    	if(!usernamefield.getText().isBlank()  && !passwordfield.getText().isBlank()) {
    		Db database = new Db();
    		database.createNewUser(usernamefield.getText(), BCrypt.hashpw(passwordfield.getText(), BCrypt.gensalt()));
    		loginStatus.setStyle("-fx-text-inner-color: green;");
    		loginStatus.setText("account successfully create, try loggin in!");
        	newAccountButton.setVisible(false);
        	loginButton.setVisible(true);
        	usernamefield.setText("");
        	passwordfield.setText("");
    	}
    	else {
    		loginStatus.setText("neither field can be blank");
        	usernamefield.setText("");
        	passwordfield.setText("");
    	}
    }
    
    //initialises the page so the user can initially login, rather than create an account
    @Override
    public void initialize(final URL url, final ResourceBundle rb) {
    	System.out.println("initialize method in LoginPageController has been activated...");
    	loginButton.setVisible(true);
    	newAccountButton.setVisible(false);
    	loginPageButton.setVisible(false);
    	newAccountPageButton.setVisible(true);
    	
    	CustomButton cb = new CustomButton(loginPageButton, "back");
    	CustomButton cb2 = new CustomButton(loginButton, "login");
    	CustomButton cb3 = new CustomButton(newAccountPageButton, "newaccount");
    }
  
}
