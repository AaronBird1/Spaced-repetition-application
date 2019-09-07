package org.openjfx.spacedrepetition;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class CardsPageController implements Initializable {
	
	private static int deckNum;
	private ArrayList<Card> cardList = new ArrayList<Card>();
	private int cardId = 0;
	@FXML Text cardQuestion;
	@FXML Text correctStatus;
	@FXML private TextField usersAnswer;
	@FXML Button nextQuestionButton;
	@FXML Button submitButton;
	@FXML Text cardLevel;
	@FXML Button backButton;
	@FXML Button homeButton;

	//switches to the home page
	@FXML
    private void switchToHome() throws IOException {
    	System.out.println("\n\nswitchToHome method in CardsPageController class has been activated...\n\n");
        App.setRoot("primary");
	}
	
	//switches back to the deck page
	@FXML
    private void switchBack() throws IOException {
    	System.out.println("\n\nswitchBack method in CardsPageController class has been activated...\n\n");
        App.setRoot("deckpage");
	}
	
	//checks whether the users submitted answer is correct and updates the page and database accordingly
	@FXML
	private Boolean submitAnswer() {
		System.out.println("Users answer: " + usersAnswer.getText());
		if(usersAnswer.getText().equals(cardList.get(cardId).getAnswer())) {
			System.out.println("This is correct answer!");
			correctStatus.setText("Correct answer!");
			//cardList.get(cardId).goUpALevel();
			nextQuestionButton.setVisible(true);
			submitButton.setVisible(false);
			usersAnswer.setText("");
			int level = cardList.get(cardId).getLevel() + 1;
			cardLevel.setText("Current level: " + level);
			if(cardList.get(cardId) != null) {
				cardList.get(cardId).cardRemembered(true);
			}
			return true;
		}
		else {
			System.out.println("This is wrong answer!");
			submitButton.setVisible(false);
			nextQuestionButton.setVisible(true);
			correctStatus.setText("Wrong! The answer was " + cardList.get(cardId).getAnswer());
			usersAnswer.setText("");
			usersAnswer.setVisible(false);
			cardLevel.setText("Current level: 0");
			if(cardList.get(cardId) != null) {
				cardList.get(cardId).cardRemembered(false);
			}
			return false;
		}
	}
	
	@Override
    public void initialize(final URL url, final ResourceBundle rb) {
		setupButtons();
		
		nextQuestionButton.setVisible(false);
    	System.out.println("initialize method in CardsPageController has been activated...");
    	setCards();
    	checkCardDates();
    	if(cardList.size() != 0) { 
    		cardQuestion.setText(cardList.get(0).getQuestion());
    		cardLevel.setText("Current level: " + cardList.get(0).getLevel());
    	}
    	else {
    		cardQuestion.setText("There are no cards to study today!");
    		submitButton.setVisible(false);
    		usersAnswer.setVisible(false);
    	}
    }
	
	//setsup the custom buttons
	public void setupButtons() {
		CustomButton cb1 = new CustomButton(homeButton, "home");
		CustomButton cb2 = new CustomButton(backButton, "back");
	}
	
	//sets the deck id variable for this class
	public void setNum(int newnum) {
		deckNum = newnum;
	}
	
	//sets the cardList arrayList of cards to the cards that match the deckId in the database
	public void setCards() {
		System.out.println("setCardQuestions method in CardsPageController class has been activated...");
		System.out.println("method is looking for cards in the deck with id " + deckNum);
		Db db = new Db();
		cardList = db.returnCards(deckNum);
	
	}
	
	//removes all cards from the card arrayList that aren't meant to be studied today
	public void checkCardDates() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		long time = System.currentTimeMillis();
		Date todaysDate = new Date(time);
		String todayDate = sdf.format(todaysDate);
		int i = 0;
		while(i+1<=cardList.size()) {
			String cardsDate = sdf.format(cardList.get(i).getStudyDate());
			System.out.println("cards date is " + cardsDate);
			System.out.println("todays date is " + todayDate);
			SpacingAlgorithm sa = new SpacingAlgorithm();
			if(sa.checkCardDate(cardList.get(i)) == true) {
				i++;
			}
			else if(cardsDate.contentEquals(todayDate)) {
				i++;
			}
			else { 
				System.out.println("Removed card " + i);
				
				cardList.remove(i);
			}
		}
	}
	
	//this method is called when next button is pressed, loads next question
	public void nextQuestion() {
		System.out.println("nextQuestion method in CardsPageController class has been activated...");
		if(cardId + 2 <= cardList.size()) {
			cardId++;
			cardQuestion.setText(cardList.get(cardId).getQuestion());
			System.out.println("The study date is: " + cardList.get(cardId).getStudyDate());
			submitButton.setVisible(true);
			nextQuestionButton.setVisible(false);
			correctStatus.setText("");
			usersAnswer.setText("");
			usersAnswer.setVisible(true);
			cardLevel.setText("Current level: " + cardList.get(cardId).getLevel());
		}
		else {
			cardQuestion.setText("No more cards to study!");
			nextQuestionButton.setVisible(false);
			correctStatus.setText("");
			usersAnswer.setText("");
			usersAnswer.setVisible(false);
			cardLevel.setText("");
		}
	}
	
}
