package org.openjfx.spacedrepetition;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class EditDeckPageController implements Initializable {
	
	private static int deckId;
	@FXML private TextField newDeckNameField;
	@FXML private TextField newDeckDescriptionField;
	@FXML private Text status;
	@FXML private TableView<CardsTableData> cardsTable;
	@FXML private Button homeButton;
	@FXML private Button backButton;
	private static Deck deck;
	private ObservableList<CardsTableData> data = FXCollections.observableArrayList();

	//switches to the home page
    @FXML
    private void switchToHome() throws IOException {
    	System.out.println("\n\nswitchToHome method in EditDeckPageController class has been activated...\n\n");
        App.setRoot("primary");
    }
    
    //switches back to the deck page
    @FXML
    private void switchBack() throws IOException {
    	System.out.println("\n\nswitchBack method in EditDeckPageController class has been activated...\n\n");
    	DeckPageController dpc = new DeckPageController();
    	dpc.setDeck(deck);
        App.setRoot("deckpage");
    }
    
    //updates the deck name and description of both the deck object in this class and in the database
    @FXML
    private void submitChanges() throws IOException {
    	System.out.println("\n\nsubmitChanges method in EditDeckPageController class has been activated...\n\n");
    	
    	if(!newDeckNameField.getText().isBlank() && !newDeckDescriptionField.getText().isBlank()) {
    		System.out.println("Neither field is blanK!");
    		status.setVisible(false);
    		Db database = new Db();
        	
    		deck.setDeckName(newDeckNameField.getText());
    		deck.setDeckDescription(newDeckDescriptionField.getText());
        	database.editDeckName(newDeckNameField.getText(), deckId);
        	database.editDeckDescription(newDeckDescriptionField.getText(), deckId);
    	}
    	else {
    		System.out.println("At least one of the fields is blanK!");
    		status.setVisible(true);
    		status.setText("Please enter a name and a description for the deck");
    	}
    }
    
    //deletes the card selected by the user from the database
    @FXML
	private void deleteCard() throws IOException {
		System.out.println("\n\ndeleteCard method in EditDeckPageController class has been activated...\n\n");
		
		int index = cardsTable.getSelectionModel().selectedIndexProperty().get(); 
		
		if(index >= 0) {
			System.out.println("The cardId we are getting is: " + (int) Math.round(data.get(index).getCardId()));
			Db database = new Db();
			database.deleteCard((int) Math.round(data.get(index).getCardId()));
			App.setRoot("editdeckpage");
    	}
		else {
			System.out.println("There is no card selected to delete");
		}
	}
    
    //sets the deckId variable to a new inputted int
    public void setDeckId(int newDeckId) {
    	deckId = newDeckId;
    }
    
    @Override
    public void initialize(final URL url, final ResourceBundle rb) {
    	Db database = new Db();
    	String oldDeckName = database.returnDeckName(deckId);
		newDeckNameField.setText(oldDeckName);
		newDeckDescriptionField.setText(deck.getDeckDescription());
		
		cardsTable.setItems(data);
    	System.out.println("About to activate retrieveData method");
    	populate(retrieveData());
    	
    	setupButtons();
    	
    	System.out.println("initialize method completed...");
    }
    
    //setups the custom buttons for the edit deck page
    public void setupButtons() {
		CustomButton cb1 = new CustomButton(homeButton, "home");
		CustomButton cb2 = new CustomButton(backButton, "back");
	}
    
    //returns an ArrayList of cards retrieved from the database from the deck with id = 'deckId'
    private List<Card> retrieveData() {
    	ArrayList<Card> newCardList = new ArrayList<Card>();
    	System.out.println("retrieveData method in EditDeckPage class activated...");
    	try {
    		Db dbconnection = new Db();
    		newCardList = dbconnection.returnCards(deckId); //returns a list of cards in the currently selected deck.
    	} catch(Exception e) {
    		System.out.println("Caught something!");
    	}
    	return newCardList;
    }
    
    private void populate(final List<Card> cardList) {
    	System.out.println("populate method in EditDeckPageController class activated...");
    	
    	//System.out.println("The first deckName in the list is: " + deck.get(0).getDeckName());
    	//System.out.println("The second deckName in the list is: " + deck.get(1).getDeckName());
    	
    	cardList.forEach(p -> data.add(new CardsTableData(p)));	
    	
    	System.out.println("The number of entries in data thing is " + data.size());
    	
    	System.out.println("populate method completed...");
    }
    
    //sets the deck variable to a new inputted Deck
    public void setDeck(Deck newDeck) {
    	deck = newDeck;
    }
  
}