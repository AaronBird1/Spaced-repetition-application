import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.openjfx.spacedrepetition.Card;
import org.openjfx.spacedrepetition.Db;
 
class cardTests {
 
	@Test
	void setupCardTests() {
		Db database = new Db();
		
		cardGetMethodTests();
		testResetLevel();
		testGoUpALevel();
		testResetLevel();
		testAddDaysToDate();
		testSetDateToToday();
		testGetRatioTimesRemembered();
		testCardRemembered();
		testUpdateDaysAdjustment();
		testGapToNextStudySession();
		
		//database.createNewTestUser("test user", "test password", -1);
		//database.createNewTestDeck("test deck", "test deck description", -1, null, -1);
		//database.createNewTestCard("test question", "test answer", -1, -1);
		
		/*cardGetMethodTests();
		testGoUpALevel();
		testResetLevel();
		testCardRemembered();
		testIncreaseDaysDjustment();
		testDecreaseDaysDjustment();*/
	}
	
	void testcardGetMethodTests() {
		System.out.println("testcardGetMethodTests method in cardTests class activated...");
		Db database = new Db();
		
		Card testCard = database.returnCard(-1);
		
		System.out.println("The question is: " + testCard.getQuestion());
	}

    void cardGetMethodTests() {
    	String testQuestion = "This should be the new test question?";
		String testAnswer = "This should be the new test answer";
		int testId = -1;
		int testDeckId = -1;
		int testLevel = 666;
		LocalDate testDate = LocalDate.of(2002, 8, 7);
				
		Card testCard = new Card(testId, testQuestion, testAnswer, testDeckId, java.sql.Date.valueOf(testDate), testLevel);
		
		assert(testCard.getId() == -1);
		assert(testCard.getQuestion() == "This should be the new test question?");
		assert(testCard.getAnswer() == "This should be the new test answer");
		assert(testCard.getDeckId() == -1);
		assert(testCard.getStudyDate().toLocalDate().equals(testDate));
		assert(testCard.getLevel() == 666);
				
		System.out.println("\n\nAll test for cardGetMethodTests method have passed!\n\n");
    }
    
    void testGoUpALevel() {
    	int id = -1;
    	String question = "Test question from testGoUpALevel";
    	String answer = "Test answer from testGoUpALevel";
    	int deckId = -1;
    	java.sql.Date studyDate = java.sql.Date.valueOf(LocalDate.of(1800, 1, 1));
    	int level = 5;
    	int numTimesStudied = 9;
    	int numTimesRemembered = 2;
    	int daysAdjustment = 2;
    	
    	Card card1 = new Card(id, question, answer,deckId, studyDate, level, numTimesStudied, numTimesRemembered, daysAdjustment);
		Db database = new Db();
		database.updateCardData(card1);
		database.updateDeckDeadline(-1, null);
    	
		//Since the deck containing the card has no deadline the level of the card should increase by 1
    	assert(card1.getLevel() == 5);
    	card1.goUpALevel();
    	assert(card1.getLevel() == 6);
    	
    	LocalDate deadlineDate = LocalDate.now();
    	level = 3;
    	Card card2 = new Card(id, question, answer,deckId, studyDate, level, numTimesStudied, numTimesRemembered, daysAdjustment);
    	database.updateCardData(card2);
    	database.updateDeckDeadline(-1, java.sql.Date.valueOf(deadlineDate));
    	
    	//Since the deck containing the card now has a deadline the level of the card shouldn't increase
    	assert(card2.getLevel() == 3);
    	card2.goUpALevel();
    	assert(card2.getLevel() == 3);
    	
    	System.out.println("\n\nAll test for testGoUpALevel method have passed!\n\n");
    }

    void testResetLevel() {
    	Db database = new Db();
    	
    	int id = -1;
    	String question = "Test question from testResetLevel";
    	String answer = "Test answer from testResetLevel";
    	int deckId = -1;
    	java.sql.Date studyDate = java.sql.Date.valueOf(LocalDate.of(1800, 1, 1));
    	int level = 5;
    	int numTimesStudied = 9;
    	int numTimesRemembered = 2;
    	int daysAdjustment = 2;
    	
    	Card card1 = new Card(id, question, answer,deckId, studyDate, level, numTimesStudied, numTimesRemembered, daysAdjustment);
    	
    	database.updateCardData(card1);
    	card1 = database.returnCard(-1);
    	
    	assert(card1.getLevel() == 5);
    	
    	card1.resetLevel();
    	
    	//checks that the cards level has changed from 5 to 0
    	assert(card1.getLevel() == 0);
    	
    	card1 = database.returnCard(-1);
    	
    	//checks that the database has been updating accordingly
    	assert(card1.getLevel() == 0);
    	
    	System.out.println("\n\nAll test for testResetLevel method have passed!\n\n");
    }
    
    void testAddDaysToDate() {
    	int id = -1;
    	String question = "Test question from testAddDaysToDate";
    	String answer = "Test answer from testAddDaysToDate";
    	int deckId = -1;
    	java.sql.Date studyDate = java.sql.Date.valueOf(LocalDate.of(1800, 1, 1));
    	int level = 5;
    	int numTimesStudied = 9;
    	int numTimesRemembered = 2;
    	int daysAdjustment = 2;
    	
    	Card testCard = new Card(id, question, answer,deckId, studyDate, level, numTimesStudied, numTimesRemembered, daysAdjustment);
    	
    	testCard.addDaysToDate(4);
    	
    	//checks that the new study date of the testCard is 4 days in the future from today rather than in the past
    	assert(testCard.getStudyDate().toLocalDate().compareTo(LocalDate.now().plusDays(4)) == 0);
    }
    
    void testSetDateToToday() {
    	int id = -1;
    	String question = "Test question from testSetDateToToday";
    	String answer = "Test answer from testSetDateToToday";
    	int deckId = -1;
    	java.sql.Date studyDate = java.sql.Date.valueOf(LocalDate.of(1800, 1, 1));
    	int level = 5;
    	int numTimesStudied = 9;
    	int numTimesRemembered = 2;
    	int daysAdjustment = 2;
    	
    	Card testCard = new Card(id, question, answer,deckId, studyDate, level, numTimesStudied, numTimesRemembered, daysAdjustment);
    	
    	Db database = new Db();
    	database.updateCardData(testCard);
    	
    	//checks that the date of card -1 in database has been set to 01/01/1800
    	assert(testCard.getStudyDate().toLocalDate().compareTo(LocalDate.of(1800, 1, 1)) == 0);
    	assert(database.returnCard(-1).getStudyDate().toLocalDate().compareTo(LocalDate.of(1800, 1, 1)) == 0);
    	
    	testCard.setDateToToday();
    	
    	//checks that the card and its data in the database have both been updated to todays date
    	assert(testCard.getStudyDate().toLocalDate().compareTo(LocalDate.now()) == 0);
    	assert(database.returnCard(-1).getStudyDate().toLocalDate().compareTo(LocalDate.now()) == 0);
    }
    
    //tests that the getRatioTimesRemembered method is returning the correct ratio
    void testGetRatioTimesRemembered() {
    	int id = -1;
    	String question = "Test question from testSetDateToToday";
    	String answer = "Test answer from testSetDateToToday";
    	int deckId = -1;
    	java.sql.Date studyDate = java.sql.Date.valueOf(LocalDate.of(1800, 1, 1));
    	int level = 5;
    	int numTimesStudied = 25;
    	int numTimesRemembered = 5;
    	int daysAdjustment = 2;
    	
    	Card testCard = new Card(id, question, answer,deckId, studyDate, level, numTimesStudied, numTimesRemembered, daysAdjustment);
    	
    	assert(testCard.getRatioTimesRemembered() == (double) 0.2);
    }
    
    void testCardRemembered() {
    	int id = -1;
    	String question = "Test question";
    	String answer = "Test answer";
    	int deckId = -1;
    	java.sql.Date studyDate = java.sql.Date.valueOf(LocalDate.of(1800, 1, 1));
    	int level = 5;
    	int numTimesStudied = 9;
    	int numTimesRemembered = 2;
    	int daysAdjustment = 2;
    	
    	Card card1 = new Card(id, question, answer,deckId, studyDate, level, numTimesStudied, numTimesRemembered, daysAdjustment);
    			
    	card1.cardRemembered(true);
    	
    	assert(card1.getNumTimesRemembered() == 3);
    	assert(card1.getNumTimesStudied() == 10);
    	assert(card1.getRatioTimesRemembered() == 0.3);
    	assert(card1.getDaysAdjustment() == 1);
    	
    	card1.cardRemembered(false);
    	
    	assert(card1.getNumTimesRemembered() == 3);
    	assert(card1.getNumTimesStudied() == 11);
    	assert(card1.getRatioTimesRemembered() == ((double) 3 / (double) 11));
    	assert(card1.getDaysAdjustment() == 0);
    	
    	
    	System.out.println("\n\nAll test for testCardRemembered method have passed!\n\n");
    	
    }
    
    
    //TESTS THAT the updateDaysAdjustment method is updating the daysAdjusment value as expected according the remembered ratio
    void testUpdateDaysAdjustment() {
    	int id = -1;
    	String question = "Test question";
    	String answer = "Test answer";
    	int deckId = -1;
    	java.sql.Date studyDate = java.sql.Date.valueOf(LocalDate.of(1800, 1, 1));
    	int level = 5;
    	int numTimesStudied = 10;
    	int numTimesRemembered = 5;
    	int daysAdjustment = 2;
    	
    	//THIS CARD SHOULD HAVE A RATIO OF 0.5 FOR NUMBER OF TIMES REMEMBERED SO ADJUSTMENT SHOULD DECREASE TO 1
    	Card card1 = new Card(id, question, answer,deckId, studyDate, level, numTimesStudied, numTimesRemembered, daysAdjustment);
    	
    	card1.updateDaysAdjustment();
    	
    	assert(card1.getDaysAdjustment() == 1);
    	
    	numTimesStudied = 10;
    	numTimesRemembered = 9;
    	
    	//CARD SHOULD NOW HAVE RATIO 0.9 SO ADJSUTMENT SHOULD STAY AT 2
    	card1 = new Card(id, question, answer,deckId, studyDate, level, numTimesStudied, numTimesRemembered, daysAdjustment);
    	
    	card1.updateDaysAdjustment();
    	
    	assert(card1.getDaysAdjustment() == 2);
    	
    	numTimesStudied = 10;
    	numTimesRemembered = 10;
    	
    	//CARD SHOULD NOW HAVE HAVE A RATIO OF 1.0 SO ADJUSTMENT SHOULD INCREASE TO 3
    	card1 = new Card(id, question, answer,deckId, studyDate, level, numTimesStudied, numTimesRemembered, daysAdjustment);
    	
    	card1.updateDaysAdjustment();
    	
    	assert(card1.getDaysAdjustment() == 3);
    	
    }
    
    /*void testIncreaseDaysDjustment() {
    	int id = -1;
    	String question = "Test question";
    	String answer = "Test answer";
    	int deckId = -1;
    	java.sql.Date studyDate = java.sql.Date.valueOf(LocalDate.of(1800, 1, 1));
    	int level = 5;
    	int numTimesStudied = 9;
    	int numTimesRemembered = 2;
    	int daysAdjustment = 2;
    	
    	Card card1 = new Card(id, question, answer,deckId, studyDate, level, numTimesStudied, numTimesRemembered, daysAdjustment);
    		
    	assert(card1.getDaysAdjustment() == 2);
    	
    	card1.increaseDaysAdjustment();
    	
    	assert(card1.getDaysAdjustment() == 3);
    	
    	System.out.println("\n\nAll test for testIncreaseDaysDjustment method have passed!\n\n");
    }*/

    /*void testDecreaseDaysDjustment() {
    	int id = -1;
    	String question = "Test question";
    	String answer = "Test answer";
    	int deckId = -1;
    	java.sql.Date studyDate = java.sql.Date.valueOf(LocalDate.of(1800, 1, 1));
    	int level = 5;
    	int numTimesStudied = 9;
    	int numTimesRemembered = 2;
    	int daysAdjustment = 2;
    	
    	Card card1 = new Card(id, question, answer,deckId, studyDate, level, numTimesStudied, numTimesRemembered, daysAdjustment);
    	
    	assert(card1.getDaysAdjustment() == 2);
    	
    	card1.decreaseDaysAdjustment();
    	
    	assert(card1.getDaysAdjustment() == 1);
    	
		card1.decreaseDaysAdjustment();
    	
    	assert(card1.getDaysAdjustment() == 0);
    	
		card1.decreaseDaysAdjustment();
    	
    	assert(card1.getDaysAdjustment() == -1);
    	
    	System.out.println("\n\nAll test for testDecreaseDaysAdjsutment method have passed!\n\n");
    }*/
    
    //TESTS THE GAP TO NEXT STUDY SESSION IS BEING CALCULATED CORRECTLY
    void testGapToNextStudySession() {
    	int id = -1;
    	String question = "Test question";
    	String answer = "Test answer";
    	int deckId = -1;
    	java.sql.Date studyDate = java.sql.Date.valueOf(LocalDate.of(1800, 1, 1));
    	int level = 5;
    	int numTimesStudied = 10;
    	int numTimesRemembered = 5;
    	int daysAdjustment = 2;
    	
    	Card card1 = new Card(id, question, answer,deckId, studyDate, level, numTimesStudied, numTimesRemembered, daysAdjustment);
    	
    	//GAP SHOULD BE (2^LEVEL) + DAYSADJUSTMENT = 2^(5) + 2 = 32 + 2 = 34
    	assert(card1.gapToNextStudySession() == 34);
    }
    
}