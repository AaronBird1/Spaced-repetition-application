package org.openjfx.spacedrepetition;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class CardsTableData {
	private SimpleStringProperty cardQuestion;
    private SimpleStringProperty cardAnswer;
    private SimpleDoubleProperty cardId;
    
    public CardsTableData(Card card) {
    	System.out.println("TableData method 1, in TableData class, called...");
		this.cardQuestion = new SimpleStringProperty(card.getQuestion());
		this.cardAnswer = new SimpleStringProperty(card.getAnswer());
		this.cardId = new SimpleDoubleProperty(card.getId());
	}
    
    public CardsTableData(String question, String answer) {
    	System.out.println("CardsTableData method 2, in TableData class, called...");
    	this.cardQuestion = new SimpleStringProperty(question);
    	this.cardAnswer = new SimpleStringProperty(answer);
    }
    
    //returns the card question
    public String getCardQuestion() {
    	System.out.println("getQuestion method, in CardsTableData class, called...");
    	return cardQuestion.get();
    }
    
    //returns the card answer
    public String getCardAnswer() {
    	System.out.println("getAnswer method, in CardsTableData class, called...");
    	return cardAnswer.get();
    }
    
    //returns the card id 
    public Double getCardId() {
    	System.out.println("getCardId method, in CardsTableData class, called...");
    	return cardId.get();
    }
     
    //sets the simpleDoubleProperty cardQuestion with a new string
    public void setCardQuestion(final String question) {
    	this.cardQuestion.set(question);
    }
    
    //sets the simpleDoubleProperty cardAnswer with a new string
    public void setCardAnswer(final String answer) {
    	this.cardAnswer.set(answer);
    }

}