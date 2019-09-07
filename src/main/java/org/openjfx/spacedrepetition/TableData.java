package org.openjfx.spacedrepetition;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class TableData {
	
	private SimpleStringProperty deckName;
    private SimpleDoubleProperty deckId;
    private SimpleDoubleProperty numCards;
    
    public TableData(Deck deck) {
    	System.out.println("TableData method 1, in TableData class, called...");
		this.deckName = new SimpleStringProperty(deck.getDeckName());
		this.deckId = new SimpleDoubleProperty(deck.getDeckId());
		this.numCards = new SimpleDoubleProperty(deck.getNumCardsToStudy());
	}
    
    public TableData(String name, Double id) {
    	System.out.println("TableData method 2, in TableData class, called...");
    	this.deckName = new SimpleStringProperty(name);
    	this.deckId = new SimpleDoubleProperty(id);
    }
    
    //returns the deckName
    public String getDeckName() {
    	System.out.println("getName method, in TableData class, called...");
    	return deckName.get();
    }
    
    //returns the deckId
    public Double getDeckId() {
    	System.out.println("getId method, in TableData class, called...");
    	return deckId.get();
    }
    
    //returns the numCards
    public Double getNumCards() {
    	System.out.println("getNumCards method in TableData class, called...");
    	return numCards.get();
    }
    
    //sets the deckName to a new inputted String
    public void setDeckName(final String name) {
    	this.deckName.set(name);
    }
    
    //sets the deckId to a new inputted Double
    public void setId(final Double id) {
    	this.deckId.set(id);
    }

}
