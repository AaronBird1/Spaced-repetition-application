package org.openjfx.spacedrepetition;

import java.sql.Date;
import java.util.ArrayList;

public class Deck {
	
	private Double deckId;
	private String deckName;
	private String deckDescription;
	private Date deckDeadline;
	private int deckOwner;
	private ArrayList<Card> cards;
	
	public Deck(String name, Double id) {
		System.out.println("New deck called " + name + " has been created!");
		this.deckName = name;
		this.deckId = id;
		
		Db database = new Db();
		this.cards = database.returnCards((int) Math.round(deckId));
	}
	
	public Deck(String name, Double id, int owner) {
		this.deckName = name;
		this.deckId = id;
		this.deckOwner = owner;
		Db database = new Db();
		this.cards = database.returnCards((int) Math.round(deckId));
	}
	
	public Deck(Double deckId, String deckName, String deckDescription, Date deckDeadline, int deckOwner) {
		Db database = new Db();
		
		this.deckId = deckId;
		this.deckName = deckName;
		this.deckDescription = deckDescription;
		this.deckDeadline = deckDeadline;
		this.deckOwner = deckOwner;
		this.cards = database.returnCards((int) Math.round(deckId));
	}
	
	//returns the name of the deck
	public String getDeckName() {
		return deckName;
	}
	
	//returns the deck's id
	public Double getDeckId() {
		return deckId;
	}
	
	//calls the getNumCardsToStudyInDeck method from the Db class and returns the number of cards there are to study in this deck today
	public int getNumCardsToStudy() {
		Db database = new Db();
		
		return database.getNumCardsToStudyInDeck(deckId);
	}
	
	//returns the id of the user that owns this deck
	public int getDeckOwner() {
		return deckOwner;
	}
	
	//returns the arrayList of cards in this deck
	public ArrayList<Card> getCards() {
		return cards;
	}
	
	//sets the arrayList of cards in this deck to a new arrayList of cards
	public void setCards(ArrayList<Card> newCards) {
		cards = newCards;
	}
	
	//returns the deck's description
	public String getDeckDescription() {
		return deckDescription;
	}
	
	//returns the deck's deadline
	public Date getDeckDeadline() {
		return deckDeadline;
	}
	
	//sets the deck's name to the new inputted name
	public void setDeckName(String newName) {
		deckName = newName;
	}

	//sets the deck's description to the new inputted description
	public void setDeckDescription(String newDescription) {
		deckDescription = newDescription;
	}
}
