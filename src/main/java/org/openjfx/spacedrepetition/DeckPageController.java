package org.openjfx.spacedrepetition;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class DeckPageController implements Initializable {
	
	private static int num;
	@FXML Text deckname;
	private static Deck deck;
	@FXML private Button homeButton;
	@FXML private Button backButton;
	@FXML private Button addNewCardButton;
	@FXML private Button statsButton;
	@FXML private Button switchToCardsButton;
	@FXML private Button switchToEditDeck;
	@FXML private Button deadlineButton;
	
	/*public TestPageController(int testnum) {
		System.out.println("TestPageController has been created.");
		num = testnum;
		System.out.println("The class has received the number " + num);
		try {
			System.out.println("About to attempt to set root to testpage...");
			App.setRoot("testpage");
			System.out.println("Do we ever get here?");
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
		
		/*FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("testpage.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		
		try {
			System.out.println("About to load fxmlLoader");
			fxmlLoader.load();
			System.out.println("Load completed");
		} catch(IOException e) {
			throw new RuntimeException(e);
		}*/
	//}
	
	@FXML Text testtext;
	
	//switches to the home page
	@FXML
    private void switchToHome() throws IOException {
    	System.out.println("\n\nswitchToHome method in DeckPageController class has been activated...\n\n");
        App.setRoot("primary");
    }
	
	//switches back to the select deck page
	@FXML
    private void backPage() throws IOException {
    	System.out.println("\n\nbackPage method in DeckPageController class has been activated...\n\n");
        App.setRoot("secondary2");
    }
	
	//switches to the study cards page
	@FXML
	private void switchToCards() throws IOException {
		System.out.println("\n\nswitchToCardsPage method in DeckPageController class has been activated\n\n");
		CardsPageController cpc = new CardsPageController();
		cpc.setNum(num);
		App.setRoot("cardspage");
	}
	
	//switches to the page to add new cards to a deck
	@FXML
	private void switchToAddCard() throws IOException {
		System.out.println("\n\nswitchToAddCard method in DeckPageController class has been activated\n\n");
		AddCardPageController apc = new AddCardPageController();
		apc.setDeckId(num);
		App.setRoot("addcardpage");
	}
	
	//switches to the page to edit a decks cards and information
	@FXML
	private void switchToEditDeck() throws IOException {
		System.out.println("\n\nswitchToEditDeck method in DeckPageController class has been activated\n\n");
		EditDeckPageController edpc = new EditDeckPageController();
		edpc.setDeckId(num);
		edpc.setDeck(deck);
		App.setRoot("editdeckpage");
		
	}
	
	//switches to the page to view the statistics for a deck
	@FXML
	private void switchToDeckStatsPage() throws IOException {
		System.out.println("\n\nswitchToDeckStatsPage method in DeckPageController class has been activated\n\n");
		
		DeckStatsPageController dspc = new DeckStatsPageController();
		dspc.setNum(num);
		System.out.println("test point 1");
		dspc.setDeck(deck);
		System.out.println("test point 2");
		App.setRoot("deckstatspage");
		System.out.println("test point 3");
	}
	
	//switches to the page to edit the deadline for this deck
	@FXML
	private void switchToEditDeckDeadlinePage() throws IOException {
		System.out.println("\n\nswitchToEditDeckDeadlinePage method in DeckPageController class has been activated\n\n");
		
		EditDeckDeadlinePageController eddpc = new EditDeckDeadlinePageController();
		eddpc.setDeckId(num);
		
		App.setRoot("editdeckdeadlinepage");
	}
	
	@Override
    public void initialize(final URL url, final ResourceBundle rb) {
    	System.out.println("initialize method in DeckPageController has been activated...");
    	System.out.println("The id of the deck is: " + num);
    	checkDeadline();
    	testtext.setText(deck.getDeckDescription());
    	deckname.setText("Deck name: " + deck.getDeckName());
    	
    	setupButtons();
    	
    }
	
	//sets up the custom buttons for this page
	public void setupButtons() {
		CustomButton cb = new CustomButton(homeButton, "home");
    	CustomButton cb2 = new CustomButton(backButton, "back");
		CustomButton cb3 = new CustomButton(addNewCardButton, "newcard");
		CustomButton cb4 = new CustomButton(statsButton, "stats");
		CustomButton cb5 = new CustomButton(switchToCardsButton, "study");
		CustomButton cb6 = new CustomButton(switchToEditDeck, "editdeck");
		CustomButton cb7 = new CustomButton(deadlineButton, "editdeadline");
	}
	
	//sets the num (deck id) variable to a new int
	public void setNum(int newnum) {
		System.out.println("setNum method in DeckPageController class has been activated...");
		System.out.println("The new num is: " + newnum);
		num = newnum;
	}
	
	//sets the deck to a new inputted deck
	public void setDeck(Deck newDeck) {
		deck = newDeck;
	}
	
	//checks the deadline for the deck in the database with id 'num' and removes it deadline if it has passed
	private void checkDeadline() {
		System.out.println("checkDeadline method in DeckPageController class has been activated...");
		LocalDate todaysDate = LocalDate.now();
		
		if(deck.getDeckDeadline() != null) {
			if(deck.getDeckDeadline().toLocalDate().isBefore(todaysDate)) {
				System.out.println("The deadline has gone!!");
				Db database = new Db();
				database.removeDeckDeadline(num);
			}
			else {
				System.out.println("The deadline is not gone!");
			}
		}
	}
}
