package org.openjfx.spacedrepetition;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class SecondaryController implements Initializable{
	
	@FXML TextField newDeckNameField;
	@FXML TextField newDeckDescriptionField;
	@FXML private TableView<TableData> table; 
	private ObservableList<TableData> data = FXCollections.observableArrayList();
	UserSession userSession = UserSession.getInstance();
	@FXML private DatePicker calendar;
	@FXML private CheckBox deadlineChoiceBox;
	private ArrayList<Deck> newDeckList = new ArrayList<Deck>();
	@FXML private TableColumn idcol;
	@FXML private TableColumn namecol;
	@FXML private TableColumn numcol;
	@FXML private Button backButton;
	@FXML private Button homeButton;
	
	//sends the user back to the home page
	@FXML
    private void switchToHome() throws IOException {
		System.out.println("\n\nswitchToHome method in SecondaryController class has been activated...\n\n");
        App.setRoot("primary");
    }
	
	//sends the user to the deck page for the deck they have selected
	@FXML
	private void switchToDeckPage(Deck newDeck) throws IOException {
		System.out.println("\n\nswitchToDeckPage method in SecondaryController class has been activated...\n\n");
		DeckPageController dpc = new DeckPageController();
		dpc.setDeck(newDeck);
		App.setRoot("deckpage");
	}
	
	//attempts to create a new deck in the database with the information entered by the user
	@FXML
    private void createNewDeck() throws IOException {
    	System.out.println("\n\ncreateNewDeck method in SecondaryController class has been activated...\n\n");
    	
    	String newDeckName = newDeckNameField.getText();
    	String newDeckDescription = newDeckDescriptionField.getText();
    	LocalDate selectedDate = null;
    	
    	System.out.println("The inputted deck name is: " + newDeckName);
    	
    	if(deadlineChoiceBox.isSelected() && calendar.getValue() != null) {
    		System.out.println("The check box has been selected and the date chosen is: " + calendar.getValue());
			selectedDate = calendar.getValue();
    		System.out.println("The date that has been selected is: " + selectedDate);
    	}
    	else {
    		System.out.println("The check box has not been selected!");
    	}
    	
    	if(!newDeckName.isBlank() && !newDeckDescription.isBlank()) {
    		Db database = new Db();
        	
        	database.createNewDeck(newDeckName, newDeckDescription, userSession.getUserId(), selectedDate);
        	
        	App.setRoot("secondary2");
    	}
    	else {
    		System.out.println("At least one of the fields is empty");
    	}
    }
	
	//deletes the deck chosen by the user from the database
	@FXML
	private void deleteDeck() throws IOException {
		System.out.println("\n\ndeleteDeck method in SecondaryController class has been activated...\n\n");
		
		int index = table.getSelectionModel().selectedIndexProperty().get(); 
		
		if(index >= 0) {
			System.out.println("The deckId we are getting is: " + (int) Math.round(data.get(index).getDeckId()));
			Db database = new Db();
			database.deleteDeck((int) Math.round(data.get(index).getDeckId()));
			App.setRoot("secondary2");
    	}
		else {
			System.out.println("There is no card selected to delete");
		}
	}
	
	//displays a calendar for the user to select a deadline from
	@FXML
	private void pickDate() {
		calendar.setVisible(true);
	}
    
    @Override
    public void initialize(final URL url, final ResourceBundle rb) {
    	System.out.println("initialize method in SecondaryController has been activated...");

    	UserSession us = UserSession.getInstance();
    	
    	System.out.println("The userId that is logged in is: " + us.getUserId());
    	
    	
    	table.setItems(data);
    	System.out.println("About to activate retrieveData method");
    	populate(retrieveData());
    	System.out.println("initialize method completed...");
    	setTableClickable();
    	
    	calendar.setVisible(false);
    	
    	idcol.setSortable(false);
    	namecol.setSortable(false);
    	numcol.setSortable(false);

    	CustomButton cb = new CustomButton(backButton, "back");
    	CustomButton cb2 = new CustomButton(homeButton, "home");
    }
    
    private List<Deck> retrieveData() {
    	System.out.println("retrieveData method activated...");
    	try {
    		Db dbconnection = new Db();
    		newDeckList = dbconnection.returnDeckList(userSession.getUserId());
    	} catch(Exception e) {
    		System.out.println("Caught something!");
    	}
    	return newDeckList;
    }
    
    private void populate(final List<Deck> deck) {
    	System.out.println("populate method in SecondaryController class activated...");
    	
    	//System.out.println("The first deckName in the list is: " + deck.get(0).getDeckName());
    	//System.out.println("The second deckName in the list is: " + deck.get(1).getDeckName());
    	
    	deck.forEach(p -> data.add(new TableData(p)));	
    	
    	System.out.println("The number of entries in data thing is " + data.size());
    	
    	System.out.println("populate method completed...");
    }
    
    
    //THIS IS THE OLD SETTABLECLICKABLE METHOD I DONT THINK I NEED THIS ANYMORE!
    /*private void setTableClickable2() {
    	System.out.println("setTableClickable method has been activated...");
    	int index = table.getSelectionModel().selectedIndexProperty().get();
    	System.out.println("Row number " + index + " has been clicked!");
    	
    	if(index >= 0) {
    		try {
    			System.out.println("Testpoint 1");
    			DeckPageController dpc = new DeckPageController();
    			System.out.println("The deckId we are getting is: " + (int) Math.round(data.get(index).getDeckId()));
    			System.out.println("Testpoint 2");
    	    	dpc.setNum((int) Math.round(data.get(index).getDeckId()));
    	    	System.out.println("Testpoint 3");
    			switchToDeckPage();
    			System.out.println("Testpoint 4");
    		} catch(IOException e) {
    			System.out.println("IOException has been caught!");
    		}
    		
			Db dbconnection = new Db();
    		dbconnection.returnDeckInfo(index + 1);
    	}
    	
    	index = -1; 
    	
    	table.getSelectionModel().selectedIndexProperty().addListener((num) -> setTableClickable());
    }*/
    
    //sets up the table of decks so that on two clicks the user will be sent to the deck they have chosen
    private void setTableClickable() {
    	table.setOnMouseClicked(new EventHandler<MouseEvent>() {
    		@Override
    		public void handle(MouseEvent click) {
    			if(click.getClickCount() == 2) {
    				System.out.println("A row has been clicked twice!");
    				
    				int index = table.getSelectionModel().selectedIndexProperty().get();
    				
    				if(index >= 0) {
    		    		try {
    		    			System.out.println("Testpoint 1");
    		    			DeckPageController dpc = new DeckPageController();
    		    			System.out.println("The deckId we are getting is: " + (int) Math.round(data.get(index).getDeckId()));
    		    			System.out.println("Testpoint 2");
    		    	    	dpc.setNum((int) Math.round(data.get(index).getDeckId()));
    		    	    	System.out.println("Testpoint 3");
    		    			switchToDeckPage(newDeckList.get(index));
    		    			System.out.println("Testpoint 4");
    		    		} catch(IOException e) {
    		    			System.out.println("IOException has been caught!");
    		    		}
    		    		
    					Db dbconnection = new Db();
    		    		dbconnection.returnDeckInfo(index + 1);
    		    		
    		    	}
    				
    				index = -1; 
    			}
    			
    			else if(click.getClickCount() == 1) {
    				int index = table.getSelectionModel().selectedIndexProperty().get();
    				
    				String name = newDeckList.get(index).getDeckName();
    				System.out.println(name);
    			}
    			
    			}
    	});
    }
    
    //THIS IS THE OLD WAY OF DOING THIS NEEDS TO BE UPDATED!!!
    //sets up the styling for the back button
    private void setupBackButton(Button backButton) {
    	Image image = new Image(getClass().getResourceAsStream("squarebutton.png"));
        backButton.setStyle("-fx-background-color: transparent;");
        backButton.setGraphic(new ImageView(image));
        backButton.setPrefHeight(49);
        backButton.setPrefWidth(49);
        //backButton.setStyle("-fx-background-color: transparent; -fx-background-image: url('/src/main/resources/org/openjfx/spacedrepetition/squarebutton.png');");
        //backButton.setBackground(new ImageView(image));
    }
    
}