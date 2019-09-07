package org.openjfx.spacedrepetition;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;


public class Card {
	
	private int id;
	private String question;
	private String answer;
	private int deckId;
	private Date studyDate;
	private int level;
	private int numTimesStudied;
	private int numTimesRemembered;
	private int daysAdjustment;
	
	public Card(int id, String question, String answer, int deckId, Date studyDate, int level) {
		System.out.println("New card with id " + id + " has been created!");
		this.id = id;
		this.question = question;
		this.answer = answer;
		this.deckId = deckId;
		this.studyDate = studyDate;
		this.level = level;
	}
	
	public Card(int id, String question, String answer, int deckId, Date studyDate, int level, int numTimesStudied, int numTimesRemembered, int daysAdjustment) {
		System.out.println("New card with id " + id + " has been created!");
		this.id = id;
		this.question = question;
		this.answer = answer;
		this.deckId = deckId;
		this.studyDate = studyDate;
		this.level = level;
		this.numTimesStudied = numTimesStudied;
		this.numTimesRemembered = numTimesRemembered;
		this.daysAdjustment = daysAdjustment;
	}
	
	//returns the id of the card
	public int getId() {
		return id;
	}
	
	//returns the question from this card
	public String getQuestion() {
		return question;
	}
	
	//returns the answer from this card
	public String getAnswer() {
		return answer;
	}
	
	//returns the id of the deck this card is in
	public int getDeckId() {
		return deckId;
	}
	
	//returns the date this card is to be studied on
	public Date getStudyDate() {
		return studyDate;
	}
	
	//returns the level this card is in
	public int getLevel() {
		return level;
	}
	
	//returns the number of times this card has been studied
	public int getNumTimesStudied() {
		return numTimesStudied;
	}
	
	//returns the number of times this card has been recalled successfully
	public int getNumTimesRemembered() {
		return numTimesRemembered;
	}
	
	
	//checks if the card has a deadline, if not it increases its level by 1
	//WANT THIS TO BE CHECKING THE DEADLINE OF THE ACTUAL CARD!! NOT THE DATABASE WHEN CREATING THE CARD INITIALLY SHOULD PULL ALL DATA FROM THE DATABASE
	public void goUpALevel() {
		System.out.println("goUpALevel method in Card class has been activated...");
		Db database = new Db();
		Date deckDeadline = database.returnDeckDeadline(deckId);
		if(deckDeadline == null) {
			level++;
			System.out.println("card " + id + " now has level " + level);
			System.out.println("The deck doesnt have a deadline so level has been increased");
			database.updateCardLevel(id, level);
		}
		else {
			System.out.println("The deck has a deadline so level hasnt been increased");
		}
		setDateToToday();
		addDaysToDate(gapToNextStudySession());
		//addDaysToDate((int) Math.pow(2.0f, (double) level)); //consider if this needs to be double for high level
	}
	
	//sets the level of the card back to 0
	public void resetLevel() {
		System.out.println("resetLevel method in Card class has been activated...");
		level = 0;
		setDateToToday();
		addDaysToDate(gapToNextStudySession());
		
		Db database = new Db();
		
		database.updateCardLevel(id, 0);
		//addDaysToDate((int) Math.pow(2.0f, (double) level)); //consider if this needs to be double for high level
	}
	
	//add the inputted number of days to the date this card will be studied on, updates the database accordingly by calling updateCardDate method in Db class
	public void addDaysToDate(int days) {
		System.out.println("addDaysToDate method in Card class has been activated...");
		
		System.out.println("Adding " + days + " days to studyDate of card " + id);
		
		long time = System.currentTimeMillis();
		java.sql.Date todaysDate = new java.sql.Date(time);
		
		Calendar c = Calendar.getInstance();
		c.setTime(todaysDate);
		c.add(Calendar.DATE, days);
		
		java.sql.Date newDate = new java.sql.Date(c.getTimeInMillis());
		studyDate = newDate;
		
		System.out.println("The new date is " + newDate);
		
		Db database = new Db();
		System.out.println("The id of the card being updated is " + id);
		database.updateCardDate(id, newDate);
	}
	
	//updates the date this card should be studied on to today's date, updates the database accordingly by calling updateCardDate method in Db class
	public void setDateToToday() {
		System.out.println("setDateToToday method in Card class has been activated...");
		
		LocalDate todaysDate = LocalDate.now();
		studyDate = java.sql.Date.valueOf(todaysDate);
		Db database = new Db();
		database.updateCardDate(id, java.sql.Date.valueOf(todaysDate));
	}
	
	//calculates the ratio of times this card has been recalled correctly and returns this value as a double
	public double getRatioTimesRemembered() {
		System.out.println("getRatioTimesRemembered method in Card class has been activated...");
		
		if(numTimesRemembered >= 0 && numTimesStudied >= 0) {
			System.out.println("Both numTimesRemembered and numTimesStudied are positive");
			double ratio = ((double) numTimesRemembered /  (double) numTimesStudied);
			System.out.println("The ratio is: " + ratio);
			return ratio;
		}
		else {
			System.out.println("At least one of the times trackers is negative.");
			return 0;
		}
	}
	
	//updates numTimesStudied and numTimesRemembered values & calls daysAdjustment method respective of whether the input is true or false
	//an input of true means card has been remembered correctly
	//an input of false means card has been remembered incorrectly
	public void cardRemembered(Boolean status) {
		System.out.println("cardRemembered method in Card class has been activated...");
		
		Db database = new Db();
		
		if(status == true) {
			goUpALevel();
			numTimesRemembered++;
			numTimesStudied++;
			database.increaseNumTimesCardStudied(id);
			database.increaseNumTimesCardRemembered(id);
		}
		else if(status == false) {
			resetLevel();
			numTimesStudied++;
			database.increaseNumTimesCardStudied(id);
		}
		
		updateDaysAdjustment();
		
		System.out.println("numTimesRemembered = " + numTimesRemembered + " and numTimesStudied = " + numTimesStudied);
		System.out.println("The percentage of remembers is now: " + getRatioTimesRemembered());
	}
	
	//checks whether the ratio of number times the card has been remembered correctly is in requires range
	//if not updates daysAdjustment value accordingly
	public void updateDaysAdjustment() {
		System.out.println("udpateDaysAdjustment method in Card class has been activated...");
		
		Db database = new Db();
		
		//ADD A CHECK TO SEE WHETHER CARD HAS BEENS STUDIED ENOUGH TIMES TO ADJSUT HERE E.G. 5 TIME????
		
		if(getRatioTimesRemembered() < 0.85) {
			System.out.println("The ratio is: " + getRatioTimesRemembered() + " This is below required range");
			daysAdjustment--;
			database.increaseCardDaysAdjustment(id, false);
			//NEED TO UPDATE CARD DATABASE TABLE HERE!!!
		}
		else if(getRatioTimesRemembered() >= 0.95) {
			System.out.println("The ratio is: " + getRatioTimesRemembered() + " This is above required range");
			daysAdjustment++;
			database.increaseCardDaysAdjustment(id, true);
			//NEED TO UPDATE CARD DATABASE TABLE HERE!!!
		}
		else {
			System.out.println("The ratio is: " + getRatioTimesRemembered() + " This is in the required range");
		}
		
		System.out.println("daysAdjustment is now: " + daysAdjustment);
	}
	
	//increases this cards daysAdjustment value by 1
	public void increaseDaysAdjustment() {
		System.out.println("increaseDaysAdjustment method in Card class has been activated...");
		
		daysAdjustment++;
	} 
	
	//decreases this cards daysAdjustment value by 1
	public void decreaseDaysAdjustment() {
		System.out.println("decreaseDaysAdjustment method in Card class has been activaed...");

		daysAdjustment--;
		
	}
	
	//returns the daysAdjustment value for this card
	public int getDaysAdjustment() {
		System.out.println("getDaysAdjustment method in Card class has been activated...");
		
		return daysAdjustment;
	}
	
	//calculates the gap in days until the card should next be studied again
	public int gapToNextStudySession() {
		System.out.println("gapToNextStudySession method in Card class been activated...");
		
		int gap = (int) Math.pow(2, level) + daysAdjustment;
		
		if(gap <= 1) {
			gap = 1;
		}
		
		return gap;
		
	}

}